package com.lj.controller.fengge;

import com.jfinal.plugin.activerecord.Page;
import com.lj.common.base.BaseService;
import com.lj.common.model.Fengge;

public class FenggeService extends BaseService {

	public Page<Fengge> getListFengge(Integer pages, Integer size) {

		return Fengge.dao.paginate(pages, size, "SELECT *", "FROM fengge ORDER BY fengge_date DESC ");

	}

}
