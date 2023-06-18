package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Library;
import com.mapper.LibraryMapper;
import com.service.LibraryService;
import com.vo.R;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LibraryServiceImpl extends ServiceImpl<LibraryMapper, Library> implements LibraryService {
    @Autowired
    private final LibraryMapper libraryMapper;

    // 查看图书馆信息
    @Autowired
    public R checkLibraryInfo(){
        R r = new R();
        return r;
    }
}
