package com.service;

import com.vo.R;
import com.vo.param.CallnumPram;
import com.vo.param.QueueInfo;

public interface QueueService {
    R queryQueue(CallnumPram callNumber);
    R queue(String userID,String bookid);
}
