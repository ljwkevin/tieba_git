package com.henu.util;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.google.gson.Gson;
import com.henu.bean.*;
import com.henu.controller.DoCrawling;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/14.
 */
public class PicCrawler extends BreadthCrawler {

    private static final Logger LOG = LoggerFactory.getLogger(DoCrawling.class);
    private static ApplicationContext applicationContext;
    public static void setApplicationContext(ApplicationContext applicationContext) {
        PicCrawler.applicationContext = applicationContext;
    }
    private KafkaSender sender = (KafkaSender)applicationContext.getBean(KafkaSender.class);
    private Gson gson = new Gson();
    private RedisUtil redisUtil=null;
    private String getBusiUrl(String url) {
        return url.substring(0, url.indexOf("com") + 2);
    }


    public PicCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }


    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        if(this.redisUtil==null){
            this.redisUtil = (RedisUtil)applicationContext.getBean(RedisUtil.class);
        }
        if(redisUtil.getValue("niuUrlFilter",page.url())){
            return;
        }
        redisUtil.addValue("niuUrlFilter",page.url());
        if (page.meta("level") != null && page.meta("level").equals("1")) {
            CategoryLevel1(page, crawlDatums);
        } else if (page.meta("level") != null && page.meta("level").equals("3")) {
            CategoryLevel3(page, crawlDatums);
        } else if (page.meta("level") != null && page.meta("level").equals("2")) {
            CategoryLevel2(page, crawlDatums);
        } else if (page.meta("tieba") != null && page.meta("tieba").equals("1")) {
            CrawlerTieba(page);
        } else if (page.meta("tiebaPage") != null) {
            CrawlerTieBaPages(page, crawlDatums);
        } else if (page.meta("HuiFuPage")!=null) {
            CrawlerHuiFu(page, crawlDatums);
        }

    }

    public String getUrl(String url) {
        if (!url.contains("tieba.baidu.com")) {
            return "http://tieba.baidu.com" + url;
        }
        return url;
    }

    public synchronized void CrawlerTieba(Page page) {
        int Cid = Integer.parseInt(page.meta("Cid"));
        String html = page.html();
        html = html.replace("<!--","");
        html = html.replace("-->","");
        Document parse = Jsoup.parse(html);
        String TieBaName = parse.select(".card_title_fname").get(0).text();
        String QianMing = parse.select(".card_slogan").get(0).text();
        String HeadImgUrl = parse.select("#forum-card-head").get(0).attr("src");
        HeadImgUrl = DoCrawling.downloadImg(HeadImgUrl, TieBaName);
        if (HeadImgUrl == null) {
            System.out.println("没有头像");
            return;
        }
        TieBa tieba = new TieBa();
        tieba.setCategoryid(Cid);
        tieba.setHeadimgurl(HeadImgUrl);
        tieba.setName(TieBaName);
        tieba.setSignature(QianMing);
        tieba.setHref(getUrl(page.url()));
        String message = gson.toJson(tieba);
        sender.send("tieBa",message);
    }

    public synchronized void  CrawlerTieBaPages(Page page1, CrawlDatums crawlDatums) {
        int pageNum = Integer.parseInt(page1.meta("tiebaPage"));
        if(pageNum==0){pageNum=1;}
        int TbId = Integer.parseInt(page1.meta("TbId"));
        String html = page1.html();
        html = html.replace("<!--","");
        html = html.replace("-->","");
        Document page = Jsoup.parse(html);
        Elements select = page.select(".j_thread_list.clearfix");
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format3 = new SimpleDateFormat("MM-dd");
        for (Element zhuti : select) {
            try {
                TieZi tieZi = new TieZi();
                String CreateTime = zhuti.select(".is_show_create_time").get(0).text();
                String title = zhuti.select(".j_th_tit .j_th_tit").get(0).text();
                System.out.println("题目是：" + title);
                String tieZiHref = zhuti.select(".j_th_tit .j_th_tit").get(0).attr("href");
                String userPetName = zhuti.select(".frs-author-name.j_user_card").get(0).text();
                Date date = null;
                try {
                    if (CreateTime.contains(":")) {
                        date = format1.parse(CreateTime);
                    } else if (CreateTime.length() == 5) {
                        date = format3.parse(CreateTime);
                    } else if (CreateTime.length() == 7) {
                        date = format2.parse(CreateTime);
                    } else {
                        date = new Date();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tieZi.setCteatetime(date);
                tieZi.setHref(this.getUrl(tieZiHref));
                tieZi.setTitle(title);
                tieZi.setUserpetname(userPetName);
                tieZi.setTiebaid(TbId);
                String message = gson.toJson(tieZi);
                sender.send("tieZi",message);
            }
            catch (Exception e){
                LOG.info(e.toString());
            }
        }

        if (pageNum==1) {
            String lastHref  =  page.select(".last.pagination-item").get(0).attr("href");
            int lastindex = Integer.parseInt(lastHref.substring(lastHref.lastIndexOf("=") + 1)) / 50 + 1;
            int index = lastHref.lastIndexOf("=");
            if(lastindex>=20){lastindex = 20;}
            for(pageNum=1;pageNum<=lastindex;++pageNum){
                String addHref = lastHref.substring(0, index + 1) + (pageNum) * 50;
                addHref = getUrl(addHref);
                if(!addHref.contains("http")){
                    addHref = "http:"+addHref;
                }
                crawlDatums.add(new CrawlDatum(addHref).meta("tiebaPage", pageNum).
                        meta("TbId", TbId));
            }
        }
    }


    public synchronized void CrawlerHuiFu(Page page, CrawlDatums crawlDatums) {
        int TieZiId = Integer.parseInt(page.meta("TieZiId"));
        Elements select = page.select(".l_post.j_l_post.l_post_bright");
        for (int i=0;i<select.size();i++) {
            Element element = select.get(i);
            String UserPetName = element.select(".p_author_name").text();
            if(UserPetName.equals("")){
                UserPetName="admin";
            }
            if(UserPetName.contains("�Ƽ�")){
                continue;
            }
            String louceng = (i+1)+"";
            Date time = new Date();
            HuiFu huiFu = new HuiFu();
            String content =  element.select(".d_post_content").text();
            try {
                if ( element.select(".d_post_content img") != null&&!element.select(".d_post_content img").attr("src").equals("")) {
                    DoCrawling.downloadImg("D:\\test2\\tieba\\src\\main\\resources\\huiFuImg\\",
                            element.select(".d_post_content img").attr("src"),
                            TieZiId+"_"+louceng );
                    content +="img";
                }
            }
            catch (Exception e){
                LOG.info(e.toString());
            }
            huiFu.setTime(time);
            huiFu.setContent(content);
            huiFu.setLouceng(louceng);
            huiFu.setUserpetname(UserPetName);
            huiFu.setTieziid(TieZiId);
            Map map = new HashMap();
            Integer tiebaId = Integer.parseInt(page.meta("tiebaId"));
            map.put("tiebaId",tiebaId);
            map.put("huiFu",gson.toJson(huiFu));
            String message = gson.toJson(map);
            sender.send("huiFu",message);
            CrawlerUserinfos(element, tiebaId);
        }
        try {
            Elements pageelements = page.select(".l_pager.pager_theme_5.pb_list_pager>a");
            String pageMaxHref = pageelements.get(pageelements.size()-1).attr("href");
            int pageMaxNum = Integer.parseInt(pageMaxHref.substring(pageMaxHref.lastIndexOf("=") + 1));
            int pageNow = Integer.parseInt(page.meta("HuiFuPage"));
            if(/*pageNow<2&&*/pageNow<pageMaxNum){
                ++pageNow;
                pageMaxHref = pageMaxHref.substring(0,pageMaxHref.lastIndexOf("=")+1)+pageNow;
                LOG.info("页数为："+pageNow);
                crawlDatums.add(new CrawlDatum(getUrl(pageMaxHref)).meta("HuiFuPage",pageNow).meta("TieZiId",TieZiId));
            }
        }
        catch (Exception e){
            LOG.info(e.toString());
        }
    }



    public void CrawlerUserinfos(Element element,int tiebaId){
        Element UserElement = element.select(".p_author").get(0);
        String UserName = UserElement.select(".d_name").text();
        if(UserName.equals("")){
            UserName = "admin";
        }
        String src = UserElement.select(".p_author_face>img").attr("src");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserpetname(UserName);
        userInfo.setUsername(UserName);
        String text = UserElement.select(".d_badge_lv").text();
        int level = Integer.parseInt(text.equals("")?"0":text);
        String levelName = UserElement.select(".d_badge_title").text();
        int isAdmin = 0;
        int AdmimLevel = 0;
        if(levelName.equals("大吧主")){
            isAdmin=1;
            AdmimLevel = 2;
        }else if(levelName.equals("小吧主")){
            isAdmin=1;
            AdmimLevel = 1;
        }
        UserTieba userTieba = new UserTieba();
        userTieba.setLevel(level);
        userTieba.setIsadmin(isAdmin);
        userTieba.setUsername(UserName);
        userTieba.setIsfollow(1);
        userTieba.setFollowtime(new Date());
        userTieba.setAdminlevel(AdmimLevel);
        try {
            if(!src.equals("http://tb2.bdstatic.com/tb/static-pb/img/head_80.jpg")){
                userInfo.setHeadimgurl("D:\\test2\\tieba\\src\\main\\resources\\userHeadImg\\"+UserName);
                DoCrawling.downloadImg("D:\\test2\\tieba\\src\\main\\resources\\userHeadImg\\", src, UserName);
            }else{
                userInfo.setHeadimgurl("D:\\test2\\tieba\\src\\main\\resources\\userHeadImg\\"+"default");
                DoCrawling.downloadImg("D:\\test2\\tieba\\src\\main\\resources\\userHeadImg\\", src, "default");
            }
        }
        catch (Exception e){
            userInfo.setHeadimgurl("D:\\test2\\tieba\\src\\main\\resources\\userHeadImg\\"+"default");
            LOG.info(e.toString());
            LOG.info("图片出错啦");
        }
        Map userMap = new HashMap();
        userMap.put("userName",UserName);
        userMap.put("userinfo",gson.toJson(userInfo));
        userMap.put("usertieba",gson.toJson(userTieba));
        userMap.put("tiebaId",tiebaId);
        String message = gson.toJson(userMap);
        sender.send("UserInfo",message);
    }

    public void CategoryLevel3(Page page, CrawlDatums crawlDatums) {
        int pid = Integer.parseInt(page.meta("Pid"));
        Elements selects = page.select(".pagination .last");
        if (page.meta("page").equals("1") && selects.size() != 0) {
            String href = selects.attr("href");
            int lastindex = selects.attr("href").lastIndexOf("=");
            int maxPage = Integer.parseInt(href.substring(lastindex + 1));
            for (int i = 2; i <= maxPage; i++) {
                href = href.substring(0, lastindex + 1) + i;
                href = getUrl(href);
                crawlDatums.add(new CrawlDatum(href).meta("level", "3").meta("page", i).meta("Pid", pid));
            }
        }
        Elements selectc = page.select(".ba_href.clearfix");
        for (int i = 0; i < selectc.size(); i++) {
            Element element = selectc.get(i);
            String level3Name = element.select(".ba_name").text();
            Category childcate = new Category();
            childcate.setParentid(pid);
            childcate.setLevel(3);
            childcate.setCategoryname(level3Name);
            childcate.setUrl(getUrl(element.attr("href")));
            sender.send("category",gson.toJson(childcate));
        }
    }

    public void CategoryLevel2(Page page, CrawlDatums crawlDatums) {
        Elements selectc = page.select(".class-item");
        for (int i = 0; i < selectc.size(); i++) {
            Element element = selectc.get(i);
            Elements parent = element.select(".class-item-title");
            String Pname = parent.get(0).text();
            String name = page.meta("name");
            if (name.equals(Pname)) {
                Elements childCates = element.select(".item-list-ul li");
                for (Element ChildCate : childCates) {
                    String href = ChildCate.select("a[href]").attr("href");
                    href = this.getUrl(href);
                    Category childcate = new Category();
                    childcate.setParentid(Integer.parseInt(page.meta("Pid")));
                    childcate.setLevel(2);
                    childcate.setCategoryname(ChildCate.text());
                    childcate.setUrl(href);
                    sender.send("category",gson.toJson(childcate));
                }
            }

        }
    }

    public void CategoryLevel1(Page page, CrawlDatums crawlDatums) {
        Elements selectc = page.select(".class-item");
        for (int i = 0; i < selectc.size(); i++) {
            Element element = selectc.get(i);
            Elements parent = element.select(".class-item-title");
            String Pname = parent.get(0).text();
            Category cate = new Category();
            cate.setParentid(0);
            cate.setLevel(1);
            cate.setUrl(page.url());
            cate.setCategoryname(Pname);
            sender.send("category",gson.toJson(cate));

        }
    }
}
