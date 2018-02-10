package com.henu.controller;

import com.henu.bean.Category;
import com.henu.bean.TieBa;
import com.henu.bean.User;
import com.henu.service.interfaces.CategorySV;
import com.henu.service.interfaces.TieBaSV;
import com.henu.util.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */
@Controller
@RequestMapping("/tieba")
public class CategoryController {
    @Resource
    CategorySV categorySv;
    @Resource
    TieBaSV tieBaSv;
    @RequestMapping("/selectChildById")
    public String SelectCategoryChilds(@RequestParam("categoryId") Integer categoryId,int page,int OnePageCount, Model model) throws Exception {
        Category category = new Category();
        category.setCategoryid(categoryId);
        model.addAttribute("pCategoryId",categoryId);
        Category category1 = categorySv.selectByCategoryId(categoryId);
        if(category1.getLevel()==1){
            model.addAttribute("categoryOne",categorySv.selectOneAndChilds(categoryId));
        }else if(category1.getLevel()==2){
            model.addAttribute("categoryOne",categorySv.selectOneAndChilds(category1.getParentid()));
        }
        model.addAttribute("Tiebas",tieBaSv.selectTiebaByCagegory(category,page,OnePageCount));
        int pageMax = PageUtil.getPageMax(tieBaSv.selectTiebaCountByCagegory(category), OnePageCount);
        model.addAttribute("pageMax",pageMax);
        model.addAttribute("page",page);
        return "/categoryInfo";
    }

    @RequestMapping("/selectAllCate")
    public String selectAllCate(Model model) throws Exception {
        List<Category> categories = categorySv.selectByLevel(1);
        model.addAttribute("CategoriesOne", categories);
        return "/allCategory";
    }
}
