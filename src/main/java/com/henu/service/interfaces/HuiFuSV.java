package com.henu.service.interfaces;

import com.henu.bean.HuiFu;
import com.henu.bean.TieZi;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */
public interface HuiFuSV {
    public int insertHuiFu(HuiFu huifu);
    public HuiFu selectByTieZiIdLouCeng(HuiFu huifu);
    public List<HuiFu> selectByTieZiId(TieZi tiezi, int page);
    public int selectCountByTieZiId(String TieZiId);
    public int selectLouCengTieZiId(String TieZiId);
}
