package edu.du.project2.controller;

import edu.du.project2.dto.KioskRequest;
import edu.du.project2.entity.KioskBase;
import edu.du.project2.repository.KioskBaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KioskController {
    public final KioskBaseRepository kioskBaseRepository;

    @PostMapping("/kiosk/data")
    public ResponseEntity<Map<String, Object>> getKioskData(@RequestBody KioskRequest request) {
        String name = request.getName();
        System.out.println("Received data: " + name);

        // 예시로 요청한 name 값을 기준으로 데이터를 가져옵니다.
        List<KioskBase> kiosks = kioskBaseRepository.findAllByName(name);
        System.out.println("Kiosk data: " + kiosks);

        // totalScore 기준으로 내림차순 정렬하고 상위 5개만 가져오기
        List<KioskBase> topKiosks = kiosks.stream()
                .sorted(Comparator.comparingDouble(KioskBase::getTotalScore).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // 결과를 Map 형태로 감싸서 반환
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("kioskData", topKiosks);

        // ResponseEntity로 반환하며, HttpStatus.OK 상태 코드 반환
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
