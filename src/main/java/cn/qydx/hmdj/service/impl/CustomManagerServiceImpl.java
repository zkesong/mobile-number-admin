package cn.qydx.hmdj.service.impl;

import cn.qydx.hmdj.dao.CustomManagerMapper;
import cn.qydx.hmdj.dao.PhoneNumberMapper;
import cn.qydx.hmdj.dto.CustomManagerDto;
import cn.qydx.hmdj.dto.PhoneNumberDto;
import cn.qydx.hmdj.service.CustomManagerService;
import cn.qydx.hmdj.service.PhoneNumberService;
import cn.qydx.hmdj.utils.Response;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class CustomManagerServiceImpl implements CustomManagerService {

    @Autowired
    private CustomManagerMapper mapper;

    @Override
    public Response<List<CustomManagerDto>> listCustomManagerByGroup(int groupId) {
        return new Response<>(mapper.listCustomManagerByGroup(groupId), 200);
    }
}
