package com.luntang.community.community.mapper;


import com.luntang.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.annotation.Resources;


@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
     public void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(String token);
}
