package com.lj.controller.luntan;

import com.jfinal.plugin.activerecord.Page;
import com.lj.common.base.BaseService;
import com.lj.common.model.Post;

public class LuntanService extends BaseService {

	public Page<Post> getListPost(Integer pages, Integer size) {

		return Post.dao.paginate(pages, size, "SELECT *",
				"FROM post LEFT JOIN user ON post_author=user_id ORDER BY post_date DESC ");

	}

	public Page<Post> getListPost(Integer pages, Integer size, String what) {

		return Post.dao.paginate(pages, size, "SELECT *",
				"FROM post LEFT JOIN user ON post_author=user_id WHERE post_title LIKE ? ORDER BY post_date DESC ",
				what);

	}

	public Page<Post> getListPost(Integer pages, Integer size, Integer user_id) {

		return Post.dao.paginate(pages, size, "SELECT *",
				"FROM post LEFT JOIN user ON post_author=user_id WHERE user_id=? ORDER BY post_date DESC ", user_id);

	}

}
