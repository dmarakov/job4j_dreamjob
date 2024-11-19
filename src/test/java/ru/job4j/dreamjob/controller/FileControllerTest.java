package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.service.FileService;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {

    private FileService fileService;

    private FileController fileController;

    private MultipartFile testFile;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        testFile = new MockMultipartFile("testFile.img", new byte[]{1, 2, 3});
    }

    @Test
    public void whenGetFileByIdThenHaveTheSameFile() throws IOException {
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        when(fileService.getFileById(1)).thenReturn(Optional.of(fileDto));

        var response = fileController.getById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testFile.getBytes());
    }

    @Test
    public void whenGetFileByIdAndFileNotFoundThenReturnNotFound() {
        when(fileService.getFileById(1)).thenReturn(Optional.empty());

        var response = fileController.getById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}