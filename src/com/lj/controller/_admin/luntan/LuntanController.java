package com.lj.controller._admin.luntan;

import java.util.Date;

import com.jfinal.aop.Inject;
import com.lj.common.base.BaseController;
import com.lj.common.model.Post;
import com.lj.controller.luntan.LuntanService;

public class LuntanController extends BaseController {

	@Inject
	LuntanService service;

	public void index(Integer pages) {

		pages = getDefaultPages(pages);

		setTitle("论坛管理");

		setCommPlistByPagesWithAction(service.getListPost(pages, 10));

		render("index.html");

	}

	public void edit(Integer id) {

		Post post = service.findById(Post.dao, id);
		
		setTitle("帖子详情");

		setAttr("post", post);

		render("edit.html");

	}

	public void update() {

		Post post = getModel(Post.class, "post");

		if (post.getPostId() == null) {
			post.setPostDate(new Date());
			post.setPostAuthor(getMyUser().getUserId());
		}

		boolean flag = setDefaultSaveOrUpdate(post, post.getPostId());

		setDefaultMsgTip(flag);

		forwardAction("/a/luntan");

	}

	public void delete(Integer id) {

		vailPara(id);

		setDefaultMsgTip(service.delete(new Post().setPostId(id)));

		forwardAction("/a/luntan");

	}

}
