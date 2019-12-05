package com.lj.controller.index;

import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.lj.common.base.BaseController;
import com.lj.common.interceptor.OverallInterceptor;
import com.lj.common.model.User;
import com.lj.common.str.Constants;

import java.util.Date;
import java.util.List;

public class IndexController extends BaseController {

    @Inject
    IndexService service;

    @Clear(OverallInterceptor.class)
    public void index() {

        setTitle("易家 &mdash; 装修");
        setAttr("index", true);
        setAttr("pengge_list", service.getListFenggeHot(4));
        render("index.html");

    }

    @Clear(OverallInterceptor.class)
    public void login() {

        setTitle("登录");
        render("login.html");

    }

    @Clear(OverallInterceptor.class)
    public void register() {

        setTitle("注册");
        render("sign-up.html");

    }

    @Clear(OverallInterceptor.class)
    public void toLogin(String admin, String password) {

        if (StrKit.isBlank(admin)) {
            setMsgTip("请输入用户名");
            forwardAction("/login");
            return;
        }

        if (StrKit.isBlank(admin)) {
            setMsgTip("请输入密码");
            forwardAction("/login");
            return;
        }

        User user = service.findUser(admin);

        if (user == null) {
            setMsgTip("用户不存在");
            forwardAction("/login");
            return;

        }

        if (encryptionString(password, user.getUserSalt()).equals(user.getUserPass())) {

            setMyUser(user);
            redirect("/index");
            return;
        } else {
            setMsgTip("用户名或者密码错误");
            forwardAction("/login");
            return;
        }

    }

    @Clear(OverallInterceptor.class)
    public void toRegister(String pass2) {

        User user = getModel(User.class, "u");

        if (StrKit.isBlank(user.getUserAccount())) {

            setMsgTip("请输入用户名");
            forwardAction("/register");
            return;
        }

        if (StrKit.isBlank(user.getUserEmail())) {

            setMsgTip("请输入邮箱");
            forwardAction("/register");
            return;
        }

        if (StrKit.isBlank(user.getUserPass())) {

            setMsgTip("请输入密码");
            forwardAction("/register");
            return;
        }

        if (StrKit.isBlank(pass2)) {

            setMsgTip("请再次输入密码");
            forwardAction("/register");
            return;
        }

        if (!user.getUserPass().equals(pass2)) {

            setMsgTip("两次密码不同，请重新输入");
            forwardAction("/register");
            return;
        }

        if (service.findUser(user.getUserAccount()) != null) {
            setMsgTip("该用户已经存在，请重新输入");
            forwardAction("/register");
            return;
        }

        user.setUserSalt(HashKit.generateSaltForSha256());
        user.setUserPass(encryptionString(user.getUserPass(), user.getUserSalt()));
        user.setUserPic(Constants.PIC);
        user.setUserDates(new Date());

        if (service.save(user)) {
            setMsgTip("注册成功，请登录");
            forwardAction("/login");
            return;
        } else {
            setMsgTip("注册失败，请重试");
            forwardAction("/register");
            return;
        }

    }

    public void logOut() {

        removeSessionAttr(Constants.MY_USER);
        redirect("/");

    }

    public void myinfo() {

        setTitle("个人中心");

        render("myinfo.html");

    }

    public void updateInfo() {

        UploadFile uploadFile = getFile("file");

        User user = getModel(User.class);

        if (uploadFile != null) {
            user.setUserPic("/upload/temp/" + uploadFile.getFileName());
        }

        setDefaultMsgTip(service.update(user), () -> {

            setMyUser(service.findById(User.dao, getMyUser().getUserId()));

        });

        forwardAction("/myinfo");

    }

    @Clear(OverallInterceptor.class)
    public void search(Integer pages, String what) {

        setTitle("搜索" + (StrKit.isBlank(what) ? "" : what));

        pages = getDefaultPages(pages);

        what = getDefaultWhat(what);

        setCommPlistByPagesWithAction(service.getJiajuAndJiancai(pages, what), what);

        render("search.html");


    }

    @Clear(OverallInterceptor.class)
    public void upload() {

        List<UploadFile> uploadFile = getFiles();

        if (uploadFile != null && uploadFile.size() > 0) {
            StringBuilder s = new StringBuilder();

            for (UploadFile uploadFile1 : uploadFile) {

                s.append("/upload/temp/").append(uploadFile1.getFileName());
                s.append(",");
            }
            renderText(s.toString());

        } else {
            renderText("null");
        }

    }


    private String encryptionString(String val, String salt) {

        if (StrKit.isBlank(val) || StrKit.isBlank(salt))
            return null;

        return HashKit.sha256(val + salt);

    }

}
