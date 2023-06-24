package com.controller;

import com.annotation.PassToken;
import com.annotation.UserLoginToken;
import com.service.Appointservice;
import com.vo.R;
import com.vo.param.AppointParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/AppointmentQueue")
//@CrossOrigin
@AllArgsConstructor
public class AppointController {
    private final Appointservice appointService;
    // 查看当前用户预约信息
    @GetMapping("appointList")
    @UserLoginToken
    public R appointList(@RequestAttribute(value="id") String id){
        System.out.println("成功进入查看当前用户预约信息界面");
        return appointService.appointList(id);
    }
    //为当前用户预约想要的图书
    @PostMapping("appoint")
    @UserLoginToken
    public R appoint(@RequestAttribute(value="id") String id,@RequestBody AppointParam appointParam) {
        return appointService.appoint(id,appointParam);
    }

    //查看当前书籍预约情况
    @GetMapping("appointBookList")
    @PassToken
    public R appointBookList( @RequestParam("booksearchId") String booksearchId){
        System.out.println("成功进入查看当前用户预约信息界面");
        return appointService.appointBookList(booksearchId);
    }


}
