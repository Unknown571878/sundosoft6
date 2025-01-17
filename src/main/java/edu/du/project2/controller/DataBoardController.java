package edu.du.project2.controller;

import edu.du.project2.entity.DataBoard;
import edu.du.project2.service.DataBoardService;
import edu.du.project2.utils.PagingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DataBoardController {
    private final DataBoardService dataBoardService;


    @GetMapping("/dataList")
    public String getDataList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<DataBoard> dataBoard = dataBoardService.getAllDataList();
        Page<DataBoard> dataBoardPage = PagingUtils.createPage(dataBoard, pageable);

        model.addAttribute("dataList", dataBoardPage);
        model.addAttribute("totalDataList", dataBoard.size());
        model.addAttribute("now", getCurrentTime());
        return "data/datalist";
    }
    @GetMapping("/dataListDetail")
    public String getDataListDetail(@RequestParam Long id,
                                    @RequestParam String tableName,
                                    @RequestParam(defaultValue = "10") int limit,
                                    @RequestParam(defaultValue = "0") int offset,Model model) throws Exception {

        DataBoard dataBoard = dataBoardService.getDataListDetail(id, true,tableName, limit, offset);
        model.addAttribute("offset", offset);
        model.addAttribute("limit", limit);
        model.addAttribute("dataList", dataBoard);
        model.addAttribute("now", getCurrentTime()); // 현재 시간
        return "data/dataListDetail";
    }
    @GetMapping("/dataListWrite")
    public String getDataListWrite(){
        return "data/dataListWrite";
    }
    @PostMapping("/dataListWrite")
    public String createData(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tableName")String tableName,
            @RequestParam("a1") String a1,@RequestParam("a2") String a2,@RequestParam("a3") String a3,
            @RequestParam("a4") String a4,@RequestParam("a5") String a5,@RequestParam("a6") String a6,
            @RequestParam("a7") String a7,@RequestParam("a8") String a8,@RequestParam("a9") String a9,
            @RequestParam("a10") String a10,@RequestParam("a11") String a11,@RequestParam("a12") String a12,
            @RequestParam("a13") String a13,@RequestParam("a14") String a14,@RequestParam("a15") String a15,
            @RequestParam("a16") String a16,@RequestParam("a17") String a17,@RequestParam("a18") String a18,
            @RequestParam("a19") String a19,@RequestParam("a20") String a20,
            @RequestParam("files") MultipartFile[] files) throws IOException {

        // 데이터 저장 처리
        dataBoardService.createDataBoard(title, content,tableName,files,a1,a2,a3,a4,a5,a6,a7,a8,
                a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20);

        return "redirect:/dataList"; // 데이터 목록 페이지로 리다이렉트
    }


    // 현재 시간을 반환합니다.
    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
