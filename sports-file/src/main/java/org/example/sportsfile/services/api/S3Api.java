package org.example.sportsfile.services.api;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.example.sportsfile.configurations.s3.S3Properties;
import org.example.sportsfile.exceptions.S3Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class S3Api {
    private final MinioClient minioClient;
    private final S3Properties s3Properties;
    private final Logger logger = LoggerFactory.getLogger(S3Api.class);

    private static final int EXPIRY = 1;
    private static final long PART_SIZE = -1L;

    public String upload(String path, InputStream inputStream) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(s3Properties.getBucket())
                    .object(path)
                    .stream(inputStream, inputStream.available(), PART_SIZE)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception ex) {
            throw new S3Exception("Can't upload object", ex);
        }
        logger.trace("Successfully put object to path - {}", path);
        return path;
    }

    public byte[] download(String path) {
        try {
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(s3Properties.getBucket())
                    .object(path)
                    .build();
            return minioClient.getObject(getObjectArgs).readAllBytes();
        } catch (Exception ex) {
            throw new S3Exception("Can't download object", ex);
        }
    }

    public List<String> findObjectsPreviews(String prefix) {
        try {
            ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                    .bucket(s3Properties.getBucket())
                    .recursive(true)
                    .prefix(prefix)
                    .build();
            Iterable<Result<Item>> resultIterable = minioClient.listObjects(listObjectsArgs);
            List<String> objectNames = new ArrayList<>();

            for (Result<Item> result : resultIterable) {
                objectNames.add(result.get().objectName());
            }

            return objectNames;
        } catch (Exception ex) {
            throw new S3Exception("Can't find object's list", ex);
        }
    }

    public String findPresignedObject(String path) {
        try {
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(s3Properties.getBucket())
                    .object(path)
                    .method(Method.GET)
                    .expiry(EXPIRY, TimeUnit.HOURS)
                    .build();
            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        } catch (Exception ex) {
            throw new S3Exception("Can't find object's url", ex);
        }
    }

    public void delete(String path) {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(s3Properties.getBucket())
                    .object(path)
                    .build();
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception ex) {
            throw new S3Exception("Can't delete object", ex);
        }
        logger.trace("Successfully deleted objects by path - {}", path);
    }
}
