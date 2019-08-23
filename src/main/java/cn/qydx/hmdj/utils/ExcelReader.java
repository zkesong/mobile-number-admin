package cn.qydx.hmdj.utils;

import cn.qydx.hmdj.dto.PhoneNumberDto;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelReader {
    public List<PhoneNumberDto> readExcel2007(InputStream in) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row;
        int rows = sheet.getPhysicalNumberOfRows();
        DecimalFormat df = new DecimalFormat("0");
        List<PhoneNumberDto> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            list.add(rowToObject(row));
        }
        return list;
    }

    public boolean isExcel2007(File file) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(file);
        Workbook wb = null;
        try {
            wb = new XSSFWorkbook(in);
        } catch (OLE2NotOfficeXmlFileException e1) {
            try {
                if (wb != null)
                    wb.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (wb != null) {
            try {
                wb.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public List<PhoneNumberDto> readExcel2003(InputStream in) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(in);
        HSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        List<PhoneNumberDto> list = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            list.add(rowToObject(row));
        }
        return list;
    }

    protected String replaceBlank(String str) {
        String dest = null;
        if (str == null) {
            return dest;
        } else {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n|&nbsp;");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            return dest;
        }
    }

    private PhoneNumberDto rowToObject(Row row) {
        DecimalFormat df = new DecimalFormat("0");
        String name = row.getCell(0) == null ? "" : row.getCell(0).toString();
        String longNumber = row.getCell(1) == null ? "" : row.getCell(1).toString();
        longNumber = "".equals(longNumber) ? "0" : longNumber;
        longNumber = df.format(Double.valueOf(longNumber));
        String shortNumber = row.getCell(2) == null ? "" : row.getCell(2).toString().split("\\.")[0];
        String customManager = row.getCell(3) == null ? "" : row.getCell(3).toString();
        PhoneNumberDto dto = new PhoneNumberDto();
        dto.setCustomManager(replaceBlank(customManager));
        dto.setLongNumber(replaceBlank(longNumber));
        dto.setShortNumber(replaceBlank(shortNumber));
        dto.setName(replaceBlank(name));
        return dto;
    }

    public List<PhoneNumberDto> read(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        ExcelReader reader = new ExcelReader();
        List<PhoneNumberDto> list;
        if (reader.isExcel2007(file)) {
            list = reader.readExcel2007(in);
        } else {
            list = reader.readExcel2003(in);
        }
        return list;
    }
}