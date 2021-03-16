package com.zc.file.service;

import com.zc.file.pojo.Files;

public interface FilesService {
    //保存文件元数据记录至数据库
    boolean insertFile(Files files);
    //通过UUid返回数据库查询到的元数据记录
    Files selectFileByUUid(String uid);
}
