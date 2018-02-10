package com.henu.service.interfaces;

import com.henu.bean.Category;
import com.henu.bean.HuiFu;
import com.henu.bean.TieBa;
import com.henu.bean.TieZi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/25.
 */
public interface TieBaSV {

    public int CteateTieBa(TieBa tieba);

    public List selectAllTieBa();

    public List selectTieBaPage(int page);
    public TieBa selectBykey(String Key);

    public TieBa selectById(int id);

    public List selectNoCrawlerTieBa();

    public List<TieBa> selectTiebaByCagegory(Category category,int page,int OnePageCount);

    public int selectTiebaCountByCagegory(Category category);

    public List<TieZi> selectTieziByPetName(String userPetName);

}
