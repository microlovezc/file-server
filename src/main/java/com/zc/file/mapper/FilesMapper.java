package com.zc.file.mapper;

import com.zc.file.pojo.Files;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesMapper {
    //保存文件元数据记录至数据库
    int insertFile(Files files);
    //通过UUid返回数据库查询到的元数据记录
    Files selectFileByUUid(String uid);

}
