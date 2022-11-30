resource "aws_vpc" "main_vpc" {
  cidr_block       = var.main_vpc_cidr
  instance_tenancy = "default"
  enable_dns_support = true
  tags             = {
    Name = "0222FTC1_grupo2_VPC"
  }
}

data "aws_ami" "amazon_linux" {
  owners      = ["099720109477"]
  most_recent = true
  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-focal-20.04-amd64-server-*"]
  }
}

resource "aws_instance" "frontend_instance" {
  ami                    = data.aws_ami.amazon_linux.id
  instance_type          = "t2.micro"
  subnet_id              = aws_subnet.public_subnet.id
  key_name               = var.aws_ssh_key_pair_name_frontend
  vpc_security_group_ids = [aws_security_group.frontend_security_group.id]
  associate_public_ip_address = true
  private_ip = var.frontend_instance_ip
  user_data              = <<-EOF
  #!/bin/bash
  sudo apt update -y
  sudo apt upgrade -y

  cd /opt
  sudo apt install apt-transport-https ca-certificates curl software-properties-common -y
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
  echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  sudo apt update -y
  apt-cache policy docker-ce
  sudo apt install docker-ce -y
  sudo usermod -aG docker ubuntu
  echo "*** Completed Installing Docker"

  curl -L https://packages.gitlab.com/install/repositories/runner/gitlab-runner/script.deb.sh > script.deb.sh
  sudo bash script.deb.sh
  sudo apt install gitlab-runner -y
  sudo gitlab-runner register -n --url ${var.gitlab_runner_url} --registration-token ${var.gitlab_runner_token} --executor docker --description "Integrator Proyect Frontend Runner" --docker-image "docker:stable" --tag-list deployment_frontend --docker-privileged
  cd ~
  echo "*** Completed setting gitlab requeriments for CD"

  sudo curl -L https://github.com/docker/compose/releases/download/1.18.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
  echo "*** Completed Installing Docker Compose"

  sudo mkdir -p /docker/letsencrypt-docker-nginx/src/letsencrypt
  echo -e "${data.local_file.docker_compose_frontend_file.content}" > /docker/letsencrypt-docker-nginx/src/letsencrypt/docker-compose.yml
  echo "*** Completed SSL Creation Config"

  sudo mkdir -p /docker/letsencrypt-docker-nginx/src/production/dh-param
  echo -e "${data.local_file.docker_compose_frontend_file.content}" > /docker/letsencrypt-docker-nginx/src/production/docker-compose.yml
  sudo openssl dhparam -out /docker/letsencrypt-docker-nginx/src/production/dh-param/dhparam-2048.pem 2048
  echo "*** Completed SSL Deploy Config"

  sudo apt install certbot -y
  sudo certbot certonly --non-interactive --agree-tos --standalone --preferred-challenges http -d ${var.site_url} -d ${var.site_url_www} -m ${var.cert_email}
  echo "*** Completed SSL Installation"
  EOF
  tags                   = {
    Name = "0222FTC1_grupo2_frontend_instance"
  }
}

data "local_file" "docker_compose_frontend_file" {
    filename = "${path.module}/DockerComposeFiles/docker-compose-frontend.yml"
}

data "local_file" "docker_compose_backend_file" {
    filename = "${path.module}/DockerComposeFiles/docker-compose-backend.yml"
}

resource "aws_instance" "backend_instance" {
  ami                    = data.aws_ami.amazon_linux.id
  instance_type          = "t2.micro"
  subnet_id              = aws_subnet.public_subnet.id
  key_name               = var.aws_ssh_key_pair_name_backend
  vpc_security_group_ids = [aws_security_group.backend_security_group.id]
  associate_public_ip_address = true
  private_ip = var.backend_instance_ip
  user_data              = <<-EOF
  #!/bin/bash
  sudo apt update -y
  sudo apt upgrade -y

  cd /opt
  sudo apt install apt-transport-https ca-certificates curl software-properties-common -y
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
  echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  sudo apt update -y
  apt-cache policy docker-ce
  sudo apt install docker-ce -y
  sudo usermod -aG docker ubuntu
  echo "*** Completed Installing Docker"

  curl -L https://packages.gitlab.com/install/repositories/runner/gitlab-runner/script.deb.sh > script.deb.sh
  sudo bash script.deb.sh
  sudo apt install gitlab-runner -y
  sudo gitlab-runner register -n --url ${var.gitlab_runner_url} --registration-token ${var.gitlab_runner_token} --executor docker --description "Integrator Proyect Backend Runner" --docker-image "docker:stable" --tag-list deployment_backend --docker-privileged
  cd ~
  echo "*** Completed setting gitlab requeriments for CD"

  sudo curl -L https://github.com/docker/compose/releases/download/1.18.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
  echo "*** Completed Installing Docker Compose"

  sudo mkdir -p /docker/letsencrypt-docker-tomcat/src/production
  echo -e "${data.local_file.docker_compose_backend_file.content}" > /docker/letsencrypt-docker-tomcat/src/production/docker-compose.yml
  echo "*** Completed Docker Requeriments"

  sudo apt install certbot -y
  sudo certbot certonly --non-interactive --agree-tos --standalone --preferred-challenges http -d ${var.backend_site_url} -m ${var.cert_email}
  echo "*** Completed SSL Installation"
  EOF
  tags                   = {
    Name = "0222FTC1_grupo2_backend_instance"
  }
}

resource "aws_internet_gateway" "IGW" {
  vpc_id = aws_vpc.main_vpc.id
  tags   = {
    Name = "0222FTC1_grupo2_IGW"
  }
}

resource "aws_subnet" "public_subnet" {
  vpc_id     = aws_vpc.main_vpc.id
  cidr_block = var.public_subnet_cidr
  tags       = {
    Name = "0222FTC1_grupo2_public_subnet"
  }
}

resource "aws_route_table" "public_route_table" {
  vpc_id = aws_vpc.main_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.IGW.id
  }
  tags = {
    Name = "0222FTC1_grupo2_public_route_table"
  }
}

resource "aws_route_table_association" "public_route_table_Association" {
  subnet_id      = aws_subnet.public_subnet.id
  route_table_id = aws_route_table.public_route_table.id
}

resource "aws_security_group" "frontend_security_group" {
  name = "0222FTC1-grupo2-frontend-SG"
  description = "Allow frontend http, https and ssh traffic"
  vpc_id = aws_vpc.main_vpc.id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  egress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "backend_security_group" {
  name        = "0222FTC1-grupo2-backend-SG"
  description = "Allow backend MySql, ssh and 8080 ports traffic"
  vpc_id      = aws_vpc.main_vpc.id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  ingress {
    from_port   = 587
    to_port     = 587
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    self        = false
  }

  egress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 587
    to_port     = 587
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}