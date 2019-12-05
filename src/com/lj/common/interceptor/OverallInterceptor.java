package com.lj.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.lj.common.annotation.Title;
import com.lj.common.model.User;
import com.lj.common.str.Constants;

public class OverallInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation arg0) {

		User user = getUser(arg0);

		if (user == null) {

			arg0.getController().redirect("/login");
			return;
		}

		if ("admin".contains(arg0.getActionKey())) {

			if (!isAdmin(user)) {
				arg0.getController().forwardAction("/index");
				arg0.getController().setAttr(Constants.TIP_MSG, "权限不足");

				return;
			}

		}

		setTitle(arg0);

		arg0.invoke();

	}

	private User getUser(Invocation inv) {

		Controller controller = inv.getController();

		return controller.getSessionAttr(Constants.MY_USER);

	}

	private boolean isAdmin(User user) {

		return user.getUserPlate() == 2;

	}

	private void setTitle(Invocation arg0) {
		if (arg0.getMethod().isAnnotationPresent(Title.class)) {
			arg0.getController().setAttr("title", arg0.getMethod().getAnnotation(Title.class).value());
		}
	}

}
