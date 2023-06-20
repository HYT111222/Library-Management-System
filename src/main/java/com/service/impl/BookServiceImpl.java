package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Book;
import com.entity.BookSearchLink;
import com.entity.Borrow;
import com.mapper.BookMapper;
import com.mapper.BookSearchLinkMapper;
import com.mapper.BorrowMapper;
import com.service.BookService;
import com.vo.R;
import com.vo.param.BorrowParam;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private final BookMapper bookMapper;
    @Autowired
    private final BookSearchLinkMapper bookSearchLinkMapper;

    @Autowired
    private final BorrowMapper borrowMapper;

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
        QueryWrapper<BookSearchLink> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bookid",borrowParam.getBookId());
        BookSearchLink booksearchlink = bookSearchLinkMapper.selectOne(queryWrapper);
        if(booksearchlink.getBookstate() == "已借出"){
            return r.error("该书籍已被借出");
        }
        else if(booksearchlink.getBookstate() == "已预约"){
            return r.error("该书籍已被预约");
        }
        // 根据bookSearchId找到该类书并将可借阅数量减1
        QueryWrapper<Book> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("booksearchid",booksearchlink.getBooksearchid());
        Book book = bookMapper.selectOne(queryWrapper1);
        if(book.getBookremainder()==0){
            return r.error("书籍剩余数量为0。但在bookSearchLink表中显示为空闲。请进行数据库检查。");
        }
        int num = book.getBookremainder() - 1;
        book.setBookremainder(num);
        // 将该记录写入borrow表中
        Borrow borrow = new Borrow();
        borrow.setUserid(borrowParam.getUserId());
        borrow.setBookid(borrowParam.getBookId());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        borrow.setLoantime(timestamp);
        // 修改该书状态为已经借出
        booksearchlink.setBookstate("已借出");
        r.data("booksearchid",booksearchlink.getBooksearchid());
        System.out.println("借阅图书执行完毕。");
        return r;
    }
}
