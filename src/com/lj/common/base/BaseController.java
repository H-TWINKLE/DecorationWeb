package com.lj.common.base;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.lj.common.model.User;
import com.lj.common.str.Constants;

public class BaseController extends Controller {

    public void vailParas(Object... objects) {

        for (Object object : objects) {
            if (object == null) {
                renderError(404);
                return;
            }
        }

    }

    public void vailPara(Object object) {

        if (object == null) {
            renderError(404);
            return;
        }

    }

    protected void setMsgTip(String valString) {

        if (StrKit.isBlank(valString))
            return;

        setAttr(Constants.TIP_MSG, valString);

    }

    protected void setMyUser(User user) {

        if (user != null) {
            setSessionAttr(Constants.MY_USER, user);
        }

    }

    protected User getMyUser() {

        return getSessionAttr(Constants.MY_USER);

    }

    protected void setTitle(String title) {
        setAttr(Constants.TITLE, title);
    }

    protected void setNavActive(String action) {
        setAttr(action, Constants.NAV_ACTIVE);
    }

    protected Integer getDefaultPages(Integer pages) {

        if (pages == null || pages == 0)
            return 1;
        return pages;

    }

    protected String getDefaultWhat(String what) {

        if (StrKit.isBlank(what))
            return "%%";
        return "%" + what + "%";

    }

    protected Integer getDefaultSize(Integer size) {

        if (size == null || size == 0)
            return 1;
        return size;

    }

    protected void setCommPlistByPagesWithAction(Page<?> list) {
        setAttr("plist", list);
        setAction(getNowMethodName());
    }

    protected void setCommPlistByPagesWithAction(Page<?> list, String what) {
        setAttr("plist", list);
        setAction(getNowMethodName(), what);
    }

    protected void setCommPlistByPagesWithAction(Ret ret, String what) {
        setAttr("ret_s", ret);
        setAction(getNowMethodName(), what);
    }

    private String setActionValue(String action) {

        StringBuffer s = new StringBuffer();

        s.append(getControllerKey());
        s.append("/");
        s.append(action);
        s.append("?");

        return s.toString();

    }

    private String setActionValue(String action, String what) {

        StringBuilder s = new StringBuilder();

        System.out.println(getControllerKey());

        if (!"/".equals(getControllerKey())) {
            s.append(getControllerKey());
        }
        s.append("/");
        s.append(action);
        s.append("?");
        s.append("what=");
        s.append(what);
        s.append("&");

        return s.toString();

    }

    protected void setAction(String action) {

        if (StrKit.isBlank(action))
            return;

        setAttr("action", setActionValue(action));
    }

    protected void setAction(String action, String what) {

        if (StrKit.isBlank(action) || StrKit.isBlank(what))
            return;

        setAttr("action", setActionValue(action, what.replaceAll("%", "")));
    }

    protected String getNowMethodName() {

        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[3];
        String methodName = e.getMethodName();
        return methodName;

    }

    protected boolean checkUserIsMy(User user) {

        User sessionUser = getMyUser();

        return sessionUser.getUserId().equals(user.getUserId());

    }

    protected void setDefaultMsgTip(boolean flag) {
        if (flag) {
            setMsgTip("操作成功");
        } else {
            setMsgTip("操作失败");
        }
    }

    protected <T extends Model<T>> boolean setDefaultSaveOrUpdate(T t, Integer id) {
        if (id != null) {
            return t.update();
        } else {
            return t.save();
        }
    }

    protected void setDefaultMsgTip(boolean flag, BaseListener bListener) {
        if (flag) {
            setMsgTip("操作成功");
            bListener.onsuccess();
        } else {
            setMsgTip("操作失败");
        }
    }

    protected void setDefaultMsgTip(boolean flag, BaseListener bListener, String tip) {
        if (flag) {
            setMsgTip(tip + "成功");
            bListener.onsuccess();
        } else {
            setMsgTip(tip + "失败");
        }
    }

    protected void setDefaultMsgTip(boolean flag, String tip) {
        if (flag) {
            setMsgTip(tip + "成功");
        } else {
            setMsgTip(tip + "失败");
        }
    }

}
