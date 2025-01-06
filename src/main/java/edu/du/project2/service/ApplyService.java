package edu.du.project2.service;

import edu.du.project2.entity.Apply;
import edu.du.project2.entity.FileDetail;
import edu.du.project2.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 상세 분석 신청서 서비스.
 * 신청서 생성, 조회, 수정, 삭제와 파일 처리를 담당.
 */
@RequiredArgsConstructor
@Service
public class ApplyService {
    private final ApplyRepository applyRepository;
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";

    // 모든 입지 분석 신청서를 생성일시 내림차순으로 정렬.
    public List<Apply> findAll() {
        return applyRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }

    /**
     * 첨부파일이 없는 신청서를 생성.
     *
     * @param author  작성자
     * @param title   제목
     * @param content 내용
     */
    @Transactional
    public void createBoard(String author, String title, String content){
        Apply apply = Apply.builder()
                .author(author)
                .title(title)
                .content(content)
                .completedYn('N')
                .createdAt(LocalDateTime.now())
                .build();

        applyRepository.save(apply);
    }

    /**
     * 파일 첨부가 포함된 신청서를 생성.
     *
     * @param author  작성자
     * @param title   제목
     * @param content 내용
     * @param files   첨부 파일 배열
     * @throws IOException 파일 저장 실패 시 발생
     */
    @Transactional
    public void createBoard(String author, String title, String content, MultipartFile[] files) throws IOException {
        Apply apply = buildApply(author, title, content);

        if (files != null && files.length > 0) {
            List<FileDetail> fileDetails = processFiles(files);
            apply.setFiles(fileDetails);
        }

        applyRepository.save(apply);
    }

    // 신청서 상세정보 조회.
    @Transactional
    public Apply selectApplyDetail(Long id){
        return applyRepository.selectApplyDetail(id);
    }

    // 신청서 업데이트.
    @Transactional
    public void updateApply(Apply apply){
        Apply updateApply = applyRepository.selectApplyDetail(apply.getId());
        updateApply.setTitle(apply.getTitle());
        updateApply.setContent(apply.getContent());
        applyRepository.save(updateApply);
    }

    // 신청서 삭제
    @Transactional
    public void deleteApply(Long id){applyRepository.deleteById(id);}

    // 공통 로직: 신청서 객체 생성
    private Apply buildApply(String author, String title, String content) {
        return Apply.builder()
                .author(author)
                .title(title)
                .content(content)
                .completedYn('N')
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 파일 처리 로직 분리
    private List<FileDetail> processFiles(MultipartFile[] files) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }

        List<FileDetail> fileDetails = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                if (originalFilename == null || originalFilename.isEmpty()) {
                    throw new IllegalArgumentException("유효하지 않은 파일 이름입니다.");
                }

                Path filePath = Paths.get(UPLOAD_DIR, originalFilename);
                file.transferTo(filePath.toFile());
                fileDetails.add(new FileDetail("/uploads/" + originalFilename, originalFilename));
            }
        }
        return fileDetails;
    }
}
