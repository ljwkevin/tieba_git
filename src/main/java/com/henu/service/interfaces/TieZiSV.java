package com.henu.service.interfaces;

import com.henu.bean.TieZi;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */
public interface TieZiSV {
    public int insertTieZi(TieZi tiezi);
    public int insertOneTieZi(TieZi tiezi);

    public List<TieZi> selectTieZiByTieBaId(int TieBaId,int type,int startIndex,int endIndex);

    public HighlightPage<TieZi> selectTieZiByTitleIndex(String title, int startIndex, int pagesum);

    public TieZi selectTieZiById(int TieZiId);

    public List<TieZi> selectNoCrawlerTieZi();

    public TieZi selectTieZiByTitleAndUserPetName(TieZi tieZi);

    public int updateTieZi(TieZi tieZi);

    public int selectCountById(int id);
}
