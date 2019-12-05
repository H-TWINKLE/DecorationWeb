package com.lj.controller.jiancai;

import com.jfinal.plugin.activerecord.Page;
import com.lj.common.base.BaseService;
import com.lj.common.model.Jiancai;

public class JiancaiService extends BaseService {

	public Page<Jiancai> getListJiancai(Integer pages, Integer size) {

		return Jiancai.dao.paginate(pages, size, "SELECT *", "FROM jiancai ORDER BY jiancai_date DESC ");

	}

}
