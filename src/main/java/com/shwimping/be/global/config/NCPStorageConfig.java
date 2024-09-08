package com.shwimping.be.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.shwimping.be.global.util.NCPProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class NCPStorageConfig {

    private final NCPProperties ncpProperties;

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials awsCredentials =
                new BasicAWSCredentials(ncpProperties.credentialsAccessKey(), ncpProperties.credentialsSecretKey());

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(
                        ncpProperties.s3Endpoint(), ncpProperties.regionStaticValue()))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}