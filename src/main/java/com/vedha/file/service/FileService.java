package com.vedha.file.service;

import com.vedha.file.dto.FileResponseDTO;
import com.vedha.file.entity.FilesEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    FileResponseDTO uploadFile(MultipartFile multipartFile);

    FilesEntity downloadFile(Long fileId);

    List<FileResponseDTO> getAllFiles();
}
