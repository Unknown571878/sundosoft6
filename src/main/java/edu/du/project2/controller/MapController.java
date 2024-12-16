package edu.du.project2.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "http://localhost:9990") // 자기가 쓰는 포트 번호
@Log4j2
public class MapController {

    @GetMapping("/map")
    public String map() {
        return "map/map";
    }
}
