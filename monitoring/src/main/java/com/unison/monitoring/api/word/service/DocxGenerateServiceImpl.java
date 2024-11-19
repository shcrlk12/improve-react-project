package com.unison.monitoring.api.word.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class DocxGenerateServiceImpl implements DocxGenerateService{

    @Override
    public XWPFDocument generateDilayReportDocx(MultipartHttpServletRequest multipartHttpServletRequest, UUID turbineUuid, LocalDateTime writeDate) {
        return null;
    }
}
