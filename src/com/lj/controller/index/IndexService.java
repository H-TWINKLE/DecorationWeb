package com.lj.controller.index;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.lj.common.base.BaseService;
import com.lj.common.model.Fengge;
import com.lj.common.model.Jiaju;
import com.lj.common.model.Jiancai;
import com.lj.common.model.User;

import java.util.List;

public class IndexService extends BaseService {

    public User findUser(String account) {

        return User.dao.findFirst("SELECT * FROM user WHERE user_account=? LIMIT 1", account);

    }

    public Page<User> getUser(Integer pages, Integer size) {
        return User.dao.paginate(pages, size, "SELECT *", "FROM user ORDER BY user_dates DESC");
    }

    public List<Fengge> getListFenggeHot(Integer size) {

        return Fengge.dao.find("SELECT * FROM fengge where fengge_hot>=100 ORDER BY fengge_hot DESC LIMIT 0," + size);

    }


    public Ret getJiajuAndJiancai(Integer pages, String what) {

        Ret ret = Ret.create();

        Page<Jiaju> jiaju = Jiaju.dao.paginate(pages, 9, "SELECT *", "FROM jiaju WHERE jiaju_title LIKE ? ORDER BY jiaju_date DESC", what);
        Page<Jiancai> jiancai = Jiancai.dao.paginate(pages, 9, "SELECT *", "FROM jiancai WHERE jiancai_title LIKE ? ORDER BY jiancai_date DESC", what);

        return ret.set("jiaju", jiaju).set("jiancai", jiancai);


    }


}
