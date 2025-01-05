package org.example.sportsfile.utils;

import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

public class MediaTypeUtils {
    private MediaTypeUtils() {}

    private static final String APPLICATION_DOC_TYPE = "application/msword";
    private static final String APPLICATION_DOCX_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    public static final List<String> AVAILABLE_DOCUMENT_TYPES = Arrays.asList(
            MediaType.APPLICATION_PDF_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            APPLICATION_DOCX_TYPE,
            APPLICATION_DOC_TYPE
    );

}
