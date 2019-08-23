package cn.qydx.hmdj.service;

import cn.qydx.hmdj.dto.PhoneNumberDto;
import cn.qydx.hmdj.utils.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface PhoneNumberService {

    List<PhoneNumberDto> listPhoneNumber();

    Response<PhoneNumberDto> savePhoneNumber(PhoneNumberDto dto);

    Response<String> savePhoneNumberMuilti(List<PhoneNumberDto> list);

    Integer existPhoneNumberType(PhoneNumberDto dto);

    void downloadPhoneNumber(HttpServletResponse response) throws UnsupportedEncodingException;

    void downloadPhoneNumberByManager(HttpServletResponse response, String manager) throws UnsupportedEncodingException;

    List<PhoneNumberDto> listPhoneNumberByManager(String manager);

    List<PhoneNumberDto> listPhoneNumberByManagerLimit(String manager, int start, int limit);

    Response<String> updatePhoneNumber(PhoneNumberDto dto);

    List<String> managers();

    List<Integer> counts();
}
