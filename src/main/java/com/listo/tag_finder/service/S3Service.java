package com.listo.tag_finder.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request.Builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {
    private final S3Client s3Client;

//    public S3Service() {
//        s3Client = S3Client.builder()
//                .region(Region.US_WEST_2)
//                .credentialsProvider(StaticCredentialsProvider.create(
//                        AwsBasicCredentials.create("null", "null")))
//                .build();
//    }

    public S3Service() {
        s3Client = S3Client.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(DefaultCredentialsProvider.create()) // Use the default credentials provider
                .build();
    }


    public List<Object> listObjects (String bucketName, String continuationToken, String maxKeys) {
        Builder requestBuilder = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .maxKeys(Integer.parseInt(maxKeys));

        if (continuationToken != null && !continuationToken.isEmpty()) {
            requestBuilder.continuationToken(continuationToken);
        }

        ListObjectsV2Response response = s3Client.listObjectsV2(requestBuilder.build());
        List<String> objectKeys = response.contents()
                .stream()
                .map((S3Object::key))
                .toList();

        List<Object> results = new ArrayList<>();
        results.add(objectKeys);
        results.add("");

        if (response.nextContinuationToken() != null) {
            continuationToken = response.nextContinuationToken();
            results.set(1, continuationToken);
            System.out.println("Continuation token: " + continuationToken);
        }

        return results;
    }

    public void uploadFile(String bucketName, String fileKey, MultipartFile file) throws IOException {
        InputStream fileInputStream = file.getInputStream();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();
        PutObjectResponse response = s3Client.putObject(request, RequestBody.fromInputStream(fileInputStream, file.getSize()));
    }

    public void deleteObject(String bucketName, String fileKey) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();
        s3Client.deleteObject(request);
    }
}
