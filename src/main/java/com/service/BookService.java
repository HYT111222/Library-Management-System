package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Book;
import com.vo.R;
import com.vo.param.BorrowParam;
import com.vo.param.ReturnBookParam;
import com.annotation.UserLoginToken;
import com.entity.User;
import com.vo.param.*;


public interface BookService extends IService<Book> {
    // 获取所有图书列表
    R getAllBookList();

    // 图书详情查看
    R checkBookDetail(String bookSearchId);

    // 借阅图书
    R borrow(BorrowParam borrowParam);

    R returnBook(ReturnBookParam returnBookParam);

    R searchBookList(String searchItm,String itemList);

    R getBookComment(String bookSearchId);

    R getBorrow(String id);

    R getBorrowHis(String id);
}
