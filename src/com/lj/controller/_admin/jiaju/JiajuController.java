package com.lj.controller._admin.jiaju;

import java.util.Date;

import com.jfinal.aop.Inject;
import com.jfinal.upload.UploadFile;
import com.lj.common.base.BaseController;
import com.lj.common.model.Jiaju;
import com.lj.controller.jiaju.JiajuService;

public class JiajuController extends BaseController {

	@Inject
	JiajuService service;

	public void index(Integer pages) {

		pages = getDefaultPages(pages);

		setTitle("家居管理");

		setCommPlistByPagesWithAction(service.getListJiaju(pages, 10));

		render("index.html");

	}

	public void edit(Integer id) {

		Jiaju jiaju = service.findById(Jiaju.dao, id);
		
		setTitle("家居详情");

		setAttr("jiaju", jiaju);

		render("edit.html");

	}

	public void update() {

		UploadFile file = getFile("file");

		Jiaju jiaju = getModel(Jiaju.class, "jiaju");

		if (file != null) {
			jiaju.setJiajuPic("/upload/temp/" + file.getFileName());

		}

		if (jiaju.getJiajuId() == null) {
			jiaju.setJiajuDate(new Date());
		}

		boolean flag = setDefaultSaveOrUpdate(jiaju, jiaju.getJiajuId());

		setDefaultMsgTip(flag);

		forwardAction("/a/jiaju");

	}

	public void delete(Integer id) {

		vailPara(id);

		setDefaultMsgTip(service.delete(new Jiaju().setJiajuId(id)));

		forwardAction("/a/jiaju");

	}

}
