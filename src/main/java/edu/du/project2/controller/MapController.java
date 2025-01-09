package edu.du.project2.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "http://localhost:9990") // 자기가 쓰는 포트 번호
@Log4j2
public class MapController {

    @GetMapping("/map")
    public String map(Model model) {
        // JSON 데이터를 Java 객체로 처리하거나, JSON 문자열로 전달
        String json = "["
                + "{ \"lat\": 35.1546745300293, \"lon\": 126.89229583740234, \"total_score\": 2.979620933532715, \"accessibility_score\": 92517792.0, \"predicted_usage\": 7202.39013671875, \"accessibility_score_standardized\": 3.0387051105499268, \"predicted_usage_standardized\": 2.920536756515503 },"
                + "{ \"lat\": 35.151123046875, \"lon\": 126.9032974243164, \"total_score\": 2.8171443939208984, \"accessibility_score\": 85195552.0, \"predicted_usage\": 7193.0361328125, \"accessibility_score_standardized\": 2.7254629135131836, \"predicted_usage_standardized\": 2.9088258743286133 },"
                + "{ \"lat\": 35.166385650634766, \"lon\": 126.89000701904297, \"total_score\": 2.796351909637451, \"accessibility_score\": 83949728.0, \"predicted_usage\": 7202.39013671875, \"accessibility_score_standardized\": 2.6721670627593994, \"predicted_usage_standardized\": 2.920536756515503 },"
                + "{ \"lat\": 35.161033630371094, \"lon\": 126.90213012695312, \"total_score\": 2.7955527305603027, \"accessibility_score\": 84186112.0, \"predicted_usage\": 7193.0361328125, \"accessibility_score_standardized\": 2.682279348373413, \"predicted_usage_standardized\": 2.9088258743286133 },"
                + "{ \"lat\": 35.17813491821289, \"lon\": 126.89541625976562, \"total_score\": 2.769340991973877, \"accessibility_score\": 80387504.0, \"predicted_usage\": 7280.962890625, \"accessibility_score_standardized\": 2.5197765827178955, \"predicted_usage_standardized\": 3.0189056396484375 }"
                + "]";

        model.addAttribute("json", json);
        return "map/map2";
    }
}
