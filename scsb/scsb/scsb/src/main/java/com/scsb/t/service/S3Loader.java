package com.scsb.t.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
@Service
public   class S3Loader {
    public  final String BUCKET_NAME = "scsb"; // TODO: Replace with your bucket name
    public  final String BASE_SIGNATURES_PATH = "signatures/";
    public  final String BASE_KEY_PATH = "formTemplate/";
    public  final String ENDPOINT = "https://o0m5.sg.idrivee2-44.com";
    public  final String ACCESS_KEY = "5sXb1p7xDgohvewN49Gp";
    public  final String SECRET_KEY = "k3E88YE9msfmKUpLjY2uzFJ1okfzKZbDDScgWvNO";
    public final S3Client s3;

    public S3Loader() {
        s3 = S3Client.builder()
                .region(Region.AP_SOUTHEAST_1)  // Adjust this if your region is different
                .endpointOverride(URI.create(ENDPOINT))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                .build();
    }

    public void uploadSignatureToS3(String signature, String s3Key) {
        s3.putObject(PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(s3Key)
                        .build(),
                RequestBody.fromString(signature));
    }

    public String readSignatureFromS3(String s3Key) {
        try (ResponseInputStream<GetObjectResponse> is = s3.getObject(GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(s3Key)
                .build())) {
            return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load signature from S3", e);
        }
    }

    public String loadFormData(String fileName) {
            String fullKey = BASE_KEY_PATH + fileName;

            try (InputStream inputStream = s3.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fullKey)
                    .build())) {

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    StringBuilder data = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        data.append(line);
                    }
                    return data.toString();
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to load data from S3", e);
            }
    }

            public void uploadToS3(String fileContent, String s3Key) {
            s3.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET_NAME)
                            .key(s3Key)
                            .build(),
                    RequestBody.fromString(fileContent));
        }
}
