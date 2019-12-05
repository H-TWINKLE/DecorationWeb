package com.lj.controller._admin.jiancai;

import java.util.Date;

import com.jfinal.aop.Inject;
import com.jfinal.upload.UploadFile;
import com.lj.common.base.BaseController;
import com.lj.common.model.Jiancai;
import com.lj.controller.jiancai.JiancaiService;

public class JiancaiController extends BaseController {

	@Inject
	JiancaiService service;

	public void index(Integer pages) {

		pages = getDefaultPages(pages);

		setTitle("建材管理");

		setCommPlistByPagesWithAction(service.getListJiancai(pages, 10));

		render("index.html");

	}

	public void edit(Integer id) {

		Jiancai jiancai = service.findById(Jiancai.dao, id);
		
		setTitle("建材详情");

		setAttr("jiancai", jiancai);

		render("edit.html");

	}

	public void update() {

		UploadFile file = getFile("file");

		Jiancai jiancai = getModel(Jiancai.class, "jiancai");

		if (file != null) {
			jiancai.setJiancaiPic("/upload/temp/" + file.getFileName());

		}

		if (jiancai.getJiancaiId() == null) {
			jiancai.setJiancaiDate(new Date());
		}

		boolean flag = setDefaultSaveOrUpdate(jiancai, jiancai.getJiancaiId());

		setDefaultMsgTip(flag);

		forwardAction("/a/jiancai");

	}

	public void delete(Integer id) {

		vailPara(id);

		setDefaultMsgTip(service.delete(new Jiancai().setJiancaiId(id)));

		forwardAction("/a/jiancai");

	}

}
