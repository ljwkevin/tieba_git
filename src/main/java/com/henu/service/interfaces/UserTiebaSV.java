package com.henu.service.interfaces;

import com.henu.bean.UserTieba;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/10/18.
 */
@Service
public interface UserTiebaSV {
    public int insertUserTieba(UserTieba userTieba);

    public int selectCountByTieBaId(Integer tiebaId);

    public UserTieba selectOneByTibeAndUser(String tiebaId,String username);

    public int updateUserTieba(UserTieba userTieba);
}
