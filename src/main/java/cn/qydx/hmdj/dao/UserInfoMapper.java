package cn.qydx.hmdj.dao;

import cn.qydx.hmdj.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserInfoMapper {

    @Select("select * from t_user where usrname = #{name}")
    UserInfo getUserInfoByName(@Param("name") String name);
}
