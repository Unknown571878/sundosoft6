package edu.du.project2.controller;

import edu.du.project2.dto.KioskRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KioskController {

    @PostMapping("/json")
    public String json(@RequestBody KioskRequest kioskRequest) throws IOException {
        // 클라이언트에서 받은 데이터 출력
        log.info("Received data: {}", kioskRequest);

        String filePath = "C:\\Users\\DU\\sundosoft\\json\\"+kioskRequest.getName()+".json";

        try {
            // 파일 내용 읽기
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // 파일 내용을 콘솔에 출력
            System.out.println(content);

            // 파일 내용을 로깅
            log.info("File content: {}", content);

            // 서버에서 응답으로 JSON 데이터 반환
            return "{\"message\": \"파일 내용이 콘솔에 출력되었습니다.\", \"data\": " + content + "}";
        } catch (IOException e) {
            // 파일 읽기 에러 처리
            log.error("파일 읽기 중 오류 발생", e);
            return "{\"error\": \"파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage() + "\"}";
        }
    }

}
