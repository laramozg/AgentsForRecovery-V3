package org.example.sportsfile.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.sportsfile.controllers.dto.FindFileUrlResponse;
import org.example.sportsfile.controllers.dto.UploadFileResponse;
import org.example.sportsfile.services.FileService;
import org.example.sportsfile.utils.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

import static org.example.sportsfile.utils.MediaTypeUtils.AVAILABLE_DOCUMENT_TYPES;

@RequiredArgsConstructor
@RestController
@RequestMapping("/files")
@Tag(name = "File controller", description = "Контроллер для работы с файлами")
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    @Operation(summary = "Загрузка файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Файл загружен", content = @Content(schema = @Schema(implementation = UploadFileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Недоступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResponse upload(
            @Parameter(description = "Файл к загрузке") @RequestParam MultipartFile file
    ) throws IOException {
        logger.trace("Upload document request");
        String path = fileService.upload(file, AVAILABLE_DOCUMENT_TYPES);
        return new UploadFileResponse(path);
    }

    @Operation(summary = "Скачивание файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл скачан"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Недоступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Файл не найден", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> download(
            @Parameter(description = "Путь до файла") @RequestParam String path,
            @Parameter(description = "Id пользователя") @RequestParam(required = false) String userId
    ) {
        if (userId == null) {
            userId = SecurityContext.getAuthorizedUserId().toString();
        }
        logger.trace("Download document by path - {}", path);
        byte[] fileData = fileService.download(path, userId);

        if (fileData != null) {
            String fileName = path.split("/")[path.split("/").length - 1];
            return asFileResponseEntity(fileData, fileName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Удаление файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Файл удален"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Недоступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Файл не найден", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Путь до файла") @RequestParam String path
    ) {
        logger.trace("Delete document by path - {}", path);
        fileService.delete(path);
    }

    @Operation(summary = "Поиск превью файлов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Превью найдены"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Недоступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Файл не найден", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/previews")
    @ResponseStatus(HttpStatus.OK)
    public Collection<String> findPreviews(
            @Parameter(description = "Id пользователя") @RequestParam(required = false) String userId
    ) {
        if (userId == null) {
            userId = SecurityContext.getAuthorizedUserId().toString();
        }
        logger.trace("Find document previews");
        Collection<String> previews = fileService.findObjectsPreviewsByUser(userId);
        logger.trace("Found previews - {}", previews);
        return previews;
    }

    @Operation(summary = "Поиск url файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Url найден"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Недоступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/urls")
    @ResponseStatus(HttpStatus.OK)
    public FindFileUrlResponse findDocumentUrl(
            @Parameter(description = "Путь до файла") @RequestParam String path,
            @Parameter(description = "Id пользователя") @RequestParam(required = false) String userId
    ) {
        if (userId == null) {
            userId = SecurityContext.getAuthorizedUserId().toString();
        }
        logger.trace("Find document url by path - {}", path);
        String url = fileService.findObjectUrl(path, userId);
        return new FindFileUrlResponse(url);
    }

    private ResponseEntity<byte[]> asFileResponseEntity(byte[] fileData, String filename) {
        ContentDisposition contentDisposition = ContentDisposition.inline().filename(filename).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }
}