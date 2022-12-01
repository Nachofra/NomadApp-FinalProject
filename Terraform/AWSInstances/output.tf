output "frontend_instance_public_ip" {
    value = aws_instance.frontend_instance.public_ip
    description = "The public IP address of the frontend instance"
}
output "backend_instance_public_ip" {
    value = aws_instance.backend_instance.public_ip
    description = "The public IP address of the backend instance"
}