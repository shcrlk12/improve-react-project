package com.unison.common.docx;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.InputStream;

public interface DocxGenerator {

    XWPFDocument getDocument();

    void setPageMargin(Object left, Object right, Object top, Object bottom);

    void addTitle(String title);

    XWPFTable createTable(int row, int column);

    void setTableText(XWPFTable table, int row, int column, String text);

    XWPFRun getRun(XWPFTable table, int row, int column);

    void createImageTable(InputStream img);

    void mergeHorizontalCell(XWPFTable table, int row, int startColumn, int endColumn);

    void mergeVerticalCell(XWPFTable table, int column, int startRow, int endRow);

    void addText(String text);

    void addPageBreak();
}
