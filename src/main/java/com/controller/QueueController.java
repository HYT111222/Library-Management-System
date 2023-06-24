package com.controller;

import com.annotation.PassToken;
import com.service.QueueService;
import com.vo.R;
import com.vo.param.CallnumPram;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/AppointmentQueue")
@AllArgsConstructor
public class QueueController {
    private final QueueService queueService;

    @PostMapping("queuebooklist")
    @PassToken
    public R queryQueue(CallnumPram callNumber) {
        return queueService.queryQueue(callNumber);
    }

    @PostMapping("register")
    @PassToken
    public R queue(String userid,String bookid) {
        return queueService.queue(userid,bookid);
    }


}
