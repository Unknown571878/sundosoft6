package edu.du.project2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 키오스크 기본 데이터 엔티티.
 * 이 클래스는 키오스크의 기본 정보를 관리하며, 데이터베이스에 저장될 각종 속성들을 정의합니다.
 * 필드에는 위도, 경도, 총 점수, 접근성 점수, 예측 사용량 등 다양한 정보가 포함됩니다.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KioskBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @Column(nullable = true)
    private String name; // 이름

    @Column(nullable = false)
    private Double lat; // 위도

    @Column(nullable = false)
    private Double lon; // 경도

    @Column(nullable = true)
    private Double totalScore; // 총 점수

    @Column(nullable = true)
    private Double accessibilityScore; // 접근성 점수

    @Column(nullable = true)
    private Double predictedUsage; // 예측된 사용량

    @Column(nullable = true)
    private Double accessibilityScoreStandardized; // 표준화된 접근성 점수

    @Column(nullable = true)
    private Double predictedUsageStandardized; // 표준화된 예측된 사용량

}
