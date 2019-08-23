package cn.qydx.hmdj.service.impl;

import cn.qydx.hmdj.constant.ResponseCode;
import cn.qydx.hmdj.dao.PhoneNumberMapper;
import cn.qydx.hmdj.dto.PhoneNumberDto;
import cn.qydx.hmdj.service.PhoneNumberService;
import cn.qydx.hmdj.utils.Response;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private PhoneNumberMapper mapper;

    @Override
    public List<PhoneNumberDto> listPhoneNumber() {
        return mapper.listPhoneNumber();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Response<PhoneNumberDto> savePhoneNumber(PhoneNumberDto dto) {
        Integer code = existPhoneNumberType(dto);
        Response<PhoneNumberDto> response = new Response<>(dto, code);
        if (ResponseCode.REQUEST_SUCCESS == code) {
            mapper.savePhoneNumber(dto);
        }
        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Response<String> savePhoneNumberMuilti(List<PhoneNumberDto> list) {
        for (PhoneNumberDto dto : list) {
            savePhoneNumber(dto);
        }
        return new Response<>("success", ResponseCode.REQUEST_SUCCESS);
    }

    @Override
    public Integer existPhoneNumberType(PhoneNumberDto dto) {
        if (mapper.getPhoneNumberByLongNumber(dto) != null &&
                mapper.getPhoneNumberByLongNumber(dto).size() > 0) {
            return ResponseCode.REQUEST_REPEAT_LONG_NUMBER;
        }
        if (mapper.getPhoneNumberByShortNumber(dto) != null &&
                mapper.getPhoneNumberByShortNumber(dto).size() > 0) {
            return ResponseCode.REQUEST_REPEAT_SHORT_NUMBER;
        }
        return ResponseCode.REQUEST_SUCCESS;
    }

    private void writeExcel(String fileName, HttpServletResponse response, List<PhoneNumberDto> dtos) throws UnsupportedEncodingException {
        //响应格式
        response.setContentType("application/octet-stream");
        //中文文件名
        fileName = new String(fileName.getBytes("utf-8"), "ISO8859_1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = workbook.createSheet("sheet1");
        //添加表格列名 姓名；长号；短号
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("移动长号");
        row.createCell(2).setCellValue("移动短号");
        row.createCell(3).setCellValue("客户经理");
        //封装数据
        for (int i = 0; i < dtos.size(); i++) {
            HSSFRow valueRow = sheet.createRow(i + 1);
            PhoneNumberDto dto = dtos.get(i);
            valueRow.createCell(0).setCellValue(dto.getName());
            valueRow.createCell(1).setCellValue(dto.getLongNumber());
            valueRow.createCell(2).setCellValue(dto.getShortNumber());
            valueRow.createCell(3).setCellValue(dto.getCustomManager());
        }
        try {
            OutputStream out = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            workbook.write(bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("success");
    }

    @Override
    public void downloadPhoneNumber(HttpServletResponse response) throws UnsupportedEncodingException {
        String fileName = "号码归总.xls";
        List<PhoneNumberDto> dtos = listPhoneNumber();
        writeExcel(fileName, response, dtos);
    }

    @Override
    public void downloadPhoneNumberByManager(HttpServletResponse response, String manager) throws UnsupportedEncodingException {
        String fileName = "号码归总-" + manager + ".xls";
        List<PhoneNumberDto> dtos = listPhoneNumberByManager(manager);
        writeExcel(fileName, response, dtos);
    }

    public List<PhoneNumberDto> listPhoneNumberByManager(String manager) {
        return mapper.listPhoneNumberByManager(manager);
    }

    public List<PhoneNumberDto> listPhoneNumberByManagerLimit(String manager, int start, int limit) {
        return mapper.listPhoneNumberByManagerLimit(manager, start, limit);
    }

    @Override
    public Response<String> updatePhoneNumber(PhoneNumberDto dto) {
        mapper.updatePhoneNumber(dto);
        return new Response<>("success", ResponseCode.REQUEST_SUCCESS);
    }

    @Override
    public List<String> managers() {
        return mapper.managers();
    }

    @Override
    public List<Integer> counts() {
        return mapper.counts();
    }
}
