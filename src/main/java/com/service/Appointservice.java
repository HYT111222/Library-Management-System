package com.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Appointment;
import com.vo.R;
import com.vo.param.AppointParam;
public interface Appointservice {
    R appointList(String id);
    R appoint(String id,AppointParam appointParam);

}
