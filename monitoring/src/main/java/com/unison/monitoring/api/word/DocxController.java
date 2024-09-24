package com.unison.monitoring.api.word;

import com.unison.common.docx.DocxGenerator;
import com.unison.common.docx.DocxGeneratorImpl;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.math.BigInteger;

@RestController
@RequestMapping("/api/docx")
@RequiredArgsConstructor
public class DocxController {

    @GetMapping("/daily-report")
    public void goToWord(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=\"document.docx\"");

        try {

            DocxGenerator docxGenerator = new DocxGeneratorImpl();


            docxGenerator.setPageMargin(BigInteger.valueOf(850), BigInteger.valueOf(850), BigInteger.valueOf(1440), BigInteger.valueOf(1440));

            docxGenerator.addTitle("U151 DAILY REPORT");

            docxGenerator.addText("일별 운전 현황");

            XWPFTable table1 = docxGenerator.createTable(3, 4);

            docxGenerator.setTableText(table1, 0, 0, "운전기간");
            docxGenerator.setTableText(table1, 0, 1, "`24.08.21 00:00 ~ 24:00");
            docxGenerator.setTableText(table1, 0, 2, "작성일");
            docxGenerator.setTableText(table1, 0, 3, "2024년 08월 23일");

            docxGenerator.setTableText(table1, 1, 0, "평균 풍속");
            docxGenerator.setTableText(table1, 1, 1, "5.03 m/s");
            docxGenerator.setTableText(table1, 1, 2, "발전량");
            docxGenerator.setTableText(table1, 1, 3, "29,433.45 kWh (CF:28.52%)");

            docxGenerator.setTableText(table1, 2, 0, "운전 시간 (가동률)");
            docxGenerator.setTableText(table1, 2, 1, "15h 07m (62.98%)");
            docxGenerator.setTableText(table1, 2, 2, "발전시간");
            docxGenerator.setTableText(table1, 2, 3, "14h 58m");

            docxGenerator.addText("누적 운전 현황");

            XWPFTable table2 = docxGenerator.createTable(2, 4);

            docxGenerator.setTableText(table2, 0, 0, "운전기간");
            docxGenerator.setTableText(table2, 0, 1, "`19.09.03 00:00 ~ `24.08.21 24:00 (43,560h)");
            docxGenerator.setTableText(table2, 0, 2, "누적 발전량");
            docxGenerator.setTableText(table2, 0, 3, "38,449,139.27 kWh (CF: 23.54%)");

            docxGenerator.setTableText(table2, 1, 0, "누적 운전시간");
            docxGenerator.setTableText(table2, 1, 1, "37,984h 07m (87.20%)");
            docxGenerator.setTableText(table2, 1, 2, "누적 발전시간");
            docxGenerator.setTableText(table2, 1, 3, "24,394h 49m");

            //출력 곡선
            docxGenerator.addText("출력곡선(`24.07~`24.09)");

            InputStream is1 = getClass().getResourceAsStream("/img/power curve.png");
            docxGenerator.createImageTable(is1);
            is1.close();

            docxGenerator.addPageBreak();
            //출력 곡선
            docxGenerator.addText("Time chart(24/08/24)");

            InputStream is2 = getClass().getResourceAsStream("/img/time chart.png");
            docxGenerator.createImageTable(is2);
            is2.close();

            //에러 발생 현황
            XWPFTable table3 = docxGenerator.createTable(4, 5);

            docxGenerator.mergeVerticalCell(table3, 0, 0, 3);

            docxGenerator.setTableText(table3, 0, 0, "에러 발생현황");
            docxGenerator.setTableText(table3, 0, 1, "발생일시");
            docxGenerator.setTableText(table3, 0, 2, "에러코드");
            docxGenerator.setTableText(table3, 0, 3, "내용");
            docxGenerator.setTableText(table3, 0, 4, "비고");

            docxGenerator.setTableText(table3, 1, 1, "2024-08-21 15:57:15");
            docxGenerator.setTableText(table3, 1, 2, "30001");
            docxGenerator.setTableText(table3, 1, 3, "Local manual stop");

            docxGenerator.setTableText(table3, 2, 1, "2024-08-21 15:57:15");
            docxGenerator.setTableText(table3, 2, 2, "30001");
            docxGenerator.setTableText(table3, 2, 3, "Local manual stop");

            docxGenerator.setTableText(table3, 3, 1, "2024-08-21 15:57:15");
            docxGenerator.setTableText(table3, 3, 2, "30001");
            docxGenerator.setTableText(table3, 3, 3, "Local manual stop");

            docxGenerator.addPageBreak();

            //특이사항
            XWPFTable table4 = docxGenerator.createTable(1, 1);
            XWPFTableRow table4_row = table4.getRow(0);

            XWPFParagraph table4_pr1 = table4_row.getCell(0).getParagraphs().get(0);
            table4_pr1.setIndentationLeft(120);
            table4_pr1.setSpacingAfter(50);

            XWPFRun table4_run1 = table4_pr1.createRun();
            table4_run1.setText("1. 주요 이벤트 현황");

            XWPFParagraph table4_pr2 = table4_row.getCell(0).addParagraph();
            table4_pr2.setIndentationLeft(480);
            table4_pr2.setSpacingAfter(50);

            XWPFRun table4_run2 = table4_pr2.createRun();
            table4_run2.setText("1) 특이사항 없음");

            table4_row.getCell(0).addParagraph().setSpacingAfter(0);

            XWPFParagraph table4_pr3 = table4_row.getCell(0).addParagraph();
            table4_pr3.setIndentationLeft(120);
            table4_pr3.setSpacingAfter(50);

            XWPFRun table4_run3 = table4_pr3.createRun();
            table4_run3.setText("2. 주요 에러 발생현황 및 조치사항");

            XWPFParagraph table4_pr4 = table4_row.getCell(0).addParagraph();
            table4_pr4.setIndentationLeft(480);
            table4_pr4.setSpacingAfter(50);

            XWPFRun table4_run4 = table4_pr4.createRun();
            table4_run4.setText("1) 특이사항 없음");

            table4_row.getCell(0).addParagraph().setSpacingAfter(0);

            XWPFParagraph table4_pr5 = table4_row.getCell(0).addParagraph();
            table4_pr5.setIndentationLeft(120);
            table4_pr5.setSpacingAfter(50);

            XWPFRun table4_run5 = table4_pr5.createRun();
            table4_run5.setText("3. 현장이슈 발생현황");

            XWPFParagraph table4_pr6 = table4_row.getCell(0).addParagraph();
            table4_pr6.setIndentationLeft(480);
            table4_pr6.setSpacingAfter(50);

            XWPFRun table4_run6 = table4_pr6.createRun();
            table4_run6.setText("1) 특이사항 없음");

            ServletOutputStream servletOutputStream = response.getOutputStream();

            XWPFDocument document = docxGenerator.getDocument();

            document.write(servletOutputStream);
            document.close();
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
