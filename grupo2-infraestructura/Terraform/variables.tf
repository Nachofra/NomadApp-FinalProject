# AWS variables
variable "aws_region_id" {
  description = "Region given by Digital House"
  type        = string
  default     = "us-east-2"
}

variable "credentials_profile" {
  description = "Profile used for config and credentials"
  type = string
  default = "default"
}

#Terraform Variables

variable "remote_state" {
  description = "Remote gitlab state for Terraform"
  type = string
  default = "" // Ingresar por gitlab
}

variable "gitlab_username" {
  description = "Gitlab Username"
  type = string
  default = "" // Ingresar por gitlab
}

variable "gitlab_access_token" {
  description = "Gitlab Access Token"
  type = string
  default = "" // Ingresar por gitlab
}

#Google Cloud Variables
variable "google_project_id" {
  description = "Project ID of current infra"
  type        = string
  default     = "" // Ingresar por gitlab
}

#AWSInstances
variable "main_vpc_cidr" {
  description = "Main VPC CIDR (Group A IP)"
  type        = string
  default = "" // Ingresar en gitlab
}

variable "public_subnet_cidr" {
  description = "Public subnet CIDR for backend instance"
  type        = string
  default = "" // Ingresar en gitlab
}

variable "frontend_instance_ip" {
  description = "Frontend bucket private ip"
  type = string
  default = "" // Ingresar en gitlab
}

variable "backend_instance_ip" {
  description = "Frontend bucket private ip"
  type = string
  default = "" // Ingresar en gitlab
}

variable "gitlab_runner_token" {
  description = "Gitlab registration token for runner"
  type = string
  default = "" // Ingresar en gitlab
}