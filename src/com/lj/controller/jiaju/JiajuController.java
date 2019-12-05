package com.lj.controller.jiaju;

import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.lj.common.base.BaseController;
import com.lj.common.interceptor.OverallInterceptor;
import com.lj.common.model.Jiaju;

@Clear(OverallInterceptor.class)
public class JiajuController extends BaseController {

    @Inject
    JiajuService service;

    public void index(Integer pages) {

        pages = getDefaultPages(pages);

        setTitle("家居");

        setCommPlistByPagesWithAction(service.getListJiaju(pages, 9));

        render("index.html");

    }


    public void detail(Integer id) {

        vailPara(id);

        setTitle("家居详细");

        Jiaju jiaju = service.findById(Jiaju.dao, id);

        vailPara(jiaju);

        setAttr("jiaju", jiaju);

        render("detail.html");

    }


}
