package com.lj.controller.fengge;

import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.lj.common.base.BaseController;
import com.lj.common.interceptor.OverallInterceptor;
import com.lj.common.model.Fengge;

@Clear(OverallInterceptor.class)
public class FenggeController extends BaseController {

	@Inject
	FenggeService service;

	public void index(Integer pages) {

		setTitle("风格");

		pages = getDefaultPages(pages);

		setCommPlistByPagesWithAction(service.getListFengge(pages, 9));

		render("index.html");

	}

	public void detail(Integer id) {

		vailPara(id);

		Fengge sFengge = service.findById(Fengge.dao, id);

		setTitle("风格详细 ");

		setAttr("fengge", sFengge);

		render("detail.html");

	}

}
