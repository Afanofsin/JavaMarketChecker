package com.marketcheck.marketchecker.excel;

import org.springframework.stereotype.Service;


@Service
public class ExcelProcessManager
{
    private ExcelFileService excel;


    public  ExcelProcessManager(ExcelFileService excel)
    {
        this.excel = excel;
    }

    public void addCase(String caseName, String date, String volume, String medianPrice, String lowestPrice, String sheetName) {
        excel.writeCaseData(caseName, date, volume, medianPrice, lowestPrice, sheetName);
    }

}
