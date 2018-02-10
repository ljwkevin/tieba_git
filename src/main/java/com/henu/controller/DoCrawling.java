package com.henu.controller;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import com.henu.bean.*;
import com.henu.service.interfaces.*;
import com.henu.util.PicCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/13.
 */
@Controller
@RequestMapping("/tieba")
public class DoCrawling {
    private static final Logger LOG = LoggerFactory.getLogger(DoCrawling.class);
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
    @RequestMapping("/DoCrawlingLevel1")
    public void DoCrawlingCategoryLevel1() {
        PicCrawler pic = new PicCrawler("leibie", true);
        pic.addSeed(new CrawlDatum("http://tieba.baidu.com/f/index/forumclass").meta("level", "1"));
        pic.setThreads(20);
        try {
            pic.start(5);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @RequestMapping("/DoCrawlingLevel2")
    public void DoCrawlingCategoryLevel2() {
        List<Category> categories = categorySV.selectByLevel(1);
        for (Category cata : categories) {
            PicCrawler pic = new PicCrawler("leibie", true);
            pic.addSeed(new CrawlDatum("http://tieba.baidu.com/f/index/forumclass").meta("level", "2").
                    meta("Pid", cata.getCategoryid()).meta("name", cata.getCategoryname()));
            pic.setThreads(1);
            try {
                pic.start(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("/DoCrawlingLevel3")
    public void DoCrawlingCategoryLevel3() {
        List<Category> categories = categorySV.selectByLevel(2);
        PicCrawler pic = new PicCrawler("leibie", true);
        for (Category cata : categories) {
            pic.addSeed(new CrawlDatum(cata.getUrl()).meta("level", "3").
                    meta("Pid", cata.getCategoryid()).meta("page", "1"));
        }
        pic.setThreads(20);
        try {
            pic.start(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/DoCrawlingTieBa")
    public void DoCrawlingTieba() {
        List<Category> categories = categorySV.selectCategoryNoTieBa();
        PicCrawler pic = new PicCrawler("leibie", true);
        for (Category cata : categories) {
            pic.addSeed(new CrawlDatum(cata.getUrl()).meta("tieba", "1").
                    meta("Cid", cata.getCategoryid()));
        }
        try {
            pic.setThreads(10);
            pic.start(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*List<TieBa> tiebaLists = pic.getTiebaLists();
        for (TieBa tieba:tiebaLists) {
            tiebaSv.CteateTieBa(tieba);
        }
        pic.setTiebaLists(new ArrayList<TieBa>());*/
    }
    @RequestMapping("/DoCrawlingTiezi")
    public void DoCrawlingTiezi() {
        List<TieBa> tieBas = tiebaSv.selectNoCrawlerTieBa();
        PicCrawler pic = new PicCrawler("leibie", true);
        for (TieBa tieBa : tieBas) {
            pic.addSeed(new CrawlDatum(tieBa.getHref()).meta("tiebaPage", "1").
                    meta("TbId", tieBa.getId()));
            pic.setThreads(10);
            try {
                pic.start(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("/DoCrawlingHuiFu")
    public void DoCrawlingHuiFu() {
        List<TieZi> tieZis = tieziSv.selectNoCrawlerTieZi();
        PicCrawler pic = new PicCrawler("leibie", true);
        for (TieZi tieZi : tieZis) {
            int tiebaId = tieZi.getTiebaid();
            pic.addSeed(new CrawlDatum(tieZi.getHref()).meta("HuiFuPage", "1").
                    meta("TieZiId", tieZi.getId()).meta("tiebaId",tiebaId));
        }
        pic.setThreads(10);
        try {
            pic.start(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String downloadImg(String path,String src, String imgName) {
        try {
            if(!src.contains("http")){
                src = "http:"+src;
            }
            URL url = new URL(src);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String imageSrc = path + imgName + ".jpg";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageSrc));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            dataInputStream.close();
            fileOutputStream.close();

            return imageSrc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String downloadImg(String src, String imgName) {
        try {
            URL url = new URL(src);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String imageSrc = "D:\\test2\\tieba\\src\\main\\resources\\headImg\\" + imgName + ".jpg";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageSrc));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            dataInputStream.close();
            fileOutputStream.close();

            return imageSrc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
