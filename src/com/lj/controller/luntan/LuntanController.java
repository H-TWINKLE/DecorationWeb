package com.lj.controller.luntan;

import java.util.Date;

import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.lj.common.base.BaseController;
import com.lj.common.interceptor.OverallInterceptor;
import com.lj.common.model.Comment;
import com.lj.common.model.Post;

public class LuntanController extends BaseController {

	@Inject
	LuntanService service;

	@Clear(OverallInterceptor.class)
	public void index(Integer pages, String what) {

		setTitle("论坛");

		pages = getDefaultPages(pages);

		what = getDefaultWhat(what);

		setCommPlistByPagesWithAction(service.getListPost(pages, 10, what), what);

		render("index.html");

	}

	@Clear(OverallInterceptor.class)
	public void detail(Integer id) {

		vailPara(id);

		Post post = service.findById(Post.dao, id);

		vailPara(post);
		
		setTitle("帖子详情");

		setAttr("post", post);

		render("detail.html");

	}

	public void myPost(Integer pages) {

		setTitle("我的帖子");

		pages = getDefaultPages(pages);

		setCommPlistByPagesWithAction(service.getListPost(pages, 10, getMyUser().getUserId()));

		render("mypost.html");

	}

	public void addPost() {

		Post post = getModel(Post.class, "p");

		post.setPostAuthor(getMyUser().getUserId());
		post.setPostDate(new Date());
		post.setPostHot(80);

		setDefaultMsgTip(service.save(post));

		forwardAction("/luntan/myPost");

	}

	@Clear(OverallInterceptor.class)
	public void addcommment(String content, Integer id) {

		if (getMyUser() == null) {
			setMsgTip("请登录后，再评论");
			forwardAction("/luntan/detail");
			return;
		}

		Comment comment = new Comment();
		comment.setCommentAuthor(getMyUser().getUserId());
		comment.setCommentContent(content);
		comment.setCommentDate(new Date());
		comment.setCommentPost(id);

		setDefaultMsgTip(service.save(comment));

		forwardAction("/luntan/detail");

	}

}
