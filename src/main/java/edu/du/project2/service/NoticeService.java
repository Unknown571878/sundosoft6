package edu.du.project2.service;

import edu.du.project2.entity.FileDetail;
import edu.du.project2.entity.Notice;
import edu.du.project2.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 공지사항 관련 비즈니스 로직을 처리하는 서비스.
 */
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final FileService fileService;

    // 모든 공지사항을 생성일시 기준 내림차순으로 반환합니다.
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }

    // 새로운 공지사항을 생성합니다.
    public void createNotice(String title, String content, MultipartFile[] files) throws IOException {
        Notice notice = Notice.builder()
                .title(title)
                .content(content)
                .hits(0) // 조회수 초기값
                .createdAt(LocalDateTime.now())
                .build();

        // 파일 업로드 처리
        List<FileDetail> fileDetails = fileService.uploadFiles(files);
        notice.setFiles(fileDetails);

        noticeRepository.save(notice);
    }

    // 특정 게시글을 조회하고, 조회수 증가 여부를 설정하여 게시글 반환
    // 수정 전 메서드 명: selectNoticeDetail
    @Transactional
    public Notice getNoticeDetail(Long id, boolean increaseHitCount) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다. ID: " + id));

        if (increaseHitCount) {
            notice.setHits(notice.getHits() + 1);
            noticeRepository.save(notice);
        }

        return notice;
    }

    // 공지사항을 수정합니다.
    @Transactional
    public void updateNotice(Notice notice) {
        Notice existingNotice = noticeRepository.findById(notice.getId())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        existingNotice.setTitle(notice.getTitle()); // 제목 수정
        existingNotice.setContent(notice.getContent()); // 내용 수정
        noticeRepository.save(existingNotice);  // 수정된 공지사항 저장
    }

    // 특정 공지사항을 삭제합니다.
    @Transactional
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

}
