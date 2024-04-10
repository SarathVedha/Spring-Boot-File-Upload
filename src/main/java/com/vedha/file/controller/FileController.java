package com.vedha.file.controller;

import com.vedha.file.dto.FileResponseDTO;
import com.vedha.file.entity.FilesEntity;
import com.vedha.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Upload file", description = "Upload file to the server")
    @ApiResponse(responseCode = "202", description = "HTTP Status 202 Accepted")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileResponseDTO> uploadFile(@RequestParam(name = "file") MultipartFile multipartFile) {

        FileResponseDTO fileResponseDTO = fileService.uploadFile(multipartFile);

        log.warn("File response: {}", fileResponseDTO.toString());

        return ResponseEntity.accepted().body(fileResponseDTO);
    }

    @Operation(summary = "Download file", description = "Download file from the server")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
//    @GetMapping(value = "/download/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE) // Commented out because of the error throw from global exception handler
    @GetMapping(value = "/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(name = "fileId") Long fileId) {

        FilesEntity filesEntity = fileService.downloadFile(fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filesEntity.getFileName());
//        headers.add(HttpHeaders.CONTENT_TYPE, filesEntity.getFileType());
//        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(filesEntity.getFileSize()));

        ByteArrayResource file = new ByteArrayResource(filesEntity.getFileData());

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(filesEntity.getFileSize()).body(file);
    }

    @Operation(summary = "Get all files", description = "Get all files from the server")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FileResponseDTO>> getAllFiles() {

        List<FileResponseDTO> allFiles = fileService.getAllFiles();

        log.warn("All files: {}", allFiles);

        return ResponseEntity.ok().body(allFiles);
    }

}
