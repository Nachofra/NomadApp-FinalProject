resource "aws_s3_bucket" "static_frontend_bucket" {
  bucket = var.static_frontend_bucket_name
}

resource "aws_s3_bucket_acl" "static_frontend_bucket_acl" {
  bucket = aws_s3_bucket.static_frontend_bucket.id
  acl    = "public-read"
}

resource "aws_s3_bucket_versioning" "static_frontend_bucket_versioning" {
  bucket = aws_s3_bucket.static_frontend_bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_policy" "static_frontend_bucket_policy" {
  bucket = aws_s3_bucket.static_frontend_bucket.id
  policy = data.aws_iam_policy_document.static_frontend_bucket_policy.json
}

data "aws_iam_policy_document" "static_frontend_bucket_policy" {
  statement {
    principals {
      type        = "AWS"
      identifiers = ["*"]
    }

    actions = [
      "s3:GetObject",
    ]

    resources = [
      aws_s3_bucket.static_frontend_bucket.arn,
      "${aws_s3_bucket.static_frontend_bucket.arn}/*",
    ]
  }
}

resource "aws_s3_bucket" "backend_bucket" {
  bucket = var.backend_bucket_name
}

resource "aws_s3_bucket_acl" "backend_bucket_acl" {
  bucket = aws_s3_bucket.backend_bucket.id
  acl    = "public-read"
}

resource "aws_s3_bucket_versioning" "backend_bucket_versioning" {
  bucket = aws_s3_bucket.backend_bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_policy" "backend_bucket_policy" {
  bucket = aws_s3_bucket.backend_bucket.id
  policy = data.aws_iam_policy_document.backend_bucket_policy.json
}

data "aws_iam_policy_document" "backend_bucket_policy" {
  statement {
    principals {
      type        = "AWS"
      identifiers = ["*"]
    }

    actions = [
      "s3:GetObject",
    ]

    resources = [
      aws_s3_bucket.backend_bucket.arn,
      "${aws_s3_bucket.backend_bucket.arn}/*",
    ]
  }
}