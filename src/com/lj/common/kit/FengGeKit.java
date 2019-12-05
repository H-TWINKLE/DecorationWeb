package com.lj.common.kit;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.lj.common.model.Fengge;
import com.lj.common.model._MappingKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

public class FengGeKit {


    public static final String JIAJU_URL = "http://www.zx123.cn/zxfg/";

    public static final String JDBC = "jdbc:mysql://127.0.0.1:3306/dec?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull";

    public static final String USER = "root";

    public static final String PASS = "123";

    public FengGeKit() {

        DruidPlugin druidPlugin = new DruidPlugin(JDBC, USER, PASS);

        druidPlugin.start();

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin.getDataSource());

        _MappingKit.mapping(activeRecordPlugin);

        activeRecordPlugin.start();

    }


    public void spider(int pages) {

        Document doc = null;


        try {
            doc = Jsoup.connect(JIAJU_URL + (pages == 0 ? "" : pages + ".html")).userAgent("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0").get();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        Elements elements = doc.getElementsByClass("aasd");

        if (elements == null) return;

        Fengge fengge;

        int x = 0;

        for (Element element : elements) {

            System.out.println("-------------------正在抓取第" + x + "条信息-------------------");

            fengge = new Fengge();

            if (element.getElementsByTag("img ") != null) {

                fengge.setFenggeTitle(element.getElementsByTag("img ").attr("alt"));

            }

            if (element.getElementsByClass("anredu") != null) {
                fengge.setFenggeAuto(element.getElementsByClass("anredu").text());
            }

            fengge.setFenggeHot("80");
            fengge.setFenggeDate(new Date());

            if (element.getElementsByTag("a") != null) {
                String url = element.getElementsByTag("a").first().attr("href");

                spiderChild(url, fengge);


            }

            x++;
        }


    }


    private void spiderChild(String url, Fengge fengge) {

        if (StrKit.isBlank(url))
            return;

        Document doc = null;

        try {
            doc = Jsoup.connect(url).userAgent("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0").get();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Elements ele = doc.getElementsByClass("left_dow");

        if (ele == null)
            return;

        Element element = ele.first();

        Elements elements = element.getElementsByTag("p");

        if (elements != null)
            fengge.setFenggeContent(elements.text());

        elements = element.getElementsByTag("img");

        int x = 0;

        StringBuilder s = new StringBuilder();

        if (elements != null) {

            for (Element element1 : elements) {

                String src = element1.attr("src");

                s.append(src.contains("http") ? src : "http:" + src);

                if (x < elements.size()) {
                    s.append(",");
                }

                x++;


            }


        }

        fengge.setFenggePic(s.toString());

        fengge.save();


    }


    public static void main(String[] args) {

        FengGeKit fengGeKit = new FengGeKit();

        for (int x = 0; x < 5; x++) {

            System.out.println("正在抓取执行第  " + x + "  页的的信息");
            fengGeKit.spider(x);

        }
    }


}
