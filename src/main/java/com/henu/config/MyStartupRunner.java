package com.henu.config;

import com.henu.bean.Category;
import com.henu.service.interfaces.CategorySV;
import com.henu.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */
@Component
public class MyStartupRunner implements CommandLineRunner {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    CategorySV categorySV;
    @Resource
    RedisUtil redisUtil;
    @Override
    public void run(String... strings) throws Exception {
        logger.info(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
        List<Category> categorieOne = categorySV.selectByLevel(1);
        for (Category categoryOne:categorieOne) {
            List<Category> categorieTwo = categorySV.selectByPid(categoryOne);
            for (Category categoryTwo:categorieTwo) {
                categorySV.selectByPid(categoryTwo);
            }
        }
    }
}
