package com.marketcheck.marketchecker.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ExcelFileService
{
    private final String EXCEL_FILE_NAME = "PriceStatistics.xlsx";
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
            wb.createSheet("Case Prices");
            wb.createSheet("Skin Prices");
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

    public XSSFWorkbook getWorkbook() {
        return wb;
    }
}
