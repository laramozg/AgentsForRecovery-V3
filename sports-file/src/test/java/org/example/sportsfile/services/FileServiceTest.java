package org.example.sportsfile.services;

import org.example.sportsfile.configurations.generators.RandomUUIDGenerator;
import org.example.sportsfile.exceptions.ErrorCode;
import org.example.sportsfile.exceptions.InternalException;
import org.example.sportsfile.services.api.S3Api;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.sportsfile.utils.Models.USER_ID;
import static org.example.sportsfile.utils.SecurityContextUtils.mockSecurityContext;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FileServiceTest {
    private S3Api s3Api;
    private RandomUUIDGenerator idGenerator;
    private FileService fileService;

    @BeforeEach
    void setUp() {
        s3Api = mock(S3Api.class);
        idGenerator = new RandomUUIDGenerator();
        fileService = new FileService(s3Api, idGenerator);
        mockSecurityContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void downloadShouldExecuteSuccessfully() {
        String path = USER_ID + "/uuid/document.png";
        byte[] expectedBytes = new byte[]{1, 2, 3};
        when(s3Api.download(path)).thenReturn(expectedBytes);

        byte[] result = fileService.download(path, USER_ID.toString());

        assertThat(result).isEqualTo(expectedBytes);
    }

    @Test
    void downloadShouldThrowForbidden() {
        String path = "userId/uuid/document.png";

        InternalException internalException = assertThrows(InternalException.class, () ->
                fileService.download(path, USER_ID.toString()));

        assertThat(internalException.getHttpStatus()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(internalException.getErrorCode()).isEqualTo(ErrorCode.DOCUMENT_NOT_YOURS);
    }

    @Test
    void findObjectsPreviewsByUserShouldExecuteSuccessfully() throws Exception {
        List<String> previews = List.of("doc1", "doc2");
        when(s3Api.findObjectsPreviews(anyString())).thenReturn(previews);

        List<String> result = fileService.findObjectsPreviewsByUser(USER_ID.toString());

        assertThat(previews.size()).isEqualTo(result.size());
    }

    @Test
    void findObjectUrlShouldExecuteSuccessfully(){
        String url = "url";
        String path = USER_ID + "/uuid/document.png";
        when(s3Api.findPresignedObject(anyString())).thenReturn(url);

        String result = fileService.findObjectUrl(path, USER_ID.toString());

        assertThat(result).isEqualTo(url);
    }

    @Test
    void findObjectUrlShouldThrowForbidden() {
        String path = "userId/uuid/document.png";

        InternalException internalException = assertThrows(InternalException.class, () -> fileService.findObjectUrl(path, USER_ID.toString()));

        assertThat(internalException.getHttpStatus()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(internalException.getErrorCode()).isEqualTo(ErrorCode.DOCUMENT_NOT_YOURS);
    }

    @Test
    void uploadShouldExecuteSuccessfully() throws Exception {
        String path = USER_ID + "/uuid/document.png";
        List<String> availableDocumentType = List.of(MediaType.IMAGE_PNG_VALUE);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "document.png",
                MediaType.IMAGE_PNG_VALUE,
                getClass().getResourceAsStream("/document.png")
        );
        when(s3Api.upload(anyString(), any())).thenReturn(path);

        String result = fileService.upload(file, availableDocumentType);

        assertThat(result.startsWith(String.valueOf(USER_ID)));
        assertThat(result.endsWith("document.png"));
    }

    @Test
    void uploadShouldThrowUnavailableContentType() throws IOException {
        List<String> availableDocumentType = List.of();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "document.png",
                MediaType.IMAGE_PNG_VALUE,
                getClass().getResourceAsStream("/document.png")
        );

        InternalException internalException = assertThrows(InternalException.class, () -> fileService.upload(file, availableDocumentType));

        assertThat(internalException.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(internalException.getErrorCode()).isEqualTo(ErrorCode.UNAVAILABLE_CONTENT_TYPE);
    }

    @Test
    void deleteShouldExecuteSuccessfully() {
        String path = USER_ID + "/uuid/document.png";
        doNothing().when(s3Api).delete(path);
        assertDoesNotThrow(() -> fileService.delete(path));
    }

    @Test
    void deleteShouldThrowForbidden() {
        String path = "userId/uuid/document.png";

        InternalException internalException = assertThrows(InternalException.class, () ->
                fileService.delete(path));

        assertThat(internalException.getHttpStatus()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(internalException.getErrorCode()).isEqualTo(ErrorCode.DOCUMENT_NOT_YOURS);
    }
}