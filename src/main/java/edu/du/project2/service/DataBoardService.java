package edu.du.project2.service;

import edu.du.project2.entity.DataBoard;
import edu.du.project2.entity.FileDetail;
import edu.du.project2.repository.DataBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataBoardService {
    private final DataBoardRepository dataBoardRepository;
    private final JdbcTemplate jdbcTemplate;
    private final FileService fileService;

    public List<DataBoard> getAllDataList() {
        return dataBoardRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }

    @Transactional
    public void createDataBoard(String title, String content,String tableName,
                                MultipartFile[] files,String a1,String a2,
                                String a3,String a4,String a5,String a6,
                                String a7,String a8,String a9,String a10,
                                String a11,String a12,String a13,String a14,
                                String a15,String a16,String a17,String a18,
                                String a19,String a20) throws IOException {
        // DataBoard 객체 생성 및 초기화
        DataBoard dataBoard = DataBoard.builder()
                .title(title)
                .content(content)
                .hits(0) // 조회수 초기값 설정
                .createdAt(LocalDateTime.now()) // 생성일시 설정
                .updatedAt(LocalDateTime.now()) // 수정일시 설정
                .tableName(tableName)
                .a1(a1).a2(a2).a3(a3).a4(a4).a5(a5)
                .a6(a6).a7(a7).a8(a8).a9(a9).a10(a10)
                .a11(a11).a12(a12).a13(a13).a14(a14).a15(a15)
                .a16(a16).a17(a17).a18(a18).a19(a19).a20(a20)
                .build();

        // 파일 업로드 처리
        List<FileDetail> fileDetails = fileService.uploadFiles(files); // 파일 업로드 서비스 호출
        dataBoard.setFiles(fileDetails); // 파일 정보 설정

        // 데이터 저장
        dataBoardRepository.save(dataBoard);
    }

    @Transactional
    public DataBoard getDataListDetail(Long id, boolean increaseHitCount, String tableName, int limit, int offset) {
        DataBoard dataBoard = dataBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다. ID: " + id));

        if (increaseHitCount) {
            dataBoard.setHits(dataBoard.getHits() + 1);
            dataBoardRepository.save(dataBoard);
        }
        // PostgreSQL 데이터를 가져와 HTML 테이블로 변환 후 preview 필드에 추가
        String htmlTable = fetchTableDataAsHtml(tableName, limit, offset);
        dataBoard.setPreview(htmlTable);
        return dataBoard;
    }

    public String fetchTableDataAsHtml(String tableName, int limit, int offset) {
        // SQL 쿼리 생성
        String sql = String.format("SELECT * FROM %s LIMIT %d OFFSET %d", tableName, limit, offset);

        // 데이터 조회
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        // HTML 테이블 생성
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append("<div style='overflow-x: auto; max-width: 100%;'>"); // 가로 스크롤 추가
        htmlTable.append("<table style='border-collapse: collapse; width: 100%; font-family: Arial, sans-serif; white-space: nowrap;'>");

        // 테이블 헤더 생성
        if (!rows.isEmpty()) {
            htmlTable.append("<thead><tr style='background-color: #f2f2f2;'>");
            for (String column : rows.get(0).keySet()) {
                if (!"geom".equalsIgnoreCase(column) && !"id".equalsIgnoreCase(column) && !"path".equalsIgnoreCase(column)) {
                    htmlTable.append("<th style='border: 1px solid #ddd; padding: 8px; text-align: left;'>")
                            .append(column)
                            .append("</th>");
                }
            }
            htmlTable.append("</tr></thead>");
        }

        // 데이터 행 생성
        htmlTable.append("<tbody>");
        for (Map<String, Object> row : rows) {
            htmlTable.append("<tr>");
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                if (!"geom".equalsIgnoreCase(entry.getKey()) && !"id".equalsIgnoreCase(entry.getKey()) && !"path".equalsIgnoreCase(entry.getKey())) {
                    htmlTable.append("<td style='border: 1px solid #ddd; padding: 8px;'>")
                            .append(entry.getValue() != null ? entry.getValue().toString() : "")
                            .append("</td>");
                }
            }
            htmlTable.append("</tr>");
        }
        htmlTable.append("</tbody></table>");
        htmlTable.append("</div>"); // 닫는 div 태그 추가

        return htmlTable.toString();
    }


}
