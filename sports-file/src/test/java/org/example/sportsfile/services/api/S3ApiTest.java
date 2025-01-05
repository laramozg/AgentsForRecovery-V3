package org.example.sportsfile.services.api;

import io.minio.MinioClient;
import org.example.sportsfile.configurations.s3.S3Properties;
import org.example.sportsfile.exceptions.S3Exception;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class S3ApiTest {

    private final MinioClient minioClient = mock(MinioClient.class);
    private final S3Properties s3Properties = mock(S3Properties.class);
    private final S3Api s3Api = new S3Api(minioClient, s3Properties);

    @Test
    void uploadShouldExecuteSuccessfully() {
        String path = "userId/uuid/document.png";
        InputStream inputStream = getClass().getResourceAsStream("/document.png");
        if (inputStream == null) throw new IllegalStateException("Test file not found");
        when(s3Properties.getBucket()).thenReturn("sports");

        String result = s3Api.upload(path, inputStream);

        assertThat(path).isEqualTo(result);
    }

    @Test
    void uploadShouldThrowException() throws Exception {
        String path = "userId/uuid/document.png";
        InputStream inputStream = getClass().getResourceAsStream("/document.png");
        if (inputStream == null) throw new IllegalStateException("Test file not found");
        when(minioClient.putObject(any())).thenThrow(IOException.class);

        S3Exception s3Exception = assertThrows(S3Exception.class, () -> s3Api.upload(path, inputStream));

        assertThat(s3Exception).isNotNull();
    }

    @Test
    void downloadShouldThrowException() throws Exception {
        String path = "userId/uuid/document.png";
        when(minioClient.getObject(any())).thenThrow(IOException.class);

        S3Exception s3Exception = assertThrows(S3Exception.class, () -> s3Api.download(path));

        assertThat(s3Exception).isNotNull();
    }

    @Test
    void findObjectsPreviewsShouldThrowException() {
        String path = "userId";
        when(minioClient.listObjects(any())).thenThrow(new RuntimeException(new IOException()));

        S3Exception s3Exception = assertThrows(S3Exception.class, () -> s3Api.findObjectsPreviews(path));

        assertThat(s3Exception).isNotNull();
    }

    @Test
    void findPresignedObjectShouldThrowException() throws Exception {
        String path = "userId";
        when(minioClient.getPresignedObjectUrl(any())).thenThrow(IOException.class);

        S3Exception s3Exception = assertThrows(S3Exception.class, () -> s3Api.findPresignedObject(path));

        assertThat(s3Exception).isNotNull();
    }

    @Test
    void deleteShouldThrowException() throws Exception {
        String path = "userId";
        doThrow(IOException.class).when(minioClient).removeObject(any());

        S3Exception s3Exception = assertThrows(S3Exception.class, () -> s3Api.delete(path));

        assertThat(s3Exception).isNotNull();
    }
}