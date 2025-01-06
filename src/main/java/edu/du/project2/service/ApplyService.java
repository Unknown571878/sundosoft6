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

@RequiredArgsConstructor
@Service
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";

    public List<Apply> findAll() {
        return applyRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }

    @Transactional
    public void createBoard(String author, String title, String content){
        Apply apply = Apply.builder()
                .author(author)
                .title(title)
                .content(content)
                .endYn('N')
                .createdAt(LocalDateTime.now())
                .build();

        applyRepository.save(apply);
    }

    @Transactional
    public void createBoard(String author, String title, String content, MultipartFile[] files)throws IOException{
        Apply apply = Apply.builder()
                .author(author)
                .title(title)
                .content(content)
                .endYn('N')
                .createdAt(LocalDateTime.now())
                .build();

        if(files != null && files.length > 0){
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if(!Files.exists(uploadPath)){
                Files.createDirectory(uploadPath);
            }

            List<FileDetail> fileDetails = new ArrayList<>();

            for(MultipartFile file : files){
                if(!file.isEmpty()){
                    String originalFilename = file.getOriginalFilename();
                    if (originalFilename == null || originalFilename.isEmpty()) {
                        throw new IllegalArgumentException("파일 이름이 유효하지 않습니다.");
                    }
                    Path filePath = Paths.get(UPLOAD_DIR, originalFilename);

                    // 파일을 지정된 경로에 저장
                    file.transferTo(filePath.toFile());

                    // 파일 경로 및 이름 객체 생성
                    FileDetail fileDetail = new FileDetail("/uploads/" + originalFilename,originalFilename);
                    fileDetails.add(fileDetail);
                }
            }
            // 신청서에 파일 정보 추가
            apply.setFiles(fileDetails);
        }
        applyRepository.save(apply);
    }

    @Transactional
    public Apply selectApplyDetail(Long id){
        return applyRepository.selectApplyDetail(id);
    }

    @Transactional
    public void updateApply(Apply apply){
        Apply updateApply = applyRepository.selectApplyDetail(apply.getId());
        updateApply.setTitle(apply.getTitle());
        updateApply.setContent(apply.getContent());
        applyRepository.save(updateApply);
    }

    @Transactional
    public void deleteApply(Long id){applyRepository.deleteById(id);}
}
