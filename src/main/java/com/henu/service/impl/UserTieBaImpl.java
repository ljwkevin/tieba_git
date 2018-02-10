package com.henu.service.impl;

import com.henu.bean.UserTieba;
import com.henu.dao.UserTiebaMapper;
import com.henu.service.interfaces.UserTiebaSV;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/10/18.
 */
@Service
public class UserTieBaImpl implements UserTiebaSV {
    @Resource
    UserTiebaMapper userTiebaMapper;

    @Override
    public int insertUserTieba(UserTieba userTieba) {
        if (userTiebaMapper.selectByPrimaryKey(userTieba) != null) {
            return userTiebaMapper.updateByPrimaryKeySelective(userTieba);
        }
        return userTiebaMapper.insertSelective(userTieba);
    }

    @Override
    public int selectCountByTieBaId(Integer tiebaId) {
        return userTiebaMapper.selectCountByTieBaId(tiebaId);
    }

    @Override
    public UserTieba selectOneByTibeAndUser(String tiebaId, String username) {
        return userTiebaMapper.selectOneByTibeAndUser(tiebaId,username);
    }

    @Override
    public int updateUserTieba(UserTieba userTieba) {
        return userTiebaMapper.updateByPrimaryKey(userTieba);
    }
}
