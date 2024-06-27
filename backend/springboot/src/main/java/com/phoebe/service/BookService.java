package com.phoebe.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoebe.entity.Book;
import com.phoebe.entity.Params;
import com.phoebe.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {
    @Autowired
    private BookMapper bookMapper;


    public PageInfo<Book> findBook(Params params) {
            //开启分页查询
        Page<Object> page = PageHelper.startPage(params.getCurrentPage(), params.getPageSize());
            //接下来的查询会自动按照当前开启的分页设置来查询
        List<Book> list = bookMapper.findBookBySearch(params);
        return PageInfo.of(list);
    }

    /*添加*/
    public void add(Book book) {
        bookMapper.insertSelective(book);
    }

    /*更新*/
    public void update(Book book) {
        bookMapper.update(book);
    }

    /*删除*/
    public void delete(Integer id) {
        bookMapper.delete(id);
    }

    public List<Book> getBook() {return bookMapper.getBook();}
}