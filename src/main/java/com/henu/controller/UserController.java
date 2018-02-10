package com.henu.controller;

import com.henu.bean.Category;
import com.henu.bean.TieBa;
import com.henu.bean.User;
import com.henu.bean.userUser;
import com.henu.service.interfaces.*;
import com.henu.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */
@Controller
@RequestMapping("/tieba")
public class UserController {
    @Resource
    UserSV userSV;
    @Resource
    UserInfoSV userInfoSV;
    @Resource
    TieBaSV tieBaSV;
    @Autowired
    RedisUtil redisUtil;
    @Resource
    CategorySV categorySv;
    @RequestMapping("/login")
    public String login(User user, HttpSession session,Model model) throws Exception {
        List<Category> categories = categorySv.selectByLevel(1);
        model.addAttribute("CategoriesOne", categories);
        user = userSV.findUserByUserNameAndPwd(user);
        session.setAttribute("user",user);
        session.setAttribute("userInfo",userInfoSV.selectOneByUserName(user.getUsername()));
        List<TieBa> tiebas = tieBaSV.selectTieBaPage(0);
        model.addAttribute("Tiebas",tiebas);
        return "/homePage";
    }
    @RequestMapping("/toHomePage")
    public String toHomePage(Model model){
        List<Category> categories = categorySv.selectByLevel(1);
        model.addAttribute("CategoriesOne", categories);
        List<TieBa> tiebas = tieBaSV.selectTieBaPage(0);
        model.addAttribute("Tiebas",tiebas);
        return "/homePage";
    }
    @RequestMapping("/openWebSocket")
    public String openWebSocket(String userPetname,HttpSession session,Model model){
        if(userPetname!=null&&!userPetname.equals("")){
            model.addAttribute("toUserName",userPetname);
        }
        User user = (User)session.getAttribute("user");
        List<userUser> userUserList = userSV.selectFriend(user.getUsername());
        model.addAttribute("friends",userUserList);
        return "/websocket";
    }
    @RequestMapping("/openWebSocket2")
    public String openWebSocket2(){
        return "/websocket_2";
    }

    @RequestMapping("/openUserInfo")
    public String openUserInfo(String userPetname,Model model,HttpSession session){
        User userAllInfoByPetName = userSV.findUserAllInfoByPetName(userPetname);
        model.addAttribute("oneUserInfo",userAllInfoByPetName);
        User user = (User)session.getAttribute("user");
        if(user !=null&&user.getUsername()!=userPetname) {
            model.addAttribute("guanzhu", userSV.selectguanzhu( user.getUsername(), userPetname));
        }
        model.addAttribute("tiezis",tieBaSV.selectTieziByPetName(userPetname));
        return "userInfo";
    }
    @RequestMapping("/UserUserGuanZhu")
    public String UserUserGuanZhu(String userName1,String userName2,int guanzhu,Model model){
        userSV.guanzhuUser(userName1, userName2, guanzhu);
        model.addAttribute("userPetname",userName2);
        return "forward:openUserInfo?userPetname="+userName2;
    }
}
