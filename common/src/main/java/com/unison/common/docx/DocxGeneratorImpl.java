package com.unison.common.docx;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class DocxGeneratorImpl implements DocxGenerator{
    private XWPFDocument document;

    public DocxGeneratorImpl() {
        document = new XWPFDocument();
    }

    @Override
    public XWPFDocument getDocument() {
        return document;
    }
    @Override
    public void addTitle(String title){
        XWPFParagraph pr = document.createParagraph();
        pr.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = pr.createRun();

        run.setText(title);
        run.setFontSize(18);
        run.setBold(true);
        run.setUnderline(UnderlinePatterns.SINGLE);
    }

    @Override
    public void setPageMargin(Object left, Object right, Object top, Object bottom) {
        CTPageMar pageMar = document.getDocument().getBody().addNewSectPr().addNewPgMar();

        pageMar.setLeft(left);
        pageMar.setRight(right);
        pageMar.setTop(top);
        pageMar.setBottom(bottom);
    }

    @Override
    public XWPFTable createTable(int rowSize, int columnSize) {
        XWPFTable table = document.createTable();
        table.setWidth(10600);

        XWPFTableRow row = table.getRow(0);

        for(int j = 1; j < columnSize; j++) {
            row.addNewTableCell();
        }

        for(int i = 1; i < rowSize; i++) {
            table.createRow();
        }

        document.createParagraph().setSpacingAfter(0);

        return table;
    }

    @Override
    public void setTableText(XWPFTable table, int row, int column, String text) {
        setTableText(table, row, column, text, 9, false);
    }

    @Override
    public void setTableText(XWPFTable table, int row, int column, String text, int fontSize) {
        setTableText(table, row, column, text, fontSize, false);
    }

    @Override
    public void setTableText(XWPFTable table, int row, int column, String text, int fontSize, boolean isBold) {
        setTableText(table, row, column, text, fontSize, 0, isBold);
    }

    @Override
    public void setTableText(XWPFTable table, int row, int column, String text, int fontSize, int spaceAfter, boolean isBold) {
        XWPFTableRow r = table.getRow(row);
        XWPFTableCell c = r.getCell(column);
        c.removeParagraph(0);

        XWPFParagraph pr = c.addParagraph();
        pr.setSpacingAfter(spaceAfter);

        XWPFRun run = pr.createRun();
        run.setText(text);
        run.setFontSize(fontSize);
        run.setBold(isBold);
    }

    @Override
    public void setIndentationLeft(XWPFTable table, int row, int column, int indentationLeft) {
        if (row >= table.getNumberOfRows()) return;
        XWPFTableRow r = table.getRow(row);

        if (column >= r.getTableCells().size()) return;
        XWPFTableCell c = r.getCell(column);

        XWPFParagraph pr;
        if (c.getParagraphs().isEmpty()) {
            pr = c.addParagraph();
        } else {
            pr = c.getParagraphs().get(0);
        }

        pr.setIndentationLeft(indentationLeft);
    }


    @Override
    public void setParagraphAlignment(XWPFTable table, int row, int column, ParagraphAlignment align) {
        if (row >= table.getNumberOfRows()) return;
        XWPFTableRow r = table.getRow(row);

        if (column >= r.getTableCells().size()) return;
        XWPFTableCell c = r.getCell(column);

        XWPFParagraph pr;
        if (c.getParagraphs().isEmpty()) {
            pr = c.addParagraph();
        } else {
            pr = c.getParagraphs().get(0);
        }
        pr.setAlignment(align);
    }

    @Override
    public void setVerticalAlignment(XWPFTable table, int row, int column, XWPFTableCell.XWPFVertAlign vAlign) {
        if (row >= table.getNumberOfRows()) return;
        XWPFTableRow r = table.getRow(row);

        if (column >= r.getTableCells().size()) return;
        XWPFTableCell c = r.getCell(column);

        c.setVerticalAlignment(vAlign);
    }


    @Override
    public XWPFRun getRun(XWPFTable table, int row, int column) {
        return null;
    }

    @Override
    public void createImageTable(InputStream is) {
        XWPFTable table = document.createTable();
        XWPFTableRow row = table.getRow(0);
        row.getCell(0).removeParagraph(0);


        XWPFParagraph pr = row.getCell(0).addParagraph();

        pr.setSpacingBefore(50);
        XWPFRun run = pr.createRun();
        try {
            run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, "power curve.png", Units.toEMU(18.59 * Units.EMU_PER_CENTIMETER / Units.EMU_PER_POINT), Units.toEMU(11.0 * Units.EMU_PER_CENTIMETER / Units.EMU_PER_POINT)); // 이미지 삽입 (사이즈: 100x100 픽셀)
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        document.createParagraph().setSpacingAfter(0);
    }

    @Override
    public void mergeHorizontalCell(XWPFTable table, int row, int startColumn, int endColumn) {

    }

    @Override
    public void mergeVerticalCell(XWPFTable table, int column, int startRow, int endRow) {

        CTTcPr tcPr = table.getRow(startRow).getCell(column).getCTTc().addNewTcPr();
        tcPr.addNewVMerge().setVal(STMerge.Enum.forString("restart")); // 병합 시작

        for(int i = startRow + 1; i <= endRow; i++){
            System.out.println(table.getNumberOfRows());
            table.getRow(i).getCell(column).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.Enum.forString("continue"));
        }
    }

    @Override
    public void addText(String text) {
        XWPFParagraph pr = document.createParagraph();
        pr.setSpacingAfter(10);

        XWPFRun run = pr.createRun();

        run.setText(text);
    }

    @Override
    public void addPageBreak() {
        XWPFParagraph pageBreakParagraph = document.createParagraph();
        pageBreakParagraph.setPageBreak(true);
    }

    @Override
    public void widthCellsAcrossRow(XWPFTable table, int rowNum, int colNum, int width) {
        XWPFTableCell cell = table.getRow(rowNum).getCell(colNum);
        if (cell.getCTTc().getTcPr() == null)
            cell.getCTTc().addNewTcPr();
        if (cell.getCTTc().getTcPr().getTcW()==null)
            cell.getCTTc().getTcPr().addNewTcW();
        cell.getCTTc().getTcPr().getTcW().setW(BigInteger.valueOf((long) width));
    }
}
