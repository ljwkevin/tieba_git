package com.henu.service.impl;

import com.henu.bean.UserSetting;
import com.henu.dao.UserSettingMapper;
import com.henu.service.interfaces.UserSettingSV;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/10/18.
 */
@Service
public class UserSettingImpl implements UserSettingSV {
    @Resource
    UserSettingMapper userSettingMapper;
    @Override
    public int insertUserSetting(UserSetting userSetting) {
        if(userSettingMapper.selectByPrimaryKey(userSetting.getUsername())==null){
            return userSettingMapper.insertSelective(userSetting);
        }
        return 0;
    }
}
