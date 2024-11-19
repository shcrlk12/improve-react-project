package com.unison.monitoring.api.word.service;

import com.unison.monitoring.api.word.DocxController;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DocxGenerateService {

    XWPFDocument generateDilayReportDocx(MultipartHttpServletRequest multipartHttpServletRequest, UUID turbineUuid, LocalDateTime writeDate);
}
