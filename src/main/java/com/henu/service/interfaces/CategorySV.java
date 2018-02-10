package com.henu.service.interfaces;

import com.henu.bean.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public interface CategorySV {
    public int AddCategory(Category category);

    public int selectByName(String name);

    public List<Category> selectByLevel(int level);

    public int addAnoPid(int Pid,String name);

    public Category selectOneAndChilds(int id);


    public List<Category> selectByPid(Category category);

    /**
     * //��ѯ��û����ȡ���ɵ�3�����
     * @return
     */
    public List<Category> selectCategoryNoTieBa();

    public Category selectByCategoryId(Integer id);

    public Category selectPCategoryByCategoryId(Integer id);

}
