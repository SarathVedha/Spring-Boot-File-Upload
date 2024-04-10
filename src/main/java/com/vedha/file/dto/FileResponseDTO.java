package com.vedha.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDTO {

    private long id;

    private String fileName;

    private String fileType;

    private String fileSize;

    private String fileUrl;
}
