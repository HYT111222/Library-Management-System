package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mapper.QueueMapper;
import com.service.QueueService;
import com.vo.R;
import com.vo.param.CallnumPram;
import com.vo.param.QueueInfo;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.Queue;
import com.service.QueueService;
import com.entity.*;
import com.mapper.*;
import com.vo.param.*;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class QueueServiceImpl extends ServiceImpl<QueueMapper, Queue> implements QueueService {
    @Autowired
    private final QueueMapper queueMapper;

    @Override
    public R queryQueue(CallnumPram callNumber) {
        System.out.println("查看排队信息开始执行");
        R r = new R();
        // 查询
        QueryWrapper<Queue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("booksearchid",callNumber);
        Queue queue = queueMapper.selectOne(queryWrapper);
        r.data("data",queue);
        System.out.println("success");
        return r.ok();
    }

    @Override
    public R queue(String userid,String bookid) {
        String userID=userid;
        System.out.println("加入队伍");
        R r = new R();
        QueryWrapper<Queue> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("queueid");
        Queue queue = queueMapper.selectOne(queryWrapper);
        int lastqueueid= queue.getQueueid();
        int queueid=lastqueueid+1;
        queue.setQueueid(queueid);
        queue.setUserid(userid);
        queue.setBookid(bookid);
        queue.setQueuestate(0);
        queueMapper.insert(queue);
        r.data("data",queue);
        System.out.println("success");
        return r.ok();
    }
    //extends ServiceImpl<QueueMapper, Queue>
}
