package com.henu.service.interfaces;

import com.henu.bean.UserInfo;

/**
 * Created by Administrator on 2017/10/18.
 */
public interface UserInfoSV {
    public int insertUserInfo(UserInfo userInfo);
    public UserInfo selectOneByUserName(String uername);
}
