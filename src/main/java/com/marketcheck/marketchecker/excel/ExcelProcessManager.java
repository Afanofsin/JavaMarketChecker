package com.marketcheck.marketchecker.excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.marketcheck.marketchecker.excel.ExcelFileService;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class ExcelProcessManager
{
    private ExcelFileService excel;

    public  ExcelProcessManager(ExcelFileService excel)
    {
        this.excel = excel;
    }



}
