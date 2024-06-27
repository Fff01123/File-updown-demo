package com.phoebe.controller;

import com.github.pagehelper.PageInfo;
import com.phoebe.common.Result;
import com.phoebe.entity.Book;
import com.phoebe.entity.Params;
import com.phoebe.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/find")
    private Result findBook(Params params){
        PageInfo<Book> books = bookService.findBook(params);
        return Result.success(books);
    }

    @GetMapping()
    public Result getBook() {
        List<Book> list = bookService.getBook();
        Result result = Result.success(list);
        return result ;
    }

    @PostMapping()
    public Result save(@RequestBody Book book) {
        if (book.getId()== null) {
            bookService.add(book);
        } else {
            bookService.update(book);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}") //通过路径传参
    public Result delete(@PathVariable Integer id) {
        bookService.delete(id);
        return Result.success();
    }

}