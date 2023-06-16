package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Book;
import com.entity.User;
import com.mapper.BookMapper;
import com.mapper.UserMapper;
import com.service.BookService;
import com.service.UserService;
import com.vo.R;
import com.vo.param.BorrowParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private final BookMapper bookMapper;

    // 获取所有图书列表
    @Override
    public R getAllBookList(){
        R r = new R();
        return r;
    }

    @Override
    public R checkBookDetail(){
        R r = new R();
        return r;
    }

    @Override
    public R borrow(BorrowParam borrowParam){
        R r = new R();
        return r;
    }
}
