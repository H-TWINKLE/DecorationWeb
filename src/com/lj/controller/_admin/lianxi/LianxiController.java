package com.lj.controller._admin.lianxi;

import com.jfinal.aop.Inject;
import com.lj.common.base.BaseController;
import com.lj.common.model.Contact;
import com.lj.controller.lianxi.LianxiService;

public class LianxiController extends BaseController {

	@Inject
	LianxiService service;

	public void index(Integer pages) {

		pages = getDefaultPages(pages);

		setTitle("联系管理");

		setCommPlistByPagesWithAction(service.getListContact(pages, 10));

		render("index.html");

	}

	public void edit(Integer id) {

		Contact contact = service.findById(Contact.dao, id);

		setAttr("contact", contact);
		
		setTitle("联系回复");

		render("edit.html");

	}

	public void update() {

		Contact contact = getModel(Contact.class, "contact");

		boolean flag = setDefaultSaveOrUpdate(contact, contact.getContactId());

		setDefaultMsgTip(flag);

		forwardAction("/a/lianxi");

	}

	public void delete(Integer id) {

		vailPara(id);

		setDefaultMsgTip(service.delete(new Contact().setContactId(id)));

		forwardAction("/a/lianxi");

	}

}
