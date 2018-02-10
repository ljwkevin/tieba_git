package com.henu.controller;

import com.henu.bean.*;
import com.henu.service.interfaces.*;
import com.henu.util.PageUtil;
import com.henu.util.RedisUtil;
import com.henu.util.TieBaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */
@Controller
@RequestMapping("/tieba")
public class TieBaController {
    @Resource
    UserSV userSV;
    @Autowired
    RedisUtil redisUtil;
    @Resource
    TieBaSV tieBaSV;
    @Resource
    TieZiSV tieZiSV;
    @Resource
    HuiFuSV huiFuSV;
    @Resource
    PingLunSV pingLunSV;
    @Resource
    UserTiebaSV userTiebaSV;
    @Resource
    CategorySV categorySV;
    @RequestMapping("search")
    public String Search(String key,Model model,HttpSession session){
        int page = 1;
        if(null==key||key.equals("")){
            TieBa tieBa = new TieBa();
            tieBa.setName("没有这个贴吧");
            model.addAttribute("tieBa",tieBa);
            return "/tieBaPage";
        }else {
            TieBa tieBa = tieBaSV.selectBykey(key);
            if(tieBa==null){
                tieBa = new TieBa();
                tieBa.setName("没有这个贴吧");
                model.addAttribute("tieBa",tieBa);
                return "/tieBaPage";
            }
            User user = (User)session.getAttribute("user");
            if(user!=null) {
                UserTieba userTieba = userTiebaSV.selectOneByTibeAndUser(tieBa.getId()+"", user.getUsername());
                model.addAttribute("userTieba", userTieba);
            }
            List<TieZi> tieZi = tieZiSV.selectTieZiByTieBaId(tieBa.getId(),0,(page-1)*10,10);
            model.addAttribute("tieBa", tieBa);
            model.addAttribute("guanzhuNum",userTiebaSV.selectCountByTieBaId(tieBa.getId()));
            model.addAttribute("fromCategory",categorySV.selectPCategoryByCategoryId(tieBa.getCategoryid()));
            model.addAttribute("tieZiList", tieZi);
            int tieziNum = tieZiSV.selectCountById(tieBa.getId());
            model.addAttribute("tieziNum",tieziNum);
            model.addAttribute("pageMax",(tieziNum/11)+1);
            model.addAttribute("page",page);
        }
        return "/tieBaPage";
    }
    @RequestMapping("/openTiebaById")
    public String openTieBa(String tiebaId,int page,int type, HttpSession session, Model model) throws Exception {
        //type 0为普通 1为精品
        if(null==tiebaId||tiebaId.equals("")){
            TieBa tieBa = new TieBa();
            tieBa.setName("没有这个贴吧");
            model.addAttribute("tieBa",tieBa);
            return "/tieBaPage";
        }else {
            User user = (User)session.getAttribute("user");
            if(user!=null) {
                UserTieba userTieba = userTiebaSV.selectOneByTibeAndUser(tiebaId, user.getUsername());
                model.addAttribute("userTieba", userTieba);
            }
            int tId = Integer.parseInt(tiebaId);
            TieBa tieBa = tieBaSV.selectById(tId);
            List<TieZi> tieZi = tieZiSV.selectTieZiByTieBaId(tId,type,(page-1)*10,10);
            model.addAttribute("tieBa", tieBa);
            model.addAttribute("guanzhuNum",userTiebaSV.selectCountByTieBaId(tieBa.getId()));
            model.addAttribute("fromCategory",categorySV.selectPCategoryByCategoryId(tieBa.getCategoryid()));
            model.addAttribute("tieZiList", tieZi);
            int tieziNum = tieZiSV.selectCountById(tId);
            model.addAttribute("tieziNum",tieziNum);
            model.addAttribute("pageMax",(tieziNum/11)+1);
            model.addAttribute("page",page);
        }
        return "/tieBaPage";
    }
    @RequestMapping("selectBySolrTitleIndex")
    public String  selectTieZiByTitleIndex(String key,int page,Model model){
        HighlightPage<TieZi>  tieZis = tieZiSV.selectTieZiByTitleIndex(key, (page - 1) * 20, 20);
        model.addAttribute("tieZiList", tieZis.getContent());
        model.addAttribute("page",page);
        model.addAttribute("key",key);
        model.addAttribute("pageMax",tieZis.getTotalPages());
        return "/searchTieziPage";
    }
    @RequestMapping("/guanzhu")
    public String guanzhu(String tiebaId,HttpSession session,Model model){
        UserTieba userTieba = this.selectUserTieba(tiebaId, session);
        if(userTieba.getIsfollow()==1){
            userTieba.setIsfollow(0);
        }else{
            userTieba.setIsfollow(1);
        }
        if(userTieba.getFollowtime()==null){
            userTieba.setFollowtime(new Date());
            userTiebaSV.insertUserTieba(userTieba);
        }else {
            userTiebaSV.updateUserTieba(userTieba);
        }
        return "redirect:/tieba/openTiebaById?tiebaId="+tiebaId+"&&page=1&&type=-1";
    }
    private UserTieba selectUserTieba(String tiebaId,HttpSession session){
        User user = (User)session.getAttribute("user");
        UserTieba userTieba = userTiebaSV.selectOneByTibeAndUser(tiebaId,user.getUsername());
        if(userTieba==null){
            userTieba = new UserTieba();
            userTieba.setJingyan(0);
            userTieba.setLevel(1);
            userTieba.setTiebaid(Integer.parseInt(tiebaId));
            userTieba.setUsername(user.getUsername());
            userTieba.setAdminlevel(0);
            userTieba.setIsadmin(0);
            userTieba.setIsqiandao(0);
            userTieba.setIsfollow(0);
            userTieba.setLouqianstrings("");
        }
        return userTieba;
    }

    @RequestMapping("/qiandao")
    public String qiandao(String tiebaId,HttpSession session,Model model){
        User user = (User)session.getAttribute("user");
        UserTieba userTieba = userTiebaSV.selectOneByTibeAndUser(tiebaId,user.getUsername());
        if(userTieba==null){
            userTieba = this.selectUserTieba(tiebaId, session);
        }
        if(userTieba.getIsqiandao()==0){
            userTieba.setIsqiandao(1);
            userTieba.setIsfollow(1);
        }
        TieBaUtil.levelAndJingYan(userTieba,1);
        if(userTieba.getFollowtime()==null){
            userTieba.setFollowtime(new Date());
            userTiebaSV.insertUserTieba(userTieba);
        }else {
            userTiebaSV.updateUserTieba(userTieba);
        }
        return "redirect:/tieba/openTiebaById?tiebaId="+tiebaId+"&&page=1&&type=-1";
    }

    @RequestMapping("/openTieZi")
    public String openTieZi(String TieZiId,int page,  Model model) throws Exception {
        if(null==TieZiId||TieZiId.equals("")){
            return "/tieBaPage";
        }else {
            int tId = Integer.parseInt(TieZiId);
            TieZi tieZi = tieZiSV.selectTieZiById(tId);
            Integer looknum = tieZi.getLooknum();
            tieZi.setLooknum(++looknum);
            tieZiSV.updateTieZi(tieZi);
            List<HuiFu> huiFu = huiFuSV.selectByTieZiId(tieZi,page);
            model.addAttribute("tieZi", tieZi);
            model.addAttribute("huiFus", huiFu);
            model.addAttribute("pageMax", PageUtil.getPageMax(huiFuSV.selectCountByTieZiId(TieZiId),10));
            model.addAttribute("page",page);
        }
        return "/tieZiPage";
    }

    @RequestMapping("/writeTieZi")
    @Transactional
    public String wtireTieZi(int tiebaid,String content,String title,String userpetname, HttpSession session, Model model) throws Exception {
        TieZi tieZi = new TieZi();
        tieZi.setTiebaid(tiebaid);
        tieZi.setTitle(title);
        tieZi.setUserpetname(userpetname);
        tieZi.setIsjingpin(0);
        tieZi.setLooknum(0);
        tieZi.setShoucangnum(0);
        tieZi.setSort(0);
        tieZiSV.insertOneTieZi(tieZi);
        tieZi = tieZiSV.selectTieZiByTitleAndUserPetName(tieZi);
        int tieZiId = tieZi.getId();
        HuiFu huifu = new HuiFu();
        huifu.setUserpetname(userpetname);
        huifu.setLouceng(1+"");
        huifu.setTieziid(tieZiId);
        huifu.setContent(content);
        huifu.setTime(new Date());
        huiFuSV.insertHuiFu(huifu);
        if(tieZiId>0){
            User user = (User)session.getAttribute("user");
            UserTieba userTieba = userTiebaSV.selectOneByTibeAndUser(tiebaid+"",user.getUsername());
            if(userTieba==null){
                userTieba = this.selectUserTieba(tiebaid+"", session);
                userTieba.setIsfollow(1);
                userTieba.setFollowtime(new Date());
                TieBaUtil.levelAndJingYan(userTieba,2);
                userTiebaSV.insertUserTieba(userTieba);
            }else {
                if (userTieba.getIsfollow() == 0) {
                    userTieba.setIsfollow(1);
                }
                TieBaUtil.levelAndJingYan(userTieba, 2);
                userTiebaSV.updateUserTieba(userTieba);
            }
            return "redirect:/tieba/openTieZi?page=1&TieZiId="+tieZiId;
        }
        return "/tieZiPage";
    }

    @RequestMapping("/wtireHuiFu")
    public String wtireHuiFu(int tieziid,int tiebaId,String content,String userpetname,HttpSession session,  Model model) throws Exception {
        HuiFu huifu = new HuiFu();
        huifu.setUserpetname(userpetname);
        int louceng = huiFuSV.selectLouCengTieZiId(tieziid + "");
        huifu.setLouceng(louceng+1+"");
        huifu.setTieziid(tieziid);
        huifu.setContent(content);
        huifu.setTime(new Date());
        huiFuSV.insertHuiFu(huifu);
        User user = (User)session.getAttribute("user");
        UserTieba userTieba = userTiebaSV.selectOneByTibeAndUser(tiebaId+"",user.getUsername());
        if(userTieba!=null) {
            TieBaUtil.levelAndJingYan(userTieba, 3);
            userTiebaSV.updateUserTieba(userTieba);
        }
        return "redirect:/tieba/openTieZi?page=1&TieZiId="+tieziid;
    }

    @RequestMapping("/wtirePingLun")
    public String wtirePingLun(int tieziid,int tiebaId,int huifuid,String context,String userpetname, HttpSession session, Model model) throws Exception {
        PingLun pingLun = new PingLun();
        pingLun.setUserpetname(userpetname);
        pingLun.setHuifuid(huifuid);
        pingLun.setContext(context);
        pingLun.setTime(new Date());
        pingLunSV.insertPingLun(pingLun);
        User user = (User)session.getAttribute("user");
        UserTieba userTieba = userTiebaSV.selectOneByTibeAndUser(tiebaId+"",user.getUsername());
        if(userTieba!=null) {
            TieBaUtil.levelAndJingYan(userTieba, 3);
            userTiebaSV.updateUserTieba(userTieba);
        }
        return "redirect:/tieba/openTieZi?page=1&TieZiId="+tieziid;
    }
}
