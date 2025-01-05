package org.example.sportsfile.configurations.s3;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {

    @Bean
    public MinioClient s3Client(S3Properties s3Properties) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(s3Properties.getEndpoint())
                .credentials(s3Properties.getAccessKey(), s3Properties.getSecretKey())
                .region(s3Properties.getRegion())
                .build();

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(s3Properties.getBucket()).build())) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(s3Properties.getBucket()).build()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing Minio bucket: " + s3Properties.getBucket(), e);
        }

        return minioClient;
    }
}