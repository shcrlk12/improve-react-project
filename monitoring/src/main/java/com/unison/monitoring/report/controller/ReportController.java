package com.unison.monitoring.report.controller;

import com.unison.common.docx.DocxGenerator;
import com.unison.common.docx.DocxGeneratorImpl;
import com.unison.common.jsonapi.JsonApi;
import com.unison.common.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.common.jsonapi.Links;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.response.ApiResponse;
import com.unison.common.util.DateTimeUtils;
import com.unison.monitoring.alarm.dto.AlarmDto;
import com.unison.monitoring.common.security.UserDetailImpl;
import com.unison.monitoring.remarks.dto.RemarkDto;
import com.unison.monitoring.report.dto.DailyReportDto;
import com.unison.monitoring.report.dto.ReportDto;
import com.unison.monitoring.report.mapper.ReportMapper;
import com.unison.monitoring.report.service.DailyReportGenerateService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final DailyReportGenerateService dailyReportGenerateService;

    @Getter
    @AllArgsConstructor
    public static class TimeObject {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime writeDate;
    }
    @GetMapping("/{turbineUuid}/daily")
    public ResponseEntity<ApiResponse<ReportDto.Response>> getDailyReport(HttpServletRequest httpServletRequest, @PathVariable("turbineUuid") UUID turbineUuid, TimeObject timeObject){
                ZonedDateTime writeDateInKoreaZone = timeObject.getWriteDate().atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        LocalDateTime writeDateInKorea = LocalDateTime.of(writeDateInKoreaZone.getYear(), writeDateInKoreaZone.getMonth(), writeDateInKoreaZone.getDayOfMonth(), 0, 0);

        DailyReportDto dailyReportDto = dailyReportGenerateService.generateDailyReport(turbineUuid, writeDateInKorea);

        ReportDto.Response attribute = ReportMapper.toReportDtoResponse(dailyReportDto);

        Resource<ReportDto.Response> resource = Resource.<ReportDto.Response>builder()
                .type(ReportDto.TYPE)
                .id(UUID.randomUUID().toString())
                .attributes(attribute)
                .build();

        ApiResponse<ReportDto.Response> apiRequests = ApiResponse.<ReportDto.Response>builder()
                .data(resource)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(new JsonApiOrgHttpHeaders())
                .body(apiRequests);
    }

    @PostMapping("/{turbineUuid}/daily")
    @ModelAttribute
    public void goToWord(HttpServletResponse response, MultipartHttpServletRequest multipartHttpServletRequest, @PathVariable("turbineUuid") UUID turbineUuid, String siteName, TimeObject timeObject) throws Exception {
        LocalDateTime writeDate = timeObject.getWriteDate();
        String fileName = String.format("%s.BS.%s daily report.docx", DateTimeUtils.formatLocalDateTime("yyyyMMdd", writeDate), siteName);
        int DEFAULT_INDENTATION_LEFT = 120;

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl userDetail = (UserDetailImpl)authentication.getPrincipal();

        MultipartFile powerCurveImg = multipartHttpServletRequest.getFile("powerCurve");
        MultipartFile timeChartImg = multipartHttpServletRequest.getFile("timeChart");


        LocalDateTime startDate = LocalDateTime.of(writeDate.getYear(), writeDate.getMonth(), writeDate.getDayOfMonth(), 0, 0);
        LocalDateTime endDate = startDate.plusDays(1);
        try {

            DailyReportDto dailyReportDto = dailyReportGenerateService.generateDailyReport(turbineUuid, startDate);

            ReportDto.Response attribute = ReportMapper.toReportDtoResponse(dailyReportDto);

            DocxGenerator docxGenerator = new DocxGeneratorImpl();


            docxGenerator.setPageMargin(BigInteger.valueOf(850), BigInteger.valueOf(850), BigInteger.valueOf(1440), BigInteger.valueOf(1440));

            LocalDateTime currentDate = attribute.getDate();

            String documentTitleDate = DateTimeUtils.formatLocalDateTime("yyyy-MM-dd", currentDate);

            docxGenerator.addTitle(siteName.toUpperCase(Locale.ROOT) + " DAILY REPORT " + "[" + documentTitleDate + "]");

            docxGenerator.addText("일별 운전 현황");

            XWPFTable table1 = docxGenerator.createTable(3, 4);


            docxGenerator.setTableText(table1, 0, 0, "운전기간", 9, true);
            docxGenerator.setTableText(table1, 0, 1, attribute.getOperatingPeriod(), 9, true);
            docxGenerator.setTableText(table1, 0, 2, "작성일", 9, true);
            docxGenerator.setTableText(table1, 0, 3, DateTimeUtils.formatLocalDateTime("yyyy년 MM월 dd일", LocalDateTime.now()), 9, true);

            docxGenerator.setIndentationLeft(table1, 0, 0, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table1, 0, 1, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table1, 0, 2, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table1, 0, 3, DEFAULT_INDENTATION_LEFT);

            DecimalFormat dfNoneDecimalPoint = new DecimalFormat("#,###");

            DecimalFormat df = new DecimalFormat("#,##0.00");
            String formattedDailyActivePower = df.format(attribute.getActivePower());

            Integer ratedPower = attribute.getRatedPower();
            String formattedRatedPower = df.format(attribute.getActivePower() / (ratedPower * 24) * 100);

            docxGenerator.setTableText(table1, 1, 0, "평균 풍속", 9, true);
            docxGenerator.setTableText(table1, 1, 1, String.format("%.02f m/s", attribute.getWindSpeed()), 9, true);
            docxGenerator.setTableText(table1, 1, 2, "발전량", 9, true);
            docxGenerator.setTableText(table1, 1, 3, String.format("%s kWh (CF:%s%%)", formattedDailyActivePower, formattedRatedPower), 9, true);

            docxGenerator.setIndentationLeft(table1, 1, 0, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table1, 1, 1, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table1, 1, 2, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table1, 1, 3, DEFAULT_INDENTATION_LEFT);

            int dailyOperatingHour = attribute.getOperatingTime() / 3600;
            float dailyOperatingMinute = attribute.getOperatingTime() % 3600 / 60f;
            int dailyGeneratingHour = attribute.getGeneratingTime() / 3600;
            float dailyGeneratingMinute = attribute.getGeneratingTime() % 3600 / 60f;

            if(dailyOperatingHour == 23 && Math.round(dailyOperatingMinute) == 60){
                dailyOperatingHour = 24;
                dailyOperatingMinute = 0;
            }
            if(dailyGeneratingHour == 23 && Math.round(dailyGeneratingMinute) == 60f){
                dailyGeneratingHour = 24;
                dailyGeneratingMinute = 0;
            }
            double operatingPercent = (double) attribute.getOperatingTime() / (24 * 60 * 60.0) * 100.0;

            docxGenerator.setTableText(table1, 2, 0,"운전 시간 (가동률)", 9, true);
            docxGenerator.setTableText(table1, 2, 1, String.format("%02dh %02dm (%.02f%%)", dailyOperatingHour, Math.round(dailyOperatingMinute), operatingPercent), 9, true);
            docxGenerator.setTableText(table1, 2, 2,"발전시간", 9, true);
            docxGenerator.setTableText(table1, 2, 3, String.format("%02dh %02dm", dailyGeneratingHour, Math.round(dailyGeneratingMinute)), 9, true);

            docxGenerator.setIndentationLeft(table1, 2, 0, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table1, 2, 1, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table1, 2, 2, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table1, 2, 3, DEFAULT_INDENTATION_LEFT);

            docxGenerator.addText("누적 운전 현황");

            XWPFTable table2 = docxGenerator.createTable(2, 4);

            String formattedCompassionate = DateTimeUtils.formatLocalDateTime("`yy.MM.dd 00:00", attribute.getStartDate());
            String formattedDate = DateTimeUtils.formatLocalDateTime("`yy.MM.dd 24:00", attribute.getDate());

            Duration duration = Duration.between(attribute.getStartDate(), attribute.getDate().plusDays(1));

            String formattedTotalActivePower = df.format(attribute.getTotalActivePower());
            String formattedTime = dfNoneDecimalPoint.format(duration.toHours());
            String formattedAvailability = df.format(attribute.getTotalActivePower() / (attribute.getRatedPower() * (attribute.getTotalOperatingTime() / 3600.0)) * 100);

            docxGenerator.setTableText(table2, 0, 0, "운전기간", 9, true);
            docxGenerator.setTableText(table2, 0, 1, String.format("%s ~ %s (%sh)", formattedCompassionate, formattedDate, formattedTime), 9, true);
            docxGenerator.setTableText(table2, 0, 2, "누적 발전량", 9, true);
            docxGenerator.setTableText(table2, 0, 3, String.format("%s kWh (CF:%s%%)", formattedTotalActivePower, formattedAvailability), 9, true);

            docxGenerator.setIndentationLeft(table2, 0, 0, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table2, 0, 1, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table2, 0, 2, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table2, 0, 3, DEFAULT_INDENTATION_LEFT);

            int totalOperatingHour = attribute.getTotalOperatingTime() / 3600;
            float totalOperatingMinute = attribute.getTotalOperatingTime() % 3600 / 60f;
            int totalGeneratingHour = attribute.getTotalGeneratingTime() / 3600;
            float totalGeneratingMinute = attribute.getTotalGeneratingTime() % 3600 / 60f;

            double operatingPercentage = attribute.getTotalOperatingTime() / (double)duration.toSeconds() * 100;

            String dfTotalOperatingHourdf = dfNoneDecimalPoint.format(totalOperatingHour);
            String dfTotalGeneratingHour = dfNoneDecimalPoint.format(totalGeneratingHour);

            docxGenerator.setTableText(table2, 1, 0, "누적 운전시간", 9, true);
            docxGenerator.setTableText(table2, 1, 1, String.format("%sh %02dm (%.02f%%)", dfTotalOperatingHourdf, Math.round(totalOperatingMinute), operatingPercentage), 9, true);
            docxGenerator.setTableText(table2, 1, 2, "누적 발전시간", 9, true);
            docxGenerator.setTableText(table2, 1, 3, String.format("%sh %02dm", dfTotalGeneratingHour, Math.round(totalGeneratingMinute)), 9, true);

            docxGenerator.setIndentationLeft(table2, 1, 0, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table2, 1, 1, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table2, 1, 2, DEFAULT_INDENTATION_LEFT);
            docxGenerator.setIndentationLeft(table2, 1, 3, DEFAULT_INDENTATION_LEFT);

            LocalDateTime firstQuarter = LocalDateTime.of(startDate.getYear(), startDate.getMonth().firstMonthOfQuarter() , 1, 0, 0);

            int firstQuarterMonth = firstQuarter.getMonth().getValue();
            int nextQuarterMonth = firstQuarter.plusMonths(2).getMonth().getValue();

            //출력 곡선
            InputStream powwerCurveInputStream = powerCurveImg.getInputStream();
            docxGenerator.addText(String.format("출력곡선(`24.%02d~`24.%02d)", firstQuarterMonth, nextQuarterMonth));
            docxGenerator.createImageTable(powwerCurveInputStream);

            powwerCurveInputStream.close();
            docxGenerator.addPageBreak();

            //출력 곡선
            InputStream timeChartInputStream = timeChartImg.getInputStream();

            docxGenerator.addText(String.format("Time chart(%02d/%02d/%02d)", currentDate.getYear() % 100, currentDate.getMonth().getValue(), currentDate.getDayOfMonth()));
            docxGenerator.createImageTable(timeChartInputStream);

            timeChartInputStream.close();
            //에러 발생 현황
            List<AlarmDto> alarms = attribute.getAlarms();

            int alarmTableSize;

            if(alarms.isEmpty()){
                alarmTableSize = 2;
            }
            else {
                alarmTableSize = alarms.size() + 1;
            }
            XWPFTable table3 = docxGenerator.createTable(alarmTableSize, 5);

            docxGenerator.mergeVerticalCell(table3, 0, 0, alarmTableSize - 1);

            docxGenerator.setTableText(table3, 0, 0, "에러 발생현황", 9, true);
            docxGenerator.setParagraphAlignment(table3, 0, 0, ParagraphAlignment.CENTER);
            docxGenerator.setVerticalAlignment(table3, 0, 0, XWPFTableCell.XWPFVertAlign.CENTER);
            docxGenerator.widthCellsAcrossRow(table3, 0, 0, 1600);

            docxGenerator.setTableText(table3, 0, 1, "발생일시");
            docxGenerator.setParagraphAlignment(table3, 0, 1, ParagraphAlignment.CENTER);

            docxGenerator.setTableText(table3, 0, 2, "에러코드");
            docxGenerator.setParagraphAlignment(table3, 0, 2, ParagraphAlignment.CENTER);

            docxGenerator.setTableText(table3, 0, 3, "내용");
            docxGenerator.setParagraphAlignment(table3, 0, 3, ParagraphAlignment.CENTER);

            docxGenerator.setTableText(table3, 0, 4, "비고");
            docxGenerator.setParagraphAlignment(table3, 0, 4, ParagraphAlignment.CENTER);

            for(int i = 0; i < alarms.size(); i++){
                AlarmDto alarm = alarms.get(i);

                docxGenerator.setTableText(table3, i+1, 1, DateTimeUtils.formatLocalDateTime("yyyy-MM-dd HH:mm:ss", alarm.getTimestamp()), 9, false);
                docxGenerator.setTableText(table3, i+1, 2, alarm.getAlarmNumber().toString(), 9, false);
                docxGenerator.setTableText(table3, i+1, 3, alarm.getAlarmName(), 9, false);
                docxGenerator.setTableText(table3, i+1, 4, Optional.ofNullable(alarm.getRemarks()).orElse(""), 9, false);

                docxGenerator.setIndentationLeft(table3, i+1, 1, DEFAULT_INDENTATION_LEFT);
                docxGenerator.setIndentationLeft(table3, i+1, 2, DEFAULT_INDENTATION_LEFT);
                docxGenerator.setIndentationLeft(table3, i+1, 3, DEFAULT_INDENTATION_LEFT);
                docxGenerator.setIndentationLeft(table3, i+1, 4, DEFAULT_INDENTATION_LEFT);
            }

            docxGenerator.addPageBreak();

            //특이사항
            XWPFTable table4 = docxGenerator.createTable(1, 1);
            XWPFTableRow table4_row = table4.getRow(0);

            List<RemarkDto> remarks = dailyReportDto.getEventBoxNotes();

            remarks.forEach(remark -> {
                XWPFParagraph titleParagraph = table4_row.getCell(0).addParagraph();
                titleParagraph.setIndentationLeft(120);
                titleParagraph.setSpacingAfter(50);

                XWPFRun textOfTitle = titleParagraph.createRun();
                textOfTitle.setText(remark.getOrder() + ". " + remark.getTitle());
                textOfTitle.setBold(true);

                XWPFParagraph contentParagraph = table4_row.getCell(0).addParagraph();
                contentParagraph.setIndentationLeft(480);
                contentParagraph.setSpacingAfter(50);

                String content = remark.getContent();
                String[] lines = content.split("\\r?\\n");

                for (String line : lines) {
                    XWPFRun textOfContent = contentParagraph.createRun();
                    textOfContent.setText(line);
                    textOfContent.addCarriageReturn(); // 줄 바꿈 추가
                }

                table4_row.getCell(0).addParagraph().setSpacingAfter(0);
            });

            //문서 작성자
            XWPFTable table5 = docxGenerator.createTable(1, 2);
            docxGenerator.setTableText(table5, 0, 0, "문서 작성자", 9, true);
            docxGenerator.setParagraphAlignment(table5, 0, 0, ParagraphAlignment.CENTER);

            docxGenerator.setTableText(table5, 0, 1, userDetail.getMember().getName(), 9, true);
            docxGenerator.widthCellsAcrossRow(table5, 0, 0, 2000);
            docxGenerator.setIndentationLeft(table5, 0, 1, DEFAULT_INDENTATION_LEFT);

            ServletOutputStream servletOutputStream = response.getOutputStream();

            XWPFDocument document = docxGenerator.getDocument();

            document.write(servletOutputStream);
            document.close();
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException e) { //에러가 있으면 빈 word 출력하자.
            e.printStackTrace();
        }
    }
}
