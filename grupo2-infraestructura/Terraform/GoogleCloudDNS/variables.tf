variable "hosted_zone_name" {
  type = string
  default = "nomadapp-com-ar"
}

variable "dns_name" {
  type = string
  default = "nomadapp.com.ar."
}

variable "frontend_instance_ip" {
  description = "AWS Frontend EC2 IP"
  type = string
}

variable "backend_instance_ip" {
  description = "AWS Backend EC2 IP"
  type = string
}