terraform {
  required_version = ">= 1.3.0, <= 1.3.4"
  backend "http" {
    config = {
      address = var.remote_state
      username = var.gitlab_username
      password = var.gitlab_access_token
    }
  }
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
    local = {
      source = "hashicorp/local"
      version = "2.2.3"
    }
    google = {
      source = "hashicorp/google"
      version = "4.44.1"
    }
  }
}

provider "aws" {
  region = var.aws_region_id
  shared_credentials_files = ["~/.aws/credentials"] // Crear el archivo en gitlab
  profile = var.credentials_profile
}

provider "google" {
  project     = var.google_project_id
  credentials = file("~/.google/credentials.json") // Crear el archivo en gitlab
}