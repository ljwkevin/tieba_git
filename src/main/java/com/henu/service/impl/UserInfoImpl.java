package com.henu.service.impl;

import com.henu.bean.UserInfo;
import com.henu.dao.UserInfoMapper;
import com.henu.service.interfaces.UserInfoSV;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/10/18.
 */
@Service
public class UserInfoImpl implements UserInfoSV {
    @Resource
    UserInfoMapper userInfoMapper;
    @Override
    public int insertUserInfo(UserInfo userInfo) {
        if(userInfoMapper.selectByPrimaryKey(userInfo.getUsername())!=null){
            return userInfoMapper.updateByPrimaryKey(userInfo);
        }
        return userInfoMapper.insertSelective(userInfo);
    }

    @Override
    public UserInfo selectOneByUserName(String uername) {
        return userInfoMapper.selectByPrimaryKey(uername);
    }
}
