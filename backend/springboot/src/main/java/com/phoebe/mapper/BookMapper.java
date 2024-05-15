package com.phoebe.mapper;

import com.phoebe.entity.Book;
import com.phoebe.entity.Params;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {

    //需要进行分页装配，所以要接收一个params参数（从前端）
    List<Book> findBookBySearch(@Param("params") Params params);


    //删除
    @Delete("delete * from book where id = #{id} }")
    void delete(@Param("id") Integer id);


    @Insert("insert into book (id,name,price,author,press,img) " +
            "values (#{id},#{name},#{price},#{author},#{press},#{img});")
    void insertSelective(Book book);


    @Update("update book set name = #{name} ,price = #{price} , author = #{author} , press = #{press} ,img = #{img} where id = #{id}")
    void update(Book book);


    @Select("select * from book")
    List<Book> getBook();
}
