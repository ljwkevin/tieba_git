package com.henu.service.interfaces;

import com.henu.bean.PingLun;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/12/13.
 */
@Service
public interface PingLunSV {
    public int insertPingLun(PingLun pingLun);
}
