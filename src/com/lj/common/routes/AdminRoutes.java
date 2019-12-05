package com.lj.common.routes;

import com.jfinal.config.Routes;
import com.lj.controller._admin.fengge.FenggeController;
import com.lj.controller._admin.index.IndexController;
import com.lj.controller._admin.jiaju.JiajuController;
import com.lj.controller._admin.jiancai.JiancaiController;
import com.lj.controller._admin.lianxi.LianxiController;
import com.lj.controller._admin.luntan.LuntanController;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		setBaseViewPath("adminView");

		add("/admin", IndexController.class, "index");
		add("/a/jiaju", JiajuController.class, "jiaju");
		add("/a/jiancai", JiancaiController.class, "jiancai");
		add("/a/fengge", FenggeController.class, "fengge");
		add("/a/luntan", LuntanController.class, "luntan");
		add("/a/lianxi", LianxiController.class, "lianxi");
	}

}