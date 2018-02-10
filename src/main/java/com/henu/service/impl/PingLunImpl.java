package com.henu.service.impl;

import com.henu.bean.PingLun;
import com.henu.dao.PingLunMapper;
import com.henu.service.interfaces.PingLunSV;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/12/13.
 */
@Service
public class PingLunImpl implements PingLunSV {
    @Resource
    PingLunMapper pingLunDAO;

    @Override
    public int insertPingLun(PingLun pingLun) {
        return pingLunDAO.insertSelective(pingLun);
    }
}
