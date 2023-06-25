package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    @Override
    public R checkLibraryInfo(String libraryId){
        System.out.println("查看图书馆信息开始执行");
        R r = new R();
        // 查询
        QueryWrapper<Library> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("libraryid",libraryId);
        Library library = libraryMapper.selectOne(queryWrapper);
        if(library == null){
            System.out.println("图书馆为空");
            return r.error("404","没有该图书馆");
        }
        r.data("data",library);
        System.out.println("图书馆信息： " + library);
        System.out.println("查看图书馆信息执行完毕");
        r.setCode("200");
        return r.ok();
    }
}
