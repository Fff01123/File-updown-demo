package com.phoebe.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoebe.common.JwtTokenUtils;
import com.phoebe.entity.Params;
import com.phoebe.entity.User;
import com.phoebe.exception.CustomException;
import com.phoebe.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUserservice() {
        //return userDao.getUser();
        // 3. 使用引入的包
        return userMapper.getUser();
    }

    //业务层开启分页装配
    public PageInfo<User> searchUser(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getCurrentPage(),params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<User> list = userMapper.searchUser(params);
        return PageInfo.of(list);
    }

    public void add(User user){

        //1.进行重复性的判断，不允许重复添加，只需要根据用户名字去查询一下即可
        User res = userMapper.findByName(user.getName());
        if (res != null){
            //说明重复添加
            throw new CustomException("添加用户已经存在，请勿重复添加！");
        }
        // 初始化一个密码
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        userMapper.insert(user);
    }


    public User login(User user) {
        // 1. 进行一些非空判断
        if (user.getName() == null || "".equals(user.getName())) {
            throw new CustomException("用户名不能为空");
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            throw new CustomException("密码不能为空");
        }
        User res = userMapper.findByNameAndPassword(user.getName(), user.getPassword());
        if(res == null){
            //说明名字密码不匹配
            throw new CustomException("姓名或者密码输入错误！请重新输入");
        }
        // 生成jwt token给前端
        String token = JwtTokenUtils.genToken(res.getId().toString(), res.getPassword());
        res.setToken(token);

        System.out.println("service中："  + res);
        return res;
    }

    public void register(User user) {
        // 1. 进行一些非空判断
        if (user.getName() == null || "".equals(user.getName())) {
            throw new CustomException("用户名不能为空");
        }
      //2.进行重复性判断，同一名字的管理员不允许重复新增，只要根据用户名去查询就ok
        User res = userMapper.findByName(user.getName());
        if(res != null){
            //说明已经有了
            throw new CustomException("注册失败！请重新输入");
        }
        //初始化密码
        if(user.getPassword() == null){
            user.setPassword("12345");
        }
        userMapper.register(user);
    }

    public User findById(int id) {
        User user = userMapper.findById(id);
        return user;
    }


    public void update(User user) {
        userMapper.update(user);
    }

    public void delete(Integer id) {
        userMapper.delete(id);
    }
}