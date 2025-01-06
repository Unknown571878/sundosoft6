package edu.du.project2.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT") // 긴 텍스트 저장
    private String content;

    @Column(nullable = false)
    private Integer hits;

    @Column(nullable = false)
    private LocalDateTime createdAt;  // 생성일시

    @ElementCollection
    @CollectionTable(name = "notice_files", joinColumns = @JoinColumn(name = "notice_id"))
    private List<FileDetail> files = new ArrayList<>();  // 파일 경로와 이름을 함께 저장


    // 생성일시 자동 설정
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();  // 생성시 현재 시간 설정
        }
    }
}
