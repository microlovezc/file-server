package com.zc.file.controller;

import com.alibaba.fastjson.JSONObject;
import com.zc.file.pojo.Files;
import com.zc.file.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RestController
@RequestMapping("/file")
public class FilesController {

    @Autowired
    private FilesService filesService;

    /*
     * 上传文件
     * */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Object addSong(@RequestParam("file") MultipartFile mpfile){
        JSONObject jsonObject = new JSONObject();


        if (mpfile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        //文件名等于当前时间到毫秒+原来的文件名
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        UUID uuid = UUID.randomUUID();
        //获取文件的后缀名 .jpg
        String originalFilename = mpfile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileType=originalFilename.substring(originalFilename.lastIndexOf(".")+1,mpfile.getOriginalFilename().length());

        System.out.println("suffix = " + suffix);
        String fileName = uuid + suffix;
        //文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "store" + File.separator + format;
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //存储到数据库里的相对文件地址
        String storeUrlPath = "store\\"+format+"\\"+fileName;
        try {
            mpfile.transferTo(dest);
            Files files = new Files();
            files.setUid(uuid.toString());
            files.setSize((int) mpfile.getSize());
            files.setType(fileType);
            files.setName(mpfile.getOriginalFilename());
            files.setCreateTime(new Date());
            files.setAddr(storeUrlPath);
            boolean res = filesService.insertFile(files);
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("UUid", uuid.toString());
                jsonObject.put("msg", "上传成功");
                return jsonObject;
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");
                return jsonObject;
            }
        } catch (IOException e) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败" + e.getMessage());
            return jsonObject;
        } finally {
            return jsonObject;
        }
    }

    /*
    * 获取文件信息
    * */
    @RequestMapping(value = "/getFileInfo",method = RequestMethod.GET)
    public Object getFileInfo(@RequestParam("uid")String uid){
        Files files = filesService.selectFileByUUid(uid);
        String s = JSONObject.toJSONString(files);
        return s;
    }

    /*
     * 获取文件信息
     * */
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public Object downloadFile(@RequestParam("uid")String uid, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Files files = filesService.selectFileByUUid(uid);

        System.out.println(files);

        String filepath = files.getAddr();

        File file = new File(filepath);

        response.setContentLength((int)file.length());
        response.setHeader("Accept-Ranges", "bytes");
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(file), 4096);

            OutputStream os = new BufferedOutputStream(response.getOutputStream());

            byte[] bytes = new byte[4096];
            int i = 0;
            while ((i = in.read(bytes)) > 0) {
                os.write(bytes, 0, i);
            }
            System.out.println(response);

            os.flush();
            os.close();
        }catch (Exception e){
            jsonObject.put("code",410);
            return jsonObject;
        }
        return jsonObject;

    }



}
