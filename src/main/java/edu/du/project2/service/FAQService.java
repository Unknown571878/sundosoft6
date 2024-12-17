package edu.du.project2.service;

import edu.du.project2.dto.AuthInfo;
import edu.du.project2.entity.FaQ;
import edu.du.project2.entity.QnAList;
import edu.du.project2.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository faqRepository;

    public Page<FaQ> getFAQs(Pageable pageable) {
        List<FaQ> list = faqRepository.findAll();
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    public FaQ faqDetail(Long id) {
        return faqRepository.findById(id).orElse(null);
    }
}
