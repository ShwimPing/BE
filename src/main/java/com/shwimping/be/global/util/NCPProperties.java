package com.shwimping.be.global.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws")
public record NCPProperties(
        Credentials credentials,
        S3 s3,
        Region region
) {
    public record Credentials(
            String accessKey, String secretKey
    ) {
    }

    public record S3(
            String endpoint, String bucket
    ) {
    }

    public record Region(
            String staticValue
    ) {
    }

    public String credentialsAccessKey() {
        return credentials.accessKey();
    }

    public String credentialsSecretKey() {
        return credentials.secretKey();
    }

    public String s3Endpoint() {
        return s3.endpoint();
    }

    public String regionStaticValue() {
        return region.staticValue();
    }
}
