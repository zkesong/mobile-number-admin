package cn.qydx.hmdj.dao;

import cn.qydx.hmdj.dto.CustomManagerDto;
import cn.qydx.hmdj.dto.PhoneNumberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomManagerMapper {

    List<CustomManagerDto> listCustomManagerByGroup(int groupId);
}
