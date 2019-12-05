package com.lj.controller.lianxi;

import java.util.Date;


import com.jfinal.aop.Inject;
import com.lj.common.base.BaseController;
import com.lj.common.model.Contact;

public class LianxiController extends BaseController {

	@Inject
	LianxiService service;

	
	public void index(Integer pages) {

		setTitle("联系");

		pages = getDefaultPages(pages);
		setCommPlistByPagesWithAction(service.getListContact(pages, 10, getMyUser().getUserId()));
		render("index.html");

	}

	public void add() {

		if (getMyUser() == null) {
			setMsgTip("请注册后再和我们联系");
			forwardAction("/lianxi");
			return;
		}

		Contact contact = getModel(Contact.class,"c");

		contact.setContactAuthor(getMyUser().getUserId());
		contact.setContactDate(new Date());

		setDefaultMsgTip(service.save(contact));

		forwardAction("/lianxi");

	}

}
