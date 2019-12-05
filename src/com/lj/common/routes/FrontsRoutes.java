package com.lj.common.routes;

import com.jfinal.config.Routes;
import com.lj.controller.fengge.FenggeController;
import com.lj.controller.index.IndexController;
import com.lj.controller.jiaju.JiajuController;
import com.lj.controller.jiancai.JiancaiController;
import com.lj.controller.lianxi.LianxiController;
import com.lj.controller.luntan.LuntanController;

public class FrontsRoutes extends Routes {

	@Override
	public void config() {

		setBaseViewPath("frontView");
		
		add("/", IndexController.class);
		add("/jiaju", JiajuController.class);
		add("/jiancai", JiancaiController.class);
		add("/fengge", FenggeController.class);
		add("/luntan", LuntanController.class);
		add("/lianxi", LianxiController.class);

	}

}
