package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Book;
import com.vo.R;
import com.vo.param.BorrowParam;

public interface BookService extends IService<Book> {
    // 获取所有图书列表
    R getAllBookList();

    // 图书详情查看
    R checkBookDetail(String bookSearchId);

    // 借阅图书
    R borrow(BorrowParam borrowParam);
}
