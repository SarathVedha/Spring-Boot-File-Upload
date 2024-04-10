package com.vedha.file.service.impl;

import com.vedha.file.dto.FileResponseDTO;
import com.vedha.file.entity.FilesEntity;
import com.vedha.file.exception.FileException;
import com.vedha.file.repository.FileRepository;
import com.vedha.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public FileResponseDTO uploadFile(MultipartFile multipartFile) {

        try {

            log.warn("File name: {}", multipartFile.getOriginalFilename()); // This will return the original name of the file
            log.warn("File content type: {}", multipartFile.getContentType()); // This will return the content type of the file
            log.warn("File size: {}", multipartFile.getSize()); // This will return the size of the file in bytes
            log.warn("File name: {}", multipartFile.getName()); // This will return the name of the parameter in the form
            log.warn("File Empty: {}", multipartFile.isEmpty()); // This will return true if the file is empty

            if (multipartFile.isEmpty()) {

                throw new FileException("File is empty");
            }

            FilesEntity build = FilesEntity.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .fileType(multipartFile.getContentType())
                    .fileData(multipartFile.getBytes())
                    .fileSize(multipartFile.getSize())
                    .build();

            FilesEntity save = fileRepository.save(build);

            String uriString = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/file/download/")
                    .path(save.getId().toString())
                    .toUriString(); // This will return the URI string

            return FileResponseDTO.builder()
                    .id(save.getId())
                    .fileName(save.getFileName())
                    .fileUrl(uriString)
                    .fileType(save.getFileType())
                    .fileSize((save.getFileSize() / 1024) + " KB")
                    .build();

        }catch (Exception e) {

            throw new FileException("Error occurred while uploading file: " + e.getMessage());
        }
    }

    @Override
    public FilesEntity downloadFile(Long fileId) {

        return fileRepository.findById(fileId).orElseThrow(() -> new FileException("File not found"));
    }

    @Override
    public List<FileResponseDTO> getAllFiles() {

        List<FilesEntity> all = fileRepository.findAll();

        if(all.isEmpty()) throw new FileException("No files found");

        return all.stream().map(filesEntity -> FileResponseDTO.builder()
                .id(filesEntity.getId())
                .fileName(filesEntity.getFileName())
                .fileUrl(ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/v1/file/download/")
                        .path(filesEntity.getId().toString())
                        .toUriString())
                .fileType(filesEntity.getFileType())
                .fileSize((filesEntity.getFileSize() / 1024) + " KB")
                .build()).toList();
    }
}
