package com.henu.service.impl;

import com.henu.bean.*;
import com.henu.dao.*;
import com.henu.service.interfaces.UserSV;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */
@Service
public class UserImpl implements UserSV {
    @Resource
    UserMapper userDao;
    @Resource
    UserInfoMapper userInfoDao;
    @Resource
    UserTiebaMapper userTiebaDao;
    @Resource
    TieBaMapper tieBaDao;
    @Resource
    userUserMapper userUserDao;
    @Override
    public int insertUser(User user) {
        if(userDao.selectByPrimaryKey(user.getUsername())==null){
            user.setPassword("123456");
            return userDao.insertSelective(user);
        }
        return 0;
    }

    @Override
    @CachePut(value = "User",key = "#user.username")
    public User findUserByUserNameAndPwd(User user) throws Exception {
        User userByUserName = userDao.selectByPrimaryKey(user.getUsername());
        if(userByUserName==null){
            throw new Exception("没有这个用户");
        }else{
            if(!userByUserName.getPassword().equals(user.getPassword())){
                throw new Exception("密码不对");
            }
            userByUserName.setUserInfo(userInfoDao.selectByPrimaryKey(userByUserName.getUsername()));
            List<UserTieba> userTiebas = userTiebaDao.selectAllByUserName(userByUserName.getUsername());
            if(userTiebas!=null&&userTiebas.size()>0) {
                for (UserTieba userTieba : userTiebas) {
                    userTieba.setTiebaName(tieBaDao.selectByPrimaryKey(userTieba.getTiebaid()).getName());
                }
            }
            userByUserName.setUserTiebas(userTiebas);
            return userByUserName;
        }
    }

    @Override
    public User findUserByPetName(String UserPetName,String TiebaId) {
        UserInfo userInfo = userInfoDao.selectByPetName(UserPetName);
        User user = userDao.selectByPrimaryKey(userInfo.getUsername());
        List<UserTieba> userTiebas = userTiebaDao.selectByUserName(user.getUsername(),TiebaId);
        if(userTiebas!=null&&userTiebas.size()>0) {
            for (UserTieba userTieba : userTiebas) {
                userTieba.setTiebaName(tieBaDao.selectByPrimaryKey(userTieba.getTiebaid()).getName());
            }
        }
        user.setUserInfo(userInfo);
        user.setUserTiebas(userTiebas);
        return user;
    }

    @Override
    public User findUserAllInfoByPetName(String UserPetName) {
        UserInfo userInfo = userInfoDao.selectByPetName(UserPetName);
        User user = userDao.selectByPrimaryKey(userInfo.getUsername());
        List<UserTieba> userTiebas = userTiebaDao.selectAllByUserName(user.getUsername());
        if(userTiebas!=null&&userTiebas.size()>0) {
            for (UserTieba userTieba : userTiebas) {
                userTieba.setTiebaName(tieBaDao.selectByPrimaryKey(userTieba.getTiebaid()).getName());
            }
        }
        user.setUserInfo(userInfo);
        user.setUserTiebas(userTiebas);
        return user;
    }

    @Override
    public int guanzhuUser(String username1, String username2, int guanzhu) {
        userUser userUser = userUserDao.selectByUsername1And2(username1, username2);
        if(userUser==null){
            userUser = new userUser();
            userUser.setChatrecord("");
            userUser.setGuanzhu(1);
            userUser.setUsername1(username1);
            userUser.setUsername2(username2);
            return userUserDao.insertSelective(userUser);
        }else{
            return userUserDao.updateguanzhu(username1,username2,guanzhu);
        }
    }

    @Override
    public int selectguanzhu(String username1, String username2) {
        userUser userUser = userUserDao.selectByUsername1And2(username1, username2);
        if(userUser==null||userUser.getGuanzhu()==0){
            return 0;
        }
        return 1;
    }

    @Override
    public List<userUser> selectFriend(String username1) {
        LogFactory.useLog4JLogging();
        List<userUser> userUsers = userUserDao.selectFriend(username1);
        List<userUser> userUserList = new ArrayList<>();
        for (userUser userUser:userUsers) {
            if(selectguanzhu(userUser.getUsername2(),username1)==1){
                userUserList.add(userUser);
            }
        }
        return userUserList;
    }
}
