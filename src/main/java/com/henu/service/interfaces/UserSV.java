package com.henu.service.interfaces;

import com.henu.bean.User;
import com.henu.bean.userUser;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */
public interface UserSV {
    public int insertUser(User user);
    public User findUserByUserNameAndPwd(User user) throws Exception;
    public User findUserByPetName(String UserPetName,String TieBaId);
    public User findUserAllInfoByPetName(String UserPetName);
    public int guanzhuUser(String username1,String username2,int guanzhu);
    public int selectguanzhu(String username1,String username2);
    public List<userUser> selectFriend(String username1);
}
