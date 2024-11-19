package com.unison.common.docx;

import org.apache.poi.xwpf.usermodel.*;

import java.io.InputStream;

public interface DocxGenerator {

    XWPFDocument getDocument();

    void setPageMargin(Object left, Object right, Object top, Object bottom);

    void addTitle(String title);

    XWPFTable createTable(int row, int column);

    void setTableText(XWPFTable table, int row, int column, String text);

    void setTableText(XWPFTable table, int row, int column, String text, int fontSize);

    void setTableText(XWPFTable table, int row, int column, String text, int fontSize, boolean isBold);
    void setTableText(XWPFTable table, int row, int column, String text, int fontSize, int spaceAfter, boolean isBold);

    void setIndentationLeft(XWPFTable table, int row, int column, int indentationLeft);


    void setParagraphAlignment(XWPFTable table, int row, int column, ParagraphAlignment align);
    void setVerticalAlignment(XWPFTable table, int row, int column, XWPFTableCell.XWPFVertAlign vAlign);

    XWPFRun getRun(XWPFTable table, int row, int column);

    void createImageTable(InputStream img);

    void mergeHorizontalCell(XWPFTable table, int row, int startColumn, int endColumn);

    void mergeVerticalCell(XWPFTable table, int column, int startRow, int endRow);

    void addText(String text);

    void addPageBreak();

    void widthCellsAcrossRow (XWPFTable table, int rowNum, int colNum, int width);
}
