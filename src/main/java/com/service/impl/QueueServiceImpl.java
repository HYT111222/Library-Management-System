package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.QueueManagement;
import com.entity.User;
import com.mapper.QueueMapper;
import com.service.QueueService;
import com.vo.R;
import com.vo.param.CallnumPram;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QueueServiceImpl extends ServiceImpl<QueueMapper, QueueManagement> implements QueueService {
    @Autowired
    private final QueueMapper queueMapper;

    @Override
    public R queryQueue(CallnumPram callNumber) {
        System.out.println("查看排队信息开始执行");
        R r = new R();
        // 查询
        QueryWrapper<QueueManagement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("queueid",callNumber);
        List<QueueManagement> que =new ArrayList<>();
        que=queueMapper.selectList(queryWrapper);
        QueueManagement queue = queueMapper.selectOne(queryWrapper);
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
                que.get(i).setQueuestate(-1);
            }
        }
        if(flag2==0){
            for(int i=0;i<que.size();i++){
                if(que.get(i).getQueuestate()==1){
                    que.get(i).setQueuestate(0);
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
        QueryWrapper<QueueManagement> queryWrapper = new QueryWrapper<>();
        QueueManagement que=new QueueManagement();
        User user=new User();
        queryWrapper.orderByDesc("queueid");
        que = queueMapper.selectOne(queryWrapper);
        int lastqueueid= que.getQueueid();
        int queueid=lastqueueid+1;
        que.setQueueid(queueid);
        que.setUserid(userid);
        que.setBooksearchid(bookid);
        que.setQueuestate(1);
        queueMapper.insert(que);
        r.data("data",que);
        System.out.println("success");
        return r.ok();
    }
    //extends ServiceImpl<QueueMapper, Queue>
}
