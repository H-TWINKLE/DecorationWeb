package com.lj.controller.jiancai;

import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.lj.common.base.BaseController;
import com.lj.common.interceptor.OverallInterceptor;
import com.lj.common.model.Jiaju;
import com.lj.common.model.Jiancai;

@Clear(OverallInterceptor.class)
public class JiancaiController extends BaseController {

	@Inject
	JiancaiService service;

	public void index(Integer pages) {

		setTitle("建材");

		pages = getDefaultPages(pages);

		setCommPlistByPagesWithAction(service.getListJiancai(pages, 9));

		render("index.html");

	}

	public void detail(Integer id) {

		vailPara(id);

		setTitle("建材详细");

		Jiancai jiancai = service.findById(Jiancai.dao, id);

		vailPara(jiancai);

		setAttr("jiancai", jiancai);

		render("detail.html");

	}

}
