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

#Google Cloud Variables

variable "google_project_id" {
  description = "Project ID of current infra"
  type        = string
   // Ingresar por gitlab
}