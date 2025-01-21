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
        return "data/dataList";
    }
    @GetMapping("/dataListDetail")
    public String getDataListDetail(@RequestParam Long id,
                                    @RequestParam(defaultValue = "10") int limit,
                                    @RequestParam(defaultValue = "0") int offset,Model model) throws Exception {

        DataBoard dataBoard = dataBoardService.getDataListDetail(id, true, limit, offset);
        model.addAttribute("dataList", dataBoard);
        model.addAttribute("now", getCurrentTime()); // 현재 시간
        return "data/dataListDetail";
    }


    // 현재 시간을 반환합니다.
    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
