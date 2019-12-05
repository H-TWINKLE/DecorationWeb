package com.lj.common.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.lj.common.interceptor.OverallInterceptor;
import com.lj.common.model._MappingKit;
import com.lj.common.routes.AdminRoutes;
import com.lj.common.routes.FrontsRoutes;

public class MainConfig extends JFinalConfig {
	/**
	 * 将全局配置提出来 方便其他地方重用
	 */
	private WallFilter wallFilter;

	/**
	 * 配置JFinal常量
	 */
	@Override
	public void configConstant(Constants me) {

		PropKit.use("config-dev.properties");
		// 开发模式
		me.setDevMode(PropKit.getBoolean("devMode"));
		// 设置默认上传文件保存路径 getFile等使用
		me.setBaseUploadPath("upload/temp/");
		// 设置上传最大限制尺寸
		// me.setMaxPostSize(1024*1024*10);
		// 设置默认下载文件路径 renderFile使用
		me.setBaseDownloadPath("download");
		// 设置默认视图类型
		me.setViewType(ViewType.JFINAL_TEMPLATE);
		me.setError404View("/comm/404.html");
		me.setError500View("/comm/500.html");
		// 设置json工厂
		me.setJsonFactory(MixedJsonFactory.me());

		// 设置启用依赖注入
		me.setInjectDependency(true);

	}

	/**
	 * 配置项目路由 路由拆分到 FrontRutes 与 AdminRoutes 之中配置的好处： 1：可分别配置不同的 baseViewPath 与
	 * Interceptor 2：避免多人协同开发时，频繁修改此文件带来的版本冲突 3：避免本文件中内容过多，拆分后可读性增强 4：便于分模块管理路由
	 */
	@Override
	public void configRoute(Routes me) {
		// 推荐拆分方式 如果需要就解开注释 创建对应的 Routes
		me.add(new AdminRoutes());
		me.add(new FrontsRoutes());

	}

	/*
	 * public static boolean isServiceEnvironment() { return
	 * JFinal.me().getContextPath().equals("Decoration"); }
	 */

	/**
	 * 获取数据库插件 抽取成独立的方法，便于重用该方法，减少代码冗余
	 */
	public static DruidPlugin getDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
	}

	/**
	 * 配置JFinal插件 数据库连接池 ActiveRecordPlugin 缓存 定时任务 自定义插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 配置数据库连接池插件
		DruidPlugin dbPlugin = getDruidPlugin();
		wallFilter = new WallFilter(); // 加强数据库安全
		wallFilter.setDbType("mysql");
		dbPlugin.addFilter(wallFilter);
		dbPlugin.addFilter(new StatFilter()); // 添加 StatFilter 才会有统计数据

		// 数据映射 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dbPlugin);
		arp.setShowSql(PropKit.getBoolean("devMode"));
		arp.setDialect(new MysqlDialect());
		dbPlugin.setDriverClass("com.mysql.jdbc.Driver");
		/******** 在此添加数据库 表-Model 映射 *********/
		// 如果使用了JFinal Model 生成器 生成了BaseModel 把下面注释解开即可
		_MappingKit.mapping(arp);

		// 添加到插件列表中
		me.add(dbPlugin);
		me.add(arp);

	}

	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new SessionInViewInterceptor());
		me.add(new OverallInterceptor());
	}

	/**
	 * 配置全局处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("base"));
	}

	/**
	 * 配置模板引擎
	 */
	@Override
	public void configEngine(Engine me) {
		// 配置模板支持热加载
		me.setDevMode(PropKit.getBoolean("devMode", false));
		// 这里只有选择JFinal TPL的时候才用
		// 配置共享函数模板
		me.addSharedFunction("/comm/commLayout.html");
		me.addSharedFunction("/comm/commIndex.html");
		me.addSharedFunction("/comm/commAdminLayout.html");
		me.addSharedFunction("/comm/commPagenation.html");
	}

	public static void main(String[] args) {
		JFinal.start("WebRoot", 1314, "/", 5);
	}

}