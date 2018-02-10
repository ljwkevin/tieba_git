package com.henu.util;

import com.henu.bean.UserTieba;

/**
 * Created by Administrator on 2017/12/22.
 */
public class TieBaUtil {
    /**
     * @param userTieba
     * @param type      1签到  2回复  3评论
     * @return
     */
    public static UserTieba levelAndJingYan(UserTieba userTieba, int type) {
        int level = userTieba.getLevel();
        int jingyan = 0;
        if(userTieba.getJingyan()!=null){
            jingyan = userTieba.getJingyan();
        }
        if (type == 1) {
            jingyan += level * 10;
        } else if (type == 2) {
            jingyan += level * 5;
        } else {
            jingyan += level * 3;
        }
        if (jingyan >= level * level * 100) {
            userTieba.setLevel(+level);
            jingyan -= level * level * 100;
            userTieba.setJingyan(jingyan);
        } else {
            userTieba.setJingyan(jingyan);
        }
        return userTieba;
    }
}
