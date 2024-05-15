package com.phoebe.mapper;

import com.phoebe.entity.Params;
import com.phoebe.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    // 1. 基于注解的方式
    //@Select("select * from user")
    List<User> getUser();

    List<User> searchUser(@Param("params") Params params);

    @Insert("insert into user(id,name,password,sex,age,phone) values(#{id},#{name},#{password},#{sex},#{age},#{phone})")
    int add(User user); //添加user

    @Select("select * from user where name = #{name} limit 1")
    User findByName(@Param("name") String name);


    @Select("select * from user where name = #{name} and  password = #{password} limit 1")
    User findByNameAndPassword(@Param("name") String name, @Param("password") String password);


    @Insert("insert into user(id,name,password,sex,age,phone) values(#{id},#{name},#{password},#{sex},#{age},#{phone})")
    int register(User user); //添加user


    @Select("select * from user WHERE  id = #{id} ")
    User findById(@Param("id") int id);



    @Update("update user set name = #{user.name}, password = #{user.password}, sex = #{user.sex}, age = #{user.age}, phone = #{user.phone} where id = #{user.id}")
    void update(@Param("user") User user);


    void insert(User user);

    @Delete("delete  from user WHERE id = #{id} ")
    void delete(@Param("id") int id);
}
