module "aws_buckets" {
  source = "./S3Buckets"
}

module "aws_instances" {
  source = "./AWSInstances"

  // Waits buckets creation
  depends_on = [module.aws_buckets]

  public_subnet_cidr = var.public_subnet_cidr
  frontend_instance_ip = var.frontend_instance_ip
  backend_instance_ip = var.backend_instance_ip
  gitlab_runner_token = var.gitlab_runner_token
}

module "google_dns" {
  source = "./GoogleCloudDNS"

  // Waits the outputs of AWS instances to create records in the DNS zone
  backend_instance_ip = module.aws_instances.backend_instance_public_ip
  frontend_instance_ip = module.aws_instances.frontend_instance_public_ip
}

