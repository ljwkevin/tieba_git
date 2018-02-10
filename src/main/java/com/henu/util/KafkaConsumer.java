package com.henu.util;

import com.google.gson.Gson;
import com.henu.bean.*;
import com.henu.controller.DoCrawling;
import com.henu.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/2/5.
 */
@Component
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(DoCrawling.class);
    private Gson gson = new Gson();
    @Resource
    CategorySV categorySV;
    @Resource
    TieBaSV tiebaSv;
    @Resource
    TieZiSV tieziSv;
    @Resource
    HuiFuSV huiFuSV;
    @Resource
    UserSV userSV;
    @Resource
    UserInfoSV userInfoSV;
    @Resource
    UserTiebaSV userTiebaSV;
    @Resource
    UserSettingSV userSettingSV;
    /**
     * 监听test主题,有消息就读取
     * @param message
     */
    @KafkaListener(topics = {"category"})
    public void category(String message){
        Category category = gson.fromJson(message, Category.class);
        if (categorySV.addAnoPid(category.getParentid(), category.getCategoryname()) == 1) {
            categorySV.AddCategory(category);
        }
    }
    @KafkaListener(topics = {"tieBa"})
    public void consumer(String message){
        TieBa tieBa = gson.fromJson(message, TieBa.class);
        tiebaSv.CteateTieBa(tieBa);
    }
    @KafkaListener(topics = {"tieZi"})
    public void tieZi(String message){
        TieZi tiezi = gson.fromJson(message, TieZi.class);
        tieziSv.insertTieZi(tiezi);
    }
    @KafkaListener(topics = {"huiFu"})
    public void huiFu(String message){
        Map map = gson.fromJson(message, Map.class);
        try {
            int tiebaId = (int)(double)map.get("tiebaId");
            String TiebaName = tiebaSv.selectById(tiebaId).getName();
            HuiFu huiFu = gson.fromJson(map.get("huiFu").toString(),HuiFu.class);
            if(huiFu.getUserpetname().equals("admin")){
                huiFu.setUserpetname(TiebaName+"_admin");
            }
            huiFuSV.insertHuiFu(huiFu);
        }
        catch (Exception e){
            log.debug(e.toString());
        }
    }
    @KafkaListener(topics = {"UserInfo"})
    public void UserInfo(String message){
        Map userMap = gson.fromJson(message, Map.class);
        String userName = userMap.get("userName").toString();
        int tiebaId = (int)(double)userMap.get("tiebaId");
        String TiebaName = tiebaSv.selectById(tiebaId).getName();
        UserInfo userinfo = gson.fromJson(userMap.get("userinfo").toString(),UserInfo.class);
        UserTieba usertieba = gson.fromJson(userMap.get("usertieba").toString(),UserTieba.class);
        usertieba.setTiebaid(tiebaId);
        if(userName.equals("admin")){
            userName = TiebaName+"_admin";
            userinfo.setUsername(userName.toString());
            userinfo.setUserpetname(userName.toString());
            usertieba.setUsername(userName.toString());
        }
        User user = new User();
        user.setPassword("123456");
        user.setUsername(userName.toString());
        UserSetting userSetting = new UserSetting();
        userSetting.setUsername(userName.toString());
        if(userSV.insertUser(user)==1){
            userInfoSV.insertUserInfo(userinfo);
            userTiebaSV.insertUserTieba(usertieba);
            userSettingSV.insertUserSetting(userSetting);
        }
    }
}
