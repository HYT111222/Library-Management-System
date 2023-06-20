package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Library;
import com.vo.R;

public interface LibraryService extends IService<Library> {

    // 查看图书馆信息
    R checkLibraryInfo(String libraryId);

}