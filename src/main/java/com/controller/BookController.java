package com.controller;

import com.annotation.PassToken;
import com.annotation.UserLoginToken;
import com.service.BookService;
import com.vo.R;
import com.vo.param.BorrowParam;
import com.vo.param.ReturnBookParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
//@CrossOrigin
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    // 获取所有图书列表
    @GetMapping("getAllBookList")
    @UserLoginToken
    public R getAllBookList(@RequestAttribute(value="id") String id){
        return bookService.getAllBookList();
    }
    // 图书详情查看
    // 待修改，不知道咋写
    @GetMapping("checkBookDetail/{bookSearchId}")
//    @GetMapping("checkBookDetail")
    @UserLoginToken
    public R checkBookDetail(@RequestAttribute(value="id") String id, @PathVariable("bookSearchId") String bookSearchId){
//    public R checkBookDetail(@RequestAttribute(value="id") String id,@RequestParam("bookSearchId") String bookSearchId){
        System.out.println("用户id是:" + id);
        System.out.println("接收到的BookID是:" + bookSearchId);
        return bookService.checkBookDetail(bookSearchId);
//        R r = new R();
//        return r.ok();
    }
    // 借阅图书
    @PostMapping("borrow")
    @PassToken
    public R borrow(@RequestBody BorrowParam borrowParam) {
        return bookService.borrow(borrowParam);
    }

    @PutMapping("return")
    @UserLoginToken
    public R returnBook(@RequestBody ReturnBookParam returnBookParam) {
        return bookService.returnBook(returnBookParam);
    }

    // 获取所有图书列表
    @GetMapping("/search/{searchItem}/{itemInfo}")
    @UserLoginToken
    public R searchBookList(@PathVariable(value="searchItem") String searchItem ,@PathVariable(value="itemInfo") String itemInfo) {
        return bookService.searchBookList(searchItem,itemInfo);
    }


    //wxy
    @GetMapping("/getBookComment/{bookSearchId}")
    @UserLoginToken
    public R getBookComment(@PathVariable(value="bookSearchId") String bookSearchId){
        return bookService.getBookComment(bookSearchId);
    }

    @GetMapping("/getBorrow")
    @UserLoginToken
    public R getBorrow(@RequestAttribute(value="id") String id){
        return bookService.getBorrow(id);
    }

    @GetMapping("/getBorrowHis")
    @UserLoginToken
    public R getBorrowHis(@RequestAttribute(value="id") String id){
        return bookService.getBorrowHis(id);
    }


}
