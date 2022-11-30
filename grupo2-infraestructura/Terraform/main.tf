module "aws_buckets" {
  source = "./S3Buckets"
}

module "aws_instances" {
  source = "./AWSInstances"

  // Waits buckets creation
  depends_on = [module.aws_buckets]
}

module "google_dns" {
  source = "./GoogleCloudDNS"

  // Waits the outputs of AWS instances to create records in the DNS zone
  backend_instance_ip = module.aws_instances.backend_instance_public_ip
  frontend_instance_ip = module.aws_instances.frontend_instance_public_ip
}

