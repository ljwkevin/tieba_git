package com.henu;

import com.henu.controller.DoCrawling;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TiebaApplicationTests {

    @Resource
    DoCrawling DoCrawling;
    @Test
    public void contextLoads3() {
        DoCrawling.DoCrawlingCategoryLevel3();
    }
    @Test
    public void contextLoads2() {
        DoCrawling.DoCrawlingCategoryLevel2();
    }

    @Test
    public void contextLoads() {
        DoCrawling.DoCrawlingCategoryLevel1();
    }

    @Test
    public void TieBa() {
        DoCrawling.DoCrawlingTieba();
    }

    @Test
    public void TieZi() {
        DoCrawling.DoCrawlingTiezi();
    }

    @Test
    public void HuiFu() {
        DoCrawling.DoCrawlingHuiFu();
    }
}
