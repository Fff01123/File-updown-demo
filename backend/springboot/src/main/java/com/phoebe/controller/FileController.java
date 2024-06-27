package com.phoebe.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.phoebe.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 *  文件上传接口
 */
@RestController
@RequestMapping("/files")
public class FileController {

    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/file/";

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        //类级别的同步锁，确保在多线程环境下对FileController类的实例方法进行同步处理，避免并发访问导致的问题。
        synchronized (FileController.class) {
            String flag = System.currentTimeMillis() + "";  //获取时间戳
            String fileName = file.getOriginalFilename();   //获取原文件名
            try {
                if (!FileUtil.isDirectory(filePath)) {     //判断文件夹是否存在
                    FileUtil.mkdir(filePath);           //不存在则创建新文件夹
                }
                // 文件存储形式：时间戳-文件名
                //将上传文件的字节内容写入到指定路径下，文件名形式为时间戳-文件名。
                FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
                System.out.println(fileName + "--上传成功");
                //线程休眠
                Thread.sleep(1L);
            } catch (Exception e) {
                System.err.println(fileName + "--文件上传失败");
            }
            return Result.success(flag);
        }
    }



    /**
     * 获取文件
     */
    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response) {
        //flag 是一个路径参数，表示用户请求的文件标识。通过这个标识，服务器端可以定位到对应的文件，并将其作为附件发送给客户端浏览器进行下载。
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);//判断文件路径是否存在，不存在则创建一个文件夹
        }
        OutputStream os;
        //获取指定文件夹下所有的文件名称
        List<String> fileNames = FileUtil.listFileNames(filePath);
        //代码通过 avatar 变量存储了根据 flag 找到的文件名。
        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(avatar)) {
                //设置响应头的方式告知客户端浏览器以附件形式下载这个文件
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(avatar, "UTF-8"));
                response.setContentType("application/octet-stream");
                //并指定下载文件的名称为 avatar。
                byte[] bytes = FileUtil.readBytes(filePath + avatar);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

}