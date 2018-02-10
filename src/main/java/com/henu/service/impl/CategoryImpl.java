package com.henu.service.impl;

import com.henu.bean.Category;
import com.henu.dao.CategoryMapper;
import com.henu.service.interfaces.CategorySV;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
@Service
public class CategoryImpl implements CategorySV {

    @Resource
    CategoryMapper categoryDao;
    @Override
    public int AddCategory(Category category) {
        return categoryDao.insertSelective(category);
    }

    @Override
    public int selectByName(String name) {
        return categoryDao.selectByName(name);
    }

    @Override
    @Cacheable(value = "Category",key = "#level+'level'")
    public List<Category> selectByLevel(int level) {
        List<Category> categories = categoryDao.selectByLevel(level);
        for (Category category:categories) {
            category.setCategoryChilds(this.selectByPid(category));
        }
        return categories;
    }


    @Override
    public int addAnoPid(int Pid,String name){
        List<Category> categorys = categoryDao.selectCategoryByName(name);
        if(categorys==null||categorys.size()==0){
            return 1;
        }
        for (Category category:categorys) {
            if(Pid==category.getParentid()){
                return 0;
            }
        }
        return 1;
    }

    @Override
    @Cacheable(value = "Category",key = "#category.categoryid+'child'")
    public List<Category> selectByPid(Category category) {
        return categoryDao.selectByPid(category.getCategoryid());
    }

    @Override
    public List<Category> selectCategoryNoTieBa() {
        return categoryDao.selectCategoryNoTieBa();
    }

    @Override
    public Category selectByCategoryId(Integer id) {
        return categoryDao.selectByPrimaryKey(id);
    }

    @Override
    public Category selectPCategoryByCategoryId(Integer id) {
        Category category = categoryDao.selectByPrimaryKey(id);
        return categoryDao.selectByPrimaryKey(category.getParentid());
    }

    @Override
    public Category selectOneAndChilds(int id) {
        Category category = categoryDao.selectByPrimaryKey(id);
        category.setCategoryChilds(this.selectByPid(category));
        return category;
    }
}
