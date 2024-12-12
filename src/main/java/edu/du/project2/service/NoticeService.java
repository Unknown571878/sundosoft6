package edu.du.project2.service;


import edu.du.project2.entity.Notice;
import edu.du.project2.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }
    public void createNotice(String title, String content) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setHits(0);  // 초기 조회수 0
        notice.setDeletedYn(false);  // 삭제 여부 기본값 false
        notice.setCreatedAt(LocalDateTime.now());  // 생성일시 현재 시간으로 설정
        noticeRepository.save(notice);
    }

    // 특정 게시글을 조회하고, 조회수 증가 여부를 설정하여 게시글 반환
    public Notice selectNoticeDetail(Long id) throws Exception{
        Notice notice= noticeRepository.selectNoticeDetail(id); // 게시글 조회
        noticeRepository.updateHitCount(id);  // 조회수 증가
        return notice;
    }

}
