package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Appointment;

import com.entity.BookSearchLink;
import com.mapper.AppointMapper;

import com.mapper.BookSearchLinkMapper;
import com.service.Appointservice;

import com.vo.R;
import com.vo.param.AppointParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class AppointServiceImpl extends ServiceImpl<AppointMapper, Appointment> implements Appointservice {
    @Autowired
    private final AppointMapper appointMapper;
    @Autowired
    private final BookSearchLinkMapper bookSearchLinkMapper;
    @Override
    public R appointList(String id){
        System.out.println("获取当前预约列表信息开始执行");
        R r = new R();
        r.data("success",false);
        List<Map<String, Object>> appointList = new ArrayList<>();
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", id);
        List<Appointment> list= appointMapper.selectList(queryWrapper);
        if(list.size()>0){
            r.data("success",true);
            r.data("message","获取成功");
        }

        for (Appointment appointment : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("shelfId", appointment.getBookid());
            map.put("shelfCapacity", appointment.getAppointmenttime());
            map.put("shelfAllowance", appointment.getAppointstate());

            appointList.add(map);
        }

        r.data("appointlist", appointList);
        System.out.println(r);
        return r.ok();

    }

    @Override
    public R appoint(String id,AppointParam appointParam){
        System.out.println("获取当前预约信息开始执行");
        R r = new R();
        r.data("success",false);
        QueryWrapper<BookSearchLink> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("booksearchid", appointParam.getCallNumber());
        List<BookSearchLink> list= bookSearchLinkMapper.selectList(queryWrapper);
        String bookID = "";
        for (BookSearchLink bookSearchLink : list) {
            if(bookSearchLink.getBookstate()=="空闲"){
                bookID = bookSearchLink.getBookid();
               break;
            }
        }
        if(bookID!=""){
            // 更改书籍状态
            UpdateWrapper<BookSearchLink> updateWrapper1 = new UpdateWrapper<>();
            updateWrapper1.set("bookstate","已预约");
            updateWrapper1.eq("bookid",bookID);
            //写入预约表
            Appointment appointment = new Appointment();
            appointment.setUserid(id);
            appointment.setBookid(bookID);
            appointment.setAppointstate("预约中");
            Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String appointTime = dateFormat.format(date);//开始时间
            appointment.setAppointmenttime(appointTime);
            r.data("success",true);
            r.data("message","预约成功");
//            if(bookSearchLinkMapper.update(null,updateWrapper1)==0){
//                return r.error("503","unknown error02");
//            }
        }
        else{
            r.data("success",false);
            r.data("message","预约失败，该书已无余量，您可以尝试排队");
        }
        return r.ok();
    }

    @Override
    public R appointBookList(String booksearchId){
        System.out.println("获取当前预约信息开始执行");
        R r = new R();
        r.data("success",false);
        List<Map<String, Object>> appointList = new ArrayList<>();
        QueryWrapper<BookSearchLink> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("booksearchid", booksearchId);
        List<BookSearchLink> list= bookSearchLinkMapper.selectList(queryWrapper);
        if(list.size()>0){
            r.data("success",true);
        };
        for (BookSearchLink bookSearchLink : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("bookid", bookSearchLink.getBookid());
            map.put("appointstate", bookSearchLink.getBookstate());

            appointList.add(map);
        }

        r.data("appointlist", appointList);
        System.out.println(r);
        return r.ok();
    }

}
