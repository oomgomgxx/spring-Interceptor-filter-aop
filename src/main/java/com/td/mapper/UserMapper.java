package com.td.mapper;

import com.td.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserMapper {

    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    @Insert("insert into user(name,age) values(#{name}, #{age})")
    void insert(User user);
}
