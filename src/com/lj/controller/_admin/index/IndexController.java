package com.lj.controller._admin.index;

import com.jfinal.aop.Inject;
import com.jfinal.upload.UploadFile;
import com.lj.common.base.BaseController;
import com.lj.common.model.User;
import com.lj.controller.index.IndexService;

public class IndexController extends BaseController {

	@Inject
	IndexService service;

	public void index(Integer pages) {

		setTitle("易家 - 家居 管理员平台");

		pages = getDefaultPages(pages);

		setCommPlistByPagesWithAction(service.getUser(pages, 10));

		render("index.html");

	}

	public void editUser(Integer user_id) {
		
		setTitle("编辑用户");

		User user = service.findById(User.dao, user_id);

		setAttr("user", user);

		render("edit.html");

	}

	public void updateUser() {

		UploadFile file = getFile("file");

		User user = getModel(User.class, "user");

		if (file != null) {
			user.setUserPic("/upload/temp/" + file.getFileName());

		}

		setDefaultMsgTip(service.update(user), () -> {

			if (checkUserIsMy(user)) {
				setMyUser(service.findById(user, "user_id"));
			}

		});

		forwardAction("/admin/index");

	}

	public void deleteUser(Integer user_id) {

		vailPara(user_id);

		setDefaultMsgTip(service.delete(new User().setUserId(user_id)));

		forwardAction("/admin/index");

	}

}
