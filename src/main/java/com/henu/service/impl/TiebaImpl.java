package com.henu.service.impl;

import com.henu.bean.Category;
import com.henu.bean.HuiFu;
import com.henu.bean.TieBa;
import com.henu.bean.TieZi;
import com.henu.dao.CategoryMapper;
import com.henu.dao.HuiFuMapper;
import com.henu.dao.TieBaMapper;
import com.henu.dao.TieZiMapper;
import com.henu.service.interfaces.TieBaSV;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/10/13.
 */
@Service
public class TiebaImpl implements TieBaSV {
    @Resource
    TieBaMapper tieBaDao;
    @Resource
    CategoryMapper categoryDao;
    @Resource
    HuiFuMapper huiFuDao;
    @Resource
    TieZiMapper tieziDao;

    @Override
    public int CteateTieBa(TieBa tieba) {
        return tieBaDao.insertSelective(tieba);
    }

    @Override
    public List selectAllTieBa() {
        List<TieBa> tieBas = tieBaDao.selectAllTieBa();
        return tieBas;
    }

    @Override
    public List selectTieBaPage(int page) {
        return tieBaDao.selectTieBaPage(page*20);
    }

    public TieBa selectBykey(String Key){
        return tieBaDao.selectByKey(Key);
    }

    @Override
    public TieBa selectById(int id) {
        return tieBaDao.selectByPrimaryKey(id);
    }

    @Override
    public List selectNoCrawlerTieBa() {
        List<TieBa> tieBas = tieBaDao.selectNoCrawlerTieBa();
        return tieBas;
    }

    @Override
//    @Cacheable(value = "Tieba",key = "#category.categoryid+'tieba'")
    public List<TieBa> selectTiebaByCagegory(Category category, int page, int OnePageCount) {
        List<TieBa> CategoryTiebas = new ArrayList<TieBa>();
        List<Category> categories = categoryDao.selectByPid(category.getCategoryid());
        if (categories == null || categories.size() == 0) {
            return CategoryTiebas;
        }
        if (categories.get(0).getLevel() == 2) {
            for (Category categoryLevel2 : categories) {
                List<Category> categoriesLevel3 = categoryDao.selectByPid(categoryLevel2.getCategoryid());
                for (Category categoryLevel3 : categoriesLevel3) {
                    TieBa tieBa = tieBaDao.selectTieBaByCagegoryId(categoryLevel3.getCategoryid());
                    if (tieBa == null) {
                        continue;
                    }
                    CategoryTiebas.add(tieBa);
                }
            }
        } else {
            for (Category categoryLevel3 : categories) {
                TieBa tieBa = tieBaDao.selectTieBaByCagegoryId(categoryLevel3.getCategoryid());
                if (tieBa == null) {
                    continue;
                }
                CategoryTiebas.add(tieBa);
            }
        }
        List returnList = new ArrayList();
        int count = (page - 1) * OnePageCount;
        for (int index = count; index < CategoryTiebas.size() && index < (OnePageCount + count); index++) {
            returnList.add(CategoryTiebas.get(index));
        }
        return returnList;
    }

    @Override
    public int selectTiebaCountByCagegory(Category category) {
        int count = 0;
        List<Category> categories = categoryDao.selectByPid(category.getCategoryid());
        if (categories == null || categories.size() == 0) {
            return 0;
        }
        if (categories.get(0).getLevel() == 2) {
            for (Category categoryLevel2 : categories) {
                List<Category> categoriesLevel3 = categoryDao.selectByPid(categoryLevel2.getCategoryid());
                for (Category categoryLevel3 : categoriesLevel3) {
                    TieBa tieBa = tieBaDao.selectTieBaByCagegoryId(categoryLevel3.getCategoryid());
                    if (tieBa == null) {
                        continue;
                    }
                    count += 1;
                }
            }
        } else {
            for (Category categoryLevel3 : categories) {
                TieBa tieBa = tieBaDao.selectTieBaByCagegoryId(categoryLevel3.getCategoryid());
                if (tieBa == null) {
                    continue;
                }
                count += 1;
            }
        }
        return count;
    }

    @Override
    public List<TieZi> selectTieziByPetName(String userPetName) {
        List<HuiFu> huiFus = huiFuDao.selectHuifuByPetName(userPetName);
        List<TieZi> tieZis = new ArrayList<>();
        Map<String,TieZi> tieziMap = new HashMap();
        for (HuiFu huiFu:huiFus) {
            TieZi tieZi = tieziDao.selectByPrimaryKey(huiFu.getTieziid());
            tieZi.setTiebaName(tieBaDao.selectByPrimaryKey(tieZi.getTiebaid()).getName());
            tieziMap.put(tieZi.getTiebaName(),tieZi);
        }
        Set<String> set = tieziMap.keySet();
        for (String name:set) {
            tieZis.add(tieziMap.get(name));
        }
        return tieZis;
    }

}
