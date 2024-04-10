package com.vedha.file.repository;

import com.vedha.file.entity.FilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FilesEntity, Long> {

}
