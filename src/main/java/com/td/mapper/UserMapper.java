package com.td.mapper;

import com.td.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into user(id,name,age) values(#{id}, #{name}, #{age})")
    void insert(User user);
}
