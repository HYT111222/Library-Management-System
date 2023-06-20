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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class QueueServiceImpl extends ServiceImpl<QueueMapper, Queue> implements QueueService {
    @Autowired
    private final QueueMapper queueMapper;
    private final BookSearchLinkMapper bookSearchLinkMapper;

    @Override
    public R queryQueue(CallnumPram callNumber) {
        System.out.println("查看排队信息开始执行");
        R r = new R();
        // 查询
        QueryWrapper<Queue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("queueid",callNumber);
        List<Queue> que =new ArrayList<>();
        que=queueMapper.selectList(queryWrapper);
        Queue queue = queueMapper.selectOne(queryWrapper);
        int flag1=0,flag2=0,flag3=0;//判断排队位置
        for(int i=0;i<que.size();i++){
            if(que.get(i).getQueuestate()==1){
                flag1=1;}
            if(que.get(i).getQueuestate()==0){
                flag2=1;}
            if(que.get(i).getQueuestate()==-1){
                flag3=1;}
        }

        r.data("data",queue);
        System.out.println("success");

        for(int i=0;i<que.size();i++){
            if(que.get(i).getQueuestate()==0){
                que.get(i).setQueuestate()==-1;
            }
        }
        if(flag2==0){
            for(int i=0;i<que.size();i++){
                if(que.get(i).getQueuestate()==1){
                    que.get(i).setQueuestate()==0;
                }
            }
        }
        return r.ok();
    }

    @Override
    public R queue(String userid,String bookid) {
        String userID=userid;
        System.out.println("加入队伍");
        R r = new R();
        QueryWrapper<Queue> queryWrapper = new QueryWrapper<>();
        Queue que=new Queue();
        queryWrapper.orderByDesc("queueid");
        que = queueMapper.selectOne(queryWrapper);
        int lastqueueid= queue.getQueueid();
        int queueid=lastqueueid+1;
        que.setQueueid(queueid);
        que.setUserid(userid);
        que.setBookid(bookid);
        que.setQueuestate(1);
        queueMapper.insert(que);
        r.data("data",que);
        System.out.println("success");
        return r.ok();
    }
    //extends ServiceImpl<QueueMapper, Queue>
}
