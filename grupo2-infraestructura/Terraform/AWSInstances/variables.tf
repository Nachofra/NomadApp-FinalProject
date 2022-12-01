# AWS variables
variable "main_vpc_cidr" {
  description = "Main VPC CIDR (Group A IP)"
  type        = string
  // Ingresar en gitlab
}

variable "public_subnet_cidr" {
  description = "Public subnet CIDR for backend instance"
  type        = string
  // Ingresar en gitlab
}

variable "frontend_instance_ip" {
  description = "Frontend bucket private ip"
  type = string
  // Ingresar en gitlab
}

variable "backend_instance_ip" {
  description = "Frontend bucket private ip"
  type = string
  // Ingresar en gitlab
}

variable "aws_ssh_key_pair_name_frontend" {
  description = "SSH key pair name for the frontend instance"
  type = string
  default = "0222FTC1-grupo2-frontend-key"
}

variable "aws_ssh_key_pair_name_backend" {
  description = "SSH key pair name for the backend instance"
  type = string
  default = "0222FTC1-grupo2-backend-key"
}

# Gitlab Variables

variable "gitlab_runner_url" {
  description = "Gitlab project URL for runner"
  type = string
  default = "https://gitlab.ctd.academy"
}

variable "gitlab_runner_token" {
  description = "Gitlab registration token for runner"
  type = string
  // Ingresar en gitlab
}

#WebSite Variables
variable "site_url" {
  description = "Site URL without www"
  type        = string
  default     = "nomadapp.com.ar"
}

variable "backend_site_url" {
  description = "Site URL with backend"
  type        = string
  default     = "backend.nomadapp.com.ar"
}

variable "site_url_www" {
  description = "Site URL with www"
  type        = string
  default     = "www.nomadapp.com.ar"
}

variable "cert_email" {
  description = "Mail used for creating de SSL"
  type        = string
  default     = "noreply@nomadapp.com.ar"
}