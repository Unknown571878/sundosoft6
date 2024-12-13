package edu.du.project2.service;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.entity.QnA;
import edu.du.project2.entity.QnAList;
import edu.du.project2.repository.QnARepository;
import edu.du.project2.repository.QnA_ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAService {

    private final QnA_ListRepository qnAListRepository;
    private final QnARepository qnARepository;

    public void initializeSession(HttpSession session, Long id, String email, String name, String role) {
        if (session.getAttribute("authInfo") != null) {
            session.invalidate();
        }
        AuthInfo authInfo = new AuthInfo(id, email, name, role);
        session.setAttribute("authInfo", authInfo);
    }

    public Page<QnAList> getInquiries(AuthInfo authInfo, Pageable pageable) {
        List<QnAList> list;
        if (authInfo.getRole().equals("admin")) {
            list = qnAListRepository.findAllByOrderByIdDesc();
        } else {
            list = qnAListRepository.findAllByUidOrderByIdDesc(authInfo.getId());
        }
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    public QnAList getInquiryDetail(Long id) {
        return qnAListRepository.findById(id).orElse(null);
    }

    public List<QnA> getInquiryReplies(Long id) {
        QnAList qna = getInquiryDetail(id);
        return qnARepository.findAllByQnaIdOrderByIdAsc(qna);
    }

    public void insertInquiry(QnAList list, String content, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        QnAList qnalist = QnAList.builder()
                .title(list.getTitle())
                .created_at(LocalDateTime.now())
                .delete_yn('n')
                .state('Q')
                .uid(authInfo.getId())
                .build();
        QnAList saveQnA = qnAListRepository.save(qnalist);

        QnA qna = QnA.builder()
                .qnaId(saveQnA)
                .content(content)
                .state('Q')
                .build();
        qnARepository.save(qna);
    }

    public void insertAnswer(String content, String role, Long id) {
        QnAList qnalist = qnAListRepository.findById(id).orElse(null);

        QnA qna = QnA.builder()
                .qnaId(qnalist)
                .content(content)
                .state(role.equals("admin") ? 'A' : 'Q')
                .build();
        qnARepository.save(qna);

        if (qnalist != null) {
            qnalist.setState(role.equals("admin") ? 'A' : 'Q');
            qnAListRepository.save(qnalist);
        }
    }
}
