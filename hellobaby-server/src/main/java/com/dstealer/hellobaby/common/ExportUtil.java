package com.dstealer.hellobaby.common;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by LiShiwu on 2/19/2017.
 * Time: 15:33
 * Description: 数据导出工具类
 */
public class ExportUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportUtil.class);
    private static final String LINE_SEPARATOR = "\r\n";
    private static final String CSV_SEPARATOR = ",";

    /**
     * 根据指定文件
     *
     * @param data       要写入文件的数据
     * @param outputFile 欲输出文件路径（如果文件不存在则创建，否则将覆盖原文件）
     * @throws IOException
     */
    public static void generateExcelFile(List<String[]> data, File outputFile) throws IOException, InvalidFormatException {
        if (data != null && data.size() != 0) {
            FileOutputStream fileOutputStream = null;
            SXSSFWorkbook wb = null;
            try {
                LOGGER.info("Data size:{}", data.size());
                wb = new SXSSFWorkbook(1000);
                Sheet sheet = wb.createSheet();
                String[] rowData;
                for (int i = 0, length = data.size(); i < length; i++) {
                    rowData = data.get(i);
                    Row row = sheet.createRow(i);
                    for (int j = 0; j < rowData.length; j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(rowData[j]);
                    }
                    if (i % 5000 == 0) {
                        LOGGER.info("Generate {} of {}", i, length);
                    }
                }
                LOGGER.info("Generation finished");
                fileOutputStream = new FileOutputStream(outputFile);
                wb.write(fileOutputStream);
                fileOutputStream.flush();
            } finally {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (wb != null) {
                    wb.dispose();
                }
            }
        } else {
            throw new IllegalArgumentException("The data can't be blank!");
        }
    }

    /**
     * 根据指定分隔符，向文本文件中写入数据
     *
     * @param data       要写入文件的数据
     * @param outputFile 欲输出文件路径（如果文件不存在则创建，否则将覆盖原文件）
     * @throws IOException
     */
    public static void generateCsvFile(List<String[]> data, File outputFile) throws IOException {
        if (data != null && data.size() != 0) {
            BufferedOutputStream bufferedOutputStream = null;
            try {
                LOGGER.info("Data size:{}", data.size());
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                String[] rows;
                StringBuilder sb;
                for (int i = 0, length = data.size(); i < length; i++) {
                    rows = data.get(i);
                    sb = new StringBuilder();
                    for (int j = 0; j < rows.length - 1; j++) {
                        sb.append(rows[j]).append(CSV_SEPARATOR);
                    }
                    sb.append(rows[rows.length - 1]).append(LINE_SEPARATOR);
                    bufferedOutputStream.write(sb.toString().getBytes());
                    bufferedOutputStream.flush();
                    if (i % 5000 == 0) {
                        LOGGER.info("Generate {} of {}", i, length);
                    }
                }
                LOGGER.info("Generation finished");
            } finally {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            }
        } else {
            throw new IllegalArgumentException("The data can't be blank!");
        }
    }
}
