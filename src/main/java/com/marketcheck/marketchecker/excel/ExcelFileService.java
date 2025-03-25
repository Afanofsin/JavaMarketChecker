package com.marketcheck.marketchecker.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ExcelFileService
{
    private final String EXCEL_FILE_NAME = "PriceStatistics.xlsx";
    private final String CASE_SHEET = "Case Prices";
    private final String SKIN_SHEET = "Skin Prices";
    private XSSFWorkbook wb;

    public ExcelFileService()
    {
        readOrCreateWB();
    }

    private void readOrCreateWB()
    {
        File file = new File(EXCEL_FILE_NAME);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                wb = new XSSFWorkbook(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            wb = new XSSFWorkbook();
            wb.createSheet(CASE_SHEET);
            wb.createSheet(SKIN_SHEET);
            saveWB();
        }
    }

    private void saveWB()
    {
        try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_NAME)) {
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCaseData(String itemName, String date, String volume, String medianPrice, String lowestPrice, String sheetName) {
        XSSFSheet sheet;
        if(sheetName.equals("Case Prices"))
        {
            sheet = this.wb.getSheet(CASE_SHEET);
        }
        else if (sheetName.equals("Skin Prices"))
        {
            sheet = this.wb.getSheet(SKIN_SHEET);
        }
        else
        {
            sheet = null;
        }

        if (sheet == null) { return;}

        int caseRowNum = findCaseRow(sheet, itemName);
        XSSFRow caseRow;

        if (caseRowNum == -1) {
            caseRowNum = sheet.getLastRowNum() + 2;
            caseRow = sheet.createRow(caseRowNum);
            XSSFCell caseCell = caseRow.createCell(0);
            caseCell.setCellStyle(getBoldStyle());
            caseCell.setCellValue(itemName);
            sheet.autoSizeColumn(caseCell.getColumnIndex());
        } else {
            caseRow = sheet.getRow(caseRowNum);
        }

        int newColIndex = caseRow.getLastCellNum();
        if (newColIndex < 1) {
            newColIndex = 1;
        }

        XSSFRow headerRow = sheet.getRow(caseRowNum);
        if (headerRow == null) {
            headerRow = sheet.createRow(1);
            headerRow.setRowStyle(setBoldBorders());
        }
        XSSFCell dateCell = headerRow.createCell(newColIndex);
        dateCell.setCellValue(date);
        dateCell.setCellStyle(setBoldBorders());
        sheet.autoSizeColumn(dateCell.getColumnIndex());


        XSSFRow volumeRow = sheet.getRow(caseRowNum + 1);
        if (volumeRow == null) {
            volumeRow = sheet.createRow(caseRowNum + 1);
        }
        XSSFRow medianPriceRow = sheet.getRow(caseRowNum + 2);
        if (medianPriceRow == null) {
            medianPriceRow = sheet.createRow(caseRowNum + 2);
        }
        XSSFRow lowestPriceRow = sheet.getRow(caseRowNum + 3);
        if (lowestPriceRow == null) {
            lowestPriceRow = sheet.createRow(caseRowNum + 3);
        }
        System.out.println(volume  + " " + medianPrice + " " + lowestPrice);

        XSSFCell volumeCell = volumeRow.createCell(newColIndex);
        volumeCell.setCellStyle(setBoldBorders());
        volumeCell.setCellValue(volume);
        XSSFCell volumeLabel = volumeRow.createCell(0);
        volumeLabel.setCellValue("Volume");
        volumeLabel.setCellStyle(setBoldBorders());


        XSSFCell medianPriceCell =  medianPriceRow.createCell(newColIndex);
        medianPriceCell.setCellStyle(setBoldBorders());
        medianPriceCell.setCellValue(medianPrice);
        XSSFCell medianLabel = medianPriceRow.createCell(0);
        medianLabel.setCellValue("Median Price");
        medianLabel.setCellStyle(setBoldBorders());


        XSSFCell lowestPriceCell =  lowestPriceRow.createCell(newColIndex);
        lowestPriceCell.setCellStyle(setBoldBorders());
        lowestPriceCell.setCellValue(lowestPrice);
        XSSFCell lowestLabel = lowestPriceRow.createCell(0);
        lowestLabel.setCellValue("Lowest Price");
        lowestLabel.setCellStyle(setBoldBorders());

        saveWB();
    }

    private int findCaseRow(XSSFSheet sheet, String caseName) {
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null && row.getCell(0) != null && row.getCell(0).getStringCellValue().equals(caseName)) {
                return i;
            }
        }
        return -1; // Case not found
    }

    private XSSFCellStyle setBoldBorders()
    {
        XSSFCellStyle style = this.wb.createCellStyle();
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        return style;
    }

    private XSSFCellStyle getBoldStyle() {
        XSSFCellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        return style;
    }

    public XSSFWorkbook getWorkbook() {
        return wb;
    }
}
