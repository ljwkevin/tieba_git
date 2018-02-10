package com.henu.service.impl;

import com.henu.bean.TieZi;
import com.henu.bean.User;
import com.henu.bean.UserInfo;
import com.henu.bean.UserSetting;
import com.henu.dao.*;
import com.henu.service.interfaces.TieZiSV;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */
@Service
public class TieZiImpl implements TieZiSV {
    @Resource
    TieZiMapper tieZiDao;
    @Resource
    UserMapper userDao;
    @Resource
    UserInfoMapper userInfoDao;
    @Resource
    UserSettingMapper userSettingDao;
    @Resource
    HuiFuMapper huiFuDao;
    @Resource
    SolrTemplate solrTemplate;
    @Override
    public int insertTieZi(TieZi tiezi) {

        if(tieZiDao.selectByTitleUserPetName(tiezi.getTitle(),tiezi.getUserpetname())!=null){
            return 0;
        }
        if(userDao.selectByPrimaryKey(tiezi.getUserpetname())==null){
            User user = new User();
            user.setUsername(tiezi.getUserpetname());
            user.setPassword("123456");
            if(userDao.insertSelective(user)==1) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername(tiezi.getUserpetname());
                userInfo.setUserpetname(tiezi.getUserpetname());
                userInfoDao.insertSelective(userInfo);
                UserSetting userSetting = new UserSetting();
                userSetting.setUsername(tiezi.getUserpetname());
                userSettingDao.insertSelective(userSetting);
            }
        }
        return tieZiDao.insertSelective(tiezi);
    }

    @Override
    public int insertOneTieZi(TieZi tiezi) {
        if(tiezi.getCteatetime()==null){
            tiezi.setCteatetime(new Date());
        }
        return tieZiDao.insertOneTieZi(tiezi);
    }

    public HighlightPage<TieZi> selectTieZiByTitleIndex(String title,int startIndex,int pagesum){
        Criteria criteria=new Criteria("Title").is(title);
        HighlightQuery query=new SimpleHighlightQuery(criteria);
        query.setOffset(startIndex);//开始索引（默认0）
        query.setRows(pagesum);
        HighlightPage<TieZi> tieZis = solrTemplate.queryForHighlightPage(query, TieZi.class);
        return tieZis;
    }

    @Override
    public List<TieZi> selectTieZiByTieBaId(int id,int type,int startIndex,int pagesum) {
        List<TieZi> tieZis = null;
        Query query=new SimpleQuery("*:*");
        Criteria criteria=new Criteria("TieBaId").is(id);
        query.setOffset(startIndex);//开始索引（默认0）
        query.setRows(pagesum);
        if(type==-1){
            query.addCriteria(criteria);
            ScoredPage<TieZi> ScoretieZis = solrTemplate.queryForPage(query, TieZi.class);
            tieZis = ScoretieZis.getContent();
//            tieZis = tieZiDao.selectTieZiByTieBaId(id,startIndex,pagesum);
        }else{
            criteria = criteria.and("IsJingPin").is(type);
            query.addCriteria(criteria);
            ScoredPage<TieZi> ScoretieZis = solrTemplate.queryForPage(query, TieZi.class);
            tieZis = ScoretieZis.getContent();
//            tieZis = tieZiDao.selectTypeTieZiByTieBaId(id,type,startIndex,pagesum);
        }
        for (TieZi tieZi:tieZis) {
            int count = huiFuDao.selectCountByTieZiId(tieZi.getId() + "");
            tieZi.setHuifuNum(count);
        }
        return tieZis;
    }
    @Override
    public TieZi selectTieZiById(int TieZiId) {
        return tieZiDao.selectByPrimaryKey(TieZiId);
    }

    @Override
    public List<TieZi> selectNoCrawlerTieZi() {
        return tieZiDao.selectNoCrawlerTieZi();
    }

    @Override
    public TieZi selectTieZiByTitleAndUserPetName(TieZi tieZi) {
        return tieZiDao.selectTieZiByTitleAndUserPetName(tieZi);
    }

    @Override
    public int updateTieZi(TieZi tieZi) {
        return tieZiDao.updateByPrimaryKeySelective(tieZi);
    }

    @Override
    public int selectCountById(int id) {
        return tieZiDao.selectCountById(id);
    }
}
