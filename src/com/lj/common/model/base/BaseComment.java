package com.lj.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseComment<M extends BaseComment<M>> extends Model<M> implements IBean {

	public M setCommentId(java.lang.Integer commentId) {
		set("comment_id", commentId);
		return (M)this;
	}
	
	public java.lang.Integer getCommentId() {
		return getInt("comment_id");
	}

	public M setCommentAuthor(java.lang.Integer commentAuthor) {
		set("comment_author", commentAuthor);
		return (M)this;
	}
	
	public java.lang.Integer getCommentAuthor() {
		return getInt("comment_author");
	}

	public M setCommentContent(java.lang.String commentContent) {
		set("comment_content", commentContent);
		return (M)this;
	}
	
	public java.lang.String getCommentContent() {
		return getStr("comment_content");
	}

	public M setCommentPost(java.lang.Integer commentPost) {
		set("comment_post", commentPost);
		return (M)this;
	}
	
	public java.lang.Integer getCommentPost() {
		return getInt("comment_post");
	}

	public M setCommentDate(java.util.Date commentDate) {
		set("comment_date", commentDate);
		return (M)this;
	}
	
	public java.util.Date getCommentDate() {
		return get("comment_date");
	}

}
