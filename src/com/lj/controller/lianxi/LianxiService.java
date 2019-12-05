package com.lj.controller.lianxi;

import com.jfinal.plugin.activerecord.Page;
import com.lj.common.base.BaseService;
import com.lj.common.model.Contact;

public class LianxiService extends BaseService {
	public Page<Contact> getListContact(Integer pages, Integer size) {

		return Contact.dao.paginate(pages, size, "SELECT *",
				"FROM contact LEFT JOIN user ON contact_author=user_id ORDER BY contact_date DESC ");

	}

	public Page<Contact> getListContact(Integer pages, Integer size, Integer user_id) {

		return Contact.dao.paginate(pages, size, "SELECT *",
				"FROM contact LEFT JOIN user ON contact_author=user_id where user_id=? ORDER BY contact_date DESC ",
				user_id);

	}
}
