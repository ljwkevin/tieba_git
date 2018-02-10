package com.henu.service.impl;

import com.henu.bean.HuiFu;
import com.henu.bean.PingLun;
import com.henu.bean.TieZi;
import com.henu.bean.User;
import com.henu.dao.HuiFuMapper;
import com.henu.dao.PingLunMapper;
import com.henu.dao.UserMapper;
import com.henu.service.interfaces.HuiFuSV;
import com.henu.service.interfaces.UserSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */
@Service
public class HuiFuImpl implements HuiFuSV {
    private static final Logger LOG = LoggerFactory.getLogger(HuiFuImpl.class);
    @Resource
    HuiFuMapper huiFuDao;
    @Resource
    PingLunMapper pingLunDao;
    @Resource
    UserSV userSV;
    @Override
    public int insertHuiFu(HuiFu huifu) {
        if(selectByTieZiIdLouCeng(huifu)==null) {
            return huiFuDao.insertSelective(huifu);
        }
        else{
            LOG.info("已爬取过该贴"+huifu.getLouceng()+"楼层的回复");
            return 0;
        }
    }

    @Override
    public HuiFu selectByTieZiIdLouCeng(HuiFu huifu) {
        return huiFuDao.selectByTieZiIdLouCeng(huifu.getTieziid(),huifu.getLouceng());
    }

    @Override
    public List<HuiFu> selectByTieZiId(TieZi tieZi, int page) {
        List<HuiFu> huiFus = huiFuDao.selectByTieZiId(tieZi.getId()+"", (page - 1) * 10, 10);
        for (HuiFu huiFu:huiFus) {
            List<PingLun> pingLuns = pingLunDao.selectPingLunByHuiFuId(huiFu.getId());
            huiFu.setUser(userSV.findUserByPetName(huiFu.getUserpetname(),tieZi.getTiebaid()+""));
            huiFu.setPingLunList(pingLuns);
        }
        return huiFus;
    }

    @Override
    public int selectCountByTieZiId(String TieZiId) {
        return huiFuDao.selectCountByTieZiId(TieZiId);
    }

    @Override
    public int selectLouCengTieZiId(String TieZiId) {
        return huiFuDao.selectLoucengByTieZiId(TieZiId);
    }


}
