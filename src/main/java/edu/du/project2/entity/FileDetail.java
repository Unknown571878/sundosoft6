package edu.du.project2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class FileDetail {
    private String filePath;  // 파일 경로
    private String fileName;  // 원본 파일 이름
}
