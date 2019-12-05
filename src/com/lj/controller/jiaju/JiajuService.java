package com.lj.controller.jiaju;

import com.jfinal.plugin.activerecord.Page;
import com.lj.common.base.BaseService;
import com.lj.common.model.Jiaju;

public class JiajuService extends BaseService {

	public Page<Jiaju> getListJiaju(Integer pages, Integer size) {

		return Jiaju.dao.paginate(pages, size, "SELECT *", "FROM jiaju ORDER BY jiaju_date DESC ");

	}

}
