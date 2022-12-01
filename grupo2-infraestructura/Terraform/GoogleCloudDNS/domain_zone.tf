resource "google_dns_managed_zone" "prod_zone" {
  name        = var.hosted_zone_name
  dns_name    = var.dns_name
  visibility = "public"
}

resource "google_dns_record_set" "www_cname" {
  name         = "test.www.${google_dns_managed_zone.prod_zone.dns_name}"
  managed_zone = google_dns_managed_zone.prod_zone.name
  type         = "CNAME"
  ttl          = 300
  rrdatas      = [google_dns_record_set.frontend_a.name]
}

resource "google_dns_record_set" "frontend_a" {
  name         = "test." + google_dns_managed_zone.prod_zone.dns_name
  managed_zone = google_dns_managed_zone.prod_zone.name
  type         = "A"
  ttl          = 300

  rrdatas = [var.frontend_instance_ip]
}

resource "google_dns_record_set" "backend_a" {
  name         = "test.backend.${google_dns_managed_zone.prod_zone.dns_name}"
  managed_zone = google_dns_managed_zone.prod_zone.name
  type         = "A"
  ttl          = 300

  rrdatas = [var.backend_instance_ip]
}