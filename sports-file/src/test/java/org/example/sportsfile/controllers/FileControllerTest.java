package org.example.sportsfile.controllers;

import org.example.sportsfile.BaseIntegrationTest;
import org.example.sportsfile.controllers.dto.UploadFileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class FileControllerTest extends BaseIntegrationTest {

    @Test
    void uploadShouldReturnCreated() throws Exception {
        uploadDocument();
    }

    @Test
    void downloadShouldExecuteOk() throws Exception {
        UploadFileResponse uploadFileResponse = uploadDocument();
        MvcResult mvcResult = mockMvc.perform(
                get("/files")
                        .param("path", uploadFileResponse.path())
        ).andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        UploadFileResponse uploadFileResponse = uploadDocument();
        mockMvc.perform(
                delete("/files")
                        .param("path", uploadFileResponse.path())
        ).andExpect(status().isNoContent());
    }

    @Test
    void findPreviewsShouldReturnOk() throws Exception {
        UploadFileResponse uploadFileResponse = uploadDocument();
        mockMvc.perform(
                get("/files/previews")
                        .param("path", uploadFileResponse.path())
        ).andExpectAll(
                status().isOk(),
                jsonPath("$").isNotEmpty()
        );
    }

    @Test
    void findDocumentUrlShouldReturnOk() throws Exception {
        UploadFileResponse uploadFileResponse = uploadDocument();
        mockMvc.perform(
                get("/files/urls")
                        .param("path", uploadFileResponse.path())
        ).andExpectAll(
                status().isOk(),
                jsonPath("$").isNotEmpty()
        );
    }

    private UploadFileResponse uploadDocument() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "document.png",
                MediaType.IMAGE_PNG_VALUE,
                getClass().getResourceAsStream("/document.png")
        );

        String responseContent = mockMvc.perform(
                        multipart("/files").file(file)
                ).andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readValue(responseContent, UploadFileResponse.class);
    }
}