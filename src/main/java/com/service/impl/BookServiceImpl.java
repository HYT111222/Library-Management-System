package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Appointment;
import com.entity.Book;
import com.entity.Booksearchlink;
import com.entity.Borrow;
import com.mapper.AppointmentMapper;
import com.mapper.BookMapper;
import com.mapper.BooksearchlinkMapper;
import com.mapper.BorrowMapper;
import com.service.BookService;
import com.vo.R;
import com.vo.param.BorrowParam;
import com.vo.param.ReturnBookParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import java.sql.Timestamp;
import com.entity.*;
import com.mapper.*;
import com.service.BookService;
import com.service.UserService;
import com.vo.R;
import com.vo.param.*;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private final BookMapper bookMapper;
    @Autowired
    private final BooksearchlinkMapper bookSearchLinkMapper;

    @Autowired
    private final BorrowMapper borrowMapper;

    @Autowired
    private final AppointmentMapper appointmentMapper;

    // 获取所有图书列表
    @Override
    public R getAllBookList(){
        System.out.println("获取所有图书列表开始执行");
        R r = new R();

        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        List<Book> list = bookMapper.selectList(queryWrapper);
        List<Map<String, Object>> bookList = new ArrayList<>();

        for (Book book : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("bookName",book.getBookname());
            map.put("author",book.getAuthor());
            map.put("bookSearchId",book.getBooksearchid());
            map.put("press",book.getPress());
            map.put("averageScore",String.valueOf(book.getAveragescore()));
            map.put("bookNumber",String.valueOf(book.getBooknumber()));
            map.put("remainder",String.valueOf(book.getBookremainder()));
            bookList.add(map);
        }
        r.data("bookList",bookList);
        if(bookList.isEmpty()){
            return r.error("图书列表为空！");
        }
        System.out.println("获取所有图书列表执行完毕");
        return r.ok();
    }

    // 图书详情查看
    @Override
    public R checkBookDetail(String bookSearchId){
        System.out.println("图书详情查看开始执行");
        R r = new R();
        // 根据索书号进行查询
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("booksearchid",bookSearchId);
        // 返回
        Book book = bookMapper.selectOne(queryWrapper);
//        Map<String,Object> map = new HashMap<>();
//        map.put("bookSearchId",book.getBooksearchid());
//        map.put("author",book.getAuthor());
//        map.put("bookName",book.getBookname());
//        map.put("press",book.getPress());
//        map.put("language",book.getLanguage());
//        map.put("abstract",book.getContentabstract());
//        map.put("averageScore",book.getAveragescore());
//        map.put("regionName",book.getRegionname());
//        map.put("pageNumber",book.getPagenumber());
//        map.put("bookNumber",book.getBooknumber());
//        map.put("bookAllowrance",book.getBookremainder());
        r.data("bookInfo",book);
        System.out.println("Book: "+String.valueOf(book));
        System.out.println("图书详情查看执行完毕");
        return r.ok();
    }

    // 借阅图书
    @Override
    public R borrow(BorrowParam borrowParam){
        System.out.println("借阅图书开始执行");
        R r = new R();
        // 根据bookid 获取对应的bookstate
        QueryWrapper<Booksearchlink> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bookid",borrowParam.getBookId());
        Booksearchlink booksearchlink = bookSearchLinkMapper.selectOne(queryWrapper);
        if(booksearchlink.getBookstate() == "已借出"){
            return r.error("该书籍已被借出");
        }
        else if(booksearchlink.getBookstate() == "已预约"){
            // 看是不是预约人
            QueryWrapper<Appointment> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("bookid",borrowParam.getBookId());
            queryWrapper1.eq("userid",borrowParam.getUserId());
            Appointment appointment = appointmentMapper.selectOne(queryWrapper1);
            if(appointment == null){
                return r.error("该书籍已被他人预约");
            }
            System.out.println("appointment: " + appointment);
            appointment.setAppointstate("预约结束");
        }
        // 根据bookSearchId找到该类书并将可借阅数量减1
        QueryWrapper<Book> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("booksearchid",booksearchlink.getBooksearchid());
        Book book = bookMapper.selectOne(queryWrapper2);
        if(book.getBookremainder()==0){
            return r.error("书籍剩余数量为0。但在bookSearchLink表中显示为空闲。请进行数据库检查。");
        }
        int num = book.getBookremainder() - 1;
        book.setBookremainder(num);
        // 将该记录写入borrow表中
        Borrow borrow = new Borrow();
        borrow.setUserid(borrowParam.getUserId());
        borrow.setBookid(borrowParam.getBookId());
        String lonatime = getStringDate();
        borrow.setLoantime(lonatime);
        System.out.println("LoanTime:" + lonatime);
        // 修改该书状态为已经借出
        booksearchlink.setBookstate("已借出");
        r.data("booksearchid",booksearchlink.getBooksearchid());
        System.out.println("借阅图书执行完毕。");
        return r;
    }

    // 获取当前时间
    private String getStringDate(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    @Override
    public R returnBook(ReturnBookParam returnBookParam){
        R r = new R();
        System.out.println(returnBookParam.getBookid());
        System.out.println(returnBookParam.getEvaluateText());
        System.out.println(returnBookParam.getScore());
        if(returnBookParam.getBookid() == "")
            return r.error("404","记录不存在");
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String returnTime = dateFormat.format(date);//开始时间
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bookid",returnBookParam.getBookid());
        queryWrapper.isNull("returntime");
        if(borrowMapper.exists(queryWrapper)){
            // 更新借阅表的信息
            UpdateWrapper<Borrow> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("returntime",returnTime);
            updateWrapper.set("score",returnBookParam.getScore());
            updateWrapper.set("evaluatetext",returnBookParam.getEvaluateText());
            updateWrapper.eq("bookid",returnBookParam.getBookid());
            updateWrapper.isNull("returntime");
            if(borrowMapper.update(null, updateWrapper)==0){
                return r.error("503","unknown error01" );
            }

            // 获取当前书籍的均分
            QueryWrapper<Borrow> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("bookid",returnBookParam.getBookid());
            queryWrapper1.select("score");
            List<Borrow> borrows = borrowMapper.selectList(queryWrapper1);
            float score = 0;
            for(int i = 0; i<borrows.size();i++){
                score = score + borrows.get(i).getScore();
            }
            score = score/borrows.size();


            // 寻找对应书籍的索书号
            QueryWrapper<BookSearchLink> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("bookid",returnBookParam.getBookid());
            BookSearchLink bookSearchLink = bookSearchLinkMapper.selectOne(queryWrapper2);
            // 更改书籍状态
            UpdateWrapper<BookSearchLink> updateWrapper1 = new UpdateWrapper<>();
            updateWrapper1.set("bookstate","空闲");
            updateWrapper1.eq("bookid",returnBookParam.getBookid());
            if(bookSearchLinkMapper.update(null,updateWrapper1)==0){
                return r.error("503","unknown error02");
            }
            // 更改书籍均分以及当前剩余数量
            UpdateWrapper<Book> updateWrapper2 = new UpdateWrapper<>();
            updateWrapper2.set("averagescore",score);
            updateWrapper2.setSql("bookremainder = bookremainder + 1");
            updateWrapper2.eq("booksearchid",bookSearchLink.getBooksearchid());
            if(bookMapper.update(null,updateWrapper2)==0){
                return r.error("503","unknown error03");
            }
            return r.ok();
        }
        else{
            return r.error("404","记录不存在");
        }
    }

    @Override
    public R searchBookList(String searchItem,String itemInfo){
        R r=new R();
        if(searchItem == null || itemInfo == null)
            return r.error("404","缺少搜索信息");
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if(searchItem.equals("author")){
            queryWrapper.eq("author",itemInfo);
        }else if(searchItem.equals("bookname")){
            queryWrapper.eq("bookname",itemInfo);
        }else if(searchItem.equals("booksearchid")){
            queryWrapper.eq("booksearchid",itemInfo);
        }else if(searchItem.equals("press")){
            queryWrapper.eq("press",itemInfo);
        }
        else {
            return r.error("404","搜索信息不存在");
        }
        List<Book> books = bookMapper.selectList(queryWrapper);
        return r.ok().data("bookList",books);
    }

    @Override
    public R getBookComment(String bookSearchId){
        R r = new R();
        // 获取对应索书号的藏书号
        QueryWrapper<BookSearchLink> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("booksearchid",bookSearchId);
        queryWrapper.select("bookid");
        List<BookSearchLink> bookids = bookSearchLinkMapper.selectList(queryWrapper);
        if(bookids.size()==0)
            return r.error("404","图书不存在");
        // 查找评论
        QueryWrapper<Borrow> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("loantime","score","evaluatetext");
        queryWrapper1.isNotNull("returntime");
        for(int i = 0; i< bookids.size(); i++){
            queryWrapper1.eq("bookid",bookids.get(i).getBookid()).or();
        }
        List<Borrow> borrows = borrowMapper.selectList(queryWrapper1);
        return r.ok().data("bookComments",borrows);
    }


    @Override
    public R getBorrow(String id){
        R r = new R();
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",id);
        queryWrapper.isNull("returntime");
        List<Borrow> borrows = borrowMapper.selectList(queryWrapper);
        return r.ok().data("borrow",borrows);
    }

    public R getBorrowHis(String id){
        R r = new R();
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",id);
        queryWrapper.isNotNull("returntime");
        List<Borrow> borrows = borrowMapper.selectList(queryWrapper);
        return r.ok().data("borrowHis",borrows);
    }
}
