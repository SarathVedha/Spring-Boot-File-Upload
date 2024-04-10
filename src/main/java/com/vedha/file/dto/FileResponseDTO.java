package com.vedha.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "FileResponseDTO", description = "File Response DTO")
public class FileResponseDTO {

    @Schema(description = "File ID", example = "1")
    private long id;

    @Schema(description = "File Name", example = "file.txt")
    private String fileName;

    @Schema(description = "File Type", example = "text/plain")
    private String fileType;

    @Schema(description = "File Size", example = "1 KB")
    private String fileSize;

    @Schema(description = "File URL", example = "http://localhost:8080/api/v1/files/download/1")
    private String fileUrl;
}
