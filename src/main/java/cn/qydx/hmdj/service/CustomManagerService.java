package cn.qydx.hmdj.service;

import cn.qydx.hmdj.dto.CustomManagerDto;
import cn.qydx.hmdj.dto.PhoneNumberDto;
import cn.qydx.hmdj.utils.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CustomManagerService {

    Response<List<CustomManagerDto>> listCustomManagerByGroup(int groupId);
}
