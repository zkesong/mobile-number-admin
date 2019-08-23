package cn.qydx.hmdj.dao;

import cn.qydx.hmdj.dto.PhoneNumberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PhoneNumberMapper {

    List<PhoneNumberDto> listPhoneNumber();

    List<PhoneNumberDto> listPhoneNumberByManager(String manager);

    void savePhoneNumber(PhoneNumberDto dto);

    List<PhoneNumberDto> getPhoneNumberByLongNumber(PhoneNumberDto dto);

    List<PhoneNumberDto> getPhoneNumberByShortNumber(PhoneNumberDto dto);

    @Select("select * from t_phonenumber where customManager = #{manager} limit #{start},#{limit}")
    List<PhoneNumberDto> listPhoneNumberByManagerLimit(@Param("manager") String manager,
                                                       @Param("start") int start,@Param("limit") int limit);

    @Update("update t_phonenumber set name = #{name}, " +
            "shortNumber=#{shortNumber}, longNumber=#{longNumber}," +
            "customManager=#{customManager} where id = #{id}")
    void updatePhoneNumber(PhoneNumberDto dto);

    @Select("select customManager from t_phonenumber group by customManager")
    List<String> managers();

    @Select("select count(1) from t_phonenumber group by customManager")
    List<Integer> counts();
}
