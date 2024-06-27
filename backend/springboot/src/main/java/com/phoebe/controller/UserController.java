package com.phoebe.controller;

import com.github.pagehelper.PageInfo;
import com.phoebe.common.Result;
import com.phoebe.entity.Params;
import com.phoebe.entity.User;
import com.phoebe.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    /**
     * controller里的一个方法，它其实就是我们平常说的web项目的一个接口的入口
     * 可以在这个方法上再加上一个url
     * 也可以指定请求方式：GET POST PUT DELETE
     * @return
     */

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User loginUser = userService.login(user);
        Result result = Result.success(loginUser);
        return result;

    }

    @GetMapping("/load")
    public Result getUser() {
        List<User> list = userService.getUserservice();
        Result result = Result.success(list);
        return result ;
    }

    @GetMapping("/search")
    public Result SearchUser(Params params) {
        PageInfo<User> info = userService.searchUser(params);
        return Result.success(info);
    }

    @PostMapping("save")
    public Result save(@RequestBody User user) {
        if (user.getId() == null) {
            userService.add(user);
        } else {
            userService.update(user);
            System.out.println("the update user" + user);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.delete(id);
        return Result.success();
    }
}