package com.lj.controller._admin.fengge;

import java.util.Date;

import com.jfinal.aop.Inject;
import com.jfinal.upload.UploadFile;
import com.lj.common.base.BaseController;
import com.lj.common.model.Fengge;
import com.lj.controller.fengge.FenggeService;

public class FenggeController extends BaseController {

	@Inject
	FenggeService service;

	public void index(Integer pages) {

		pages = getDefaultPages(pages);

		setTitle("风格管理");

		setCommPlistByPagesWithAction(service.getListFengge(pages, 10));

		render("index.html");

	}

	public void edit(Integer id) {

		Fengge fengge = service.findById(Fengge.dao, id);

		setAttr("fengge", fengge);
		
		setTitle("风格详情");

		render("edit.html");

	}

	public void update() {

		Fengge fengge = getModel(Fengge.class, "fengge");

		if (fengge.getFenggeId() == null) {

			fengge.setFenggeDate(new Date());
		}

		boolean flag = setDefaultSaveOrUpdate(fengge, fengge.getFenggeId());

		setDefaultMsgTip(flag);

		forwardAction("/a/fengge");

	}

	public void delete(Integer id) {

		vailPara(id);

		setDefaultMsgTip(service.delete(new Fengge().setFenggeId(id)));

		forwardAction("/a/fengge");

	}

}
