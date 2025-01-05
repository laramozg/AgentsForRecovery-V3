package org.example.sportsfile.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsfile.configurations.generators.IdGenerator;
import org.example.sportsfile.exceptions.ErrorCode;
import org.example.sportsfile.exceptions.InternalException;
import org.example.sportsfile.services.api.S3Api;
import org.example.sportsfile.utils.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {
    private final S3Api s3Api;
    private final IdGenerator idGenerator;
    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    public byte[] download(String path, String userId) {
        if (!path.contains(userId)) {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.DOCUMENT_NOT_YOURS);
        }
        logger.info("Finding document by path - {}", path);
        return s3Api.download(path);
    }

    public List<String> findObjectsPreviewsByUser(String userId) {
        logger.info("Finding document's previews by user - {}", userId);
        return s3Api.findObjectsPreviews(userId);
    }

    public String findObjectUrl(String path, String userId) {
        if (!path.contains(userId)) {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.DOCUMENT_NOT_YOURS);
        }
        logger.info("Finding document's url by path - {}", path);
        return s3Api.findPresignedObject(path);
    }

    public String upload(MultipartFile file, List<String> availableDocumentType) throws IOException {
        if (availableDocumentType.isEmpty() || !availableDocumentType.contains(file.getContentType())) {
            throw new InternalException(HttpStatus.BAD_REQUEST, ErrorCode.UNAVAILABLE_CONTENT_TYPE);
        }
        String userId = SecurityContext.getAuthorizedUserId().toString();
        String path = s3Api.upload(userId + "/" + idGenerator.generateId() + "/" + file.getOriginalFilename(), file.getInputStream());
        logger.info("Document saved successfully by user with id - {}", userId);
        return path;
    }

    public void delete(String path) {
        String userId = SecurityContext.getAuthorizedUserId().toString();
        if (!path.contains(userId)) {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.DOCUMENT_NOT_YOURS);
        }
        logger.info("Deleting document by path - {}", path);
        s3Api.delete(path);
    }
}
