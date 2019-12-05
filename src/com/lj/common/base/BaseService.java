package com.lj.common.base;

import com.jfinal.plugin.activerecord.Model;

public class BaseService {

	public <T extends Model<T>> boolean save(T t) {

		return t.save();

	}

	public <T extends Model<T>> boolean update(T t) {

		return t.update();

	}

	public <T extends Model<T>> boolean delete(T t) {

		return t.delete();

	}

	public <T extends Model<T>> T findById(T t, Object id) {

		return t.findById(id);

	}

	public <T extends Model<T>> T findById(T t, String key) {

		return t.findById(t.get(key));

	}

}
