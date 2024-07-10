package com.app.lms.framework;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Excel {

    private final Workbook workbook;
    private final Sheet sheet;

    public Excel(){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Sheet1");
    }

    public Excel(String sheetName){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
    }

    public void addHeaders(List<String> headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
        }
    }

    public void addRow(List<Object> data){
        int rowNum = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowNum);
        for (int i = 0; i < data.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(data.get(i).toString());
        }
    }

    public ByteArrayResource getByteResource() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] excelBytes = bos.toByteArray();
        bos.close();
        workbook.close();
        return new ByteArrayResource(excelBytes);
    }

}
