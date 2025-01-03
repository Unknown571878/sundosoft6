package edu.du.project2.service;

import edu.du.project2.entity.Apply;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final String UPLOAD_DIR = "C:/teamproject/sundosoft6/uploads";

    public List<Apply> findAll() {
        return applyRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }

    public void createBoard(String title, String content){
        Apply apply = Apply.builder()
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        applyRepository.save(apply);
    }

    public void createBoard(String title, String content, MultipartFile[] files)throws IOException{
        Apply apply = Apply.builder()
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        if(files != null && files.length > 0){
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if(!Files.exists(uploadPath)){
                Files.createDirectory(uploadPath);
            }

            List<String> filePaths = new ArrayList<>();

            for(MultipartFile file : files){
                if(!file.isEmpty()){
                    String originalFilename = file.getOriginalFilename();
                    String extension = "";
                    if(originalFilename != null && originalFilename.contains(".")){
                        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    }

                    String fileName = UUID.randomUUID().toString().substring(0, 8) + extension;
                    Path filePath = Paths.get(UPLOAD_DIR, fileName);

                    file.transferTo(filePath.toFile());

                    filePaths.add(fileName);
                }
            }
            apply.setFilePaths(filePaths);
        }else{
            apply.setFilePaths(Collections.emptyList());
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
