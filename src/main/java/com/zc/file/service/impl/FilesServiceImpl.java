package com.zc.file.service.impl;

import com.zc.file.mapper.FilesMapper;
import com.zc.file.pojo.Files;
import com.zc.file.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilesServiceImpl implements FilesService {

    @Autowired
    FilesMapper filesMapper;

    @Override
    public boolean insertFile(Files files) {
        return filesMapper.insertFile(files)>0;
    }

    @Override
    public Files selectFileByUUid(String uid) {
        return filesMapper.selectFileByUUid(uid);
    }
}
