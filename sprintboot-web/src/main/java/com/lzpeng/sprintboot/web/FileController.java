package com.lzpeng.sprintboot.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

    @GetMapping
    public String file(){
        return "file";
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public void upload(MultipartFile file) throws IOException {
        file.transferTo(new File("E:", file.getOriginalFilename()));
    }

    @PostMapping(value = "/uploads")
    @ResponseBody
    public void uploadManyFile(MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            file.transferTo(new File("E:", file.getOriginalFilename()));
        }
    }

    @GetMapping("/download")
    @ResponseBody
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + "demo.xls");// 设置文件名
        Files.copy(Paths.get("E:", "李志鹏.xls"),response.getOutputStream());
        log.info("下载文件");
    }
}
