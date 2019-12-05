package com.lj.common.kit;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.lj.common.model.Jiancai;
import com.lj.common.model._MappingKit;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JianCaiKit {


    /*https://product.pchouse.com.cn/list/c*/
    public static final String JIAJU_URL = "http://mall.to8to.com/tag/dengshi/p";

    public static final String JDBC = "jdbc:mysql://127.0.0.1:3306/dec?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull";

    public static final String USER = "root";

    public static final String PASS = "123";

    //public static final String HTML = ".html";

    public static final int size = 40;

    public static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(2, 10, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));


    public JianCaiKit() {

        DruidPlugin druidPlugin = new DruidPlugin(JDBC, USER, PASS);

        druidPlugin.start();

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin.getDataSource());

        _MappingKit.mapping(activeRecordPlugin);

        activeRecordPlugin.start();

    }

    public void spider(int pages) {

        Connection.Response res;

        try {
            res = Jsoup.connect(JIAJU_URL + pages).userAgent("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0").execute();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //System.out.println(res.body());

        Document doc;

        try {
            doc = res.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Elements ele = doc.getElementById("goodsList").getElementsByTag("li");

        if (ele == null)
            return;

        Jiancai jiancai;

        int x = 0;

        for (Element e : ele) {

            System.out.println("-------------------正在抓取第" + x + "条信息-------------------");

            //System.out.println(e.html());

            jiancai = new Jiancai();

            if (e.getElementsByTag("img") != null) {

                String href = e.getElementsByTag("img").first().attr("data-original");
                jiancai.setJiancaiPic(/*href.contains("http") ? href : "http:" + */href);

                href = e.getElementsByTag("img").first().attr("alt");
                jiancai.setJiancaiTitle(/*href.contains("http") ? href : "http:" + */href);
            }

            /*if (e.getElementsByClass("bTit") != null) {
                jiancai.setJiancaiTitle(e.getElementsByClass("bTit").first().text());
            }*/

            jiancai.setJiancaiHot(80);

            if (e.getElementsByTag("a") != null) {
                String href = e.getElementsByTag("a").first().attr("href");
                jiancai.setJiancaiUrl(href);
            }

            if (e.getElementsByClass("f_arial") != null) {
                jiancai.setJiancaiPrice(e.getElementsByClass("f_arial").first().text());
            }

            jiancai.setJiancaiDate(new Date());

            //jiancai.save();

            getDetailInfo(jiancai);

            x++;


        }


    }


    private void getDetailInfo(Jiancai jiancai) {

        if (jiancai == null) {
            System.out.println("信息异常，正在调跳过");
            return;
        }

        Connection.Response res;

        try {
            res = Jsoup.connect(jiancai.getJiancaiUrl()).userAgent("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0").execute();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //System.out.println(res.body());

        Document doc;

        try {
            doc = res.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (doc == null)
            return;

        //System.out.println(doc.html());


        Elements element = doc.getElementsByClass("sec2_tab_content");

        if (element != null && element.get(1) != null) {

            jiancai.setJiancaiAuto(element.get(0).html().replaceAll("display: none",""));

            jiancai.save();

        }


    }




    /*public void spider(int pages, String types) {

        Connection.Response res = null;

        try {
            res = Jsoup.connect(JIAJU_URL + types + "_a1_" + pages * size + HTML).userAgent("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0").execute();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //System.out.println(res.body());

        Document doc = null;

        try {
            doc = res.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Elements ele = doc.getElementsByClass("cp-list");

        if (ele == null)
            return;

        Jiancai jiancai;

        int x = 0;

        for (Element e : ele) {

            System.out.println("-------------------正在抓取第" + x + "条信息-------------------");

            jiancai = new Jiancai();

            if (e.getElementsByClass("pic") != null) {
                String href = e.getElementsByClass("pic").first().attr("href");
                jiancai.setJiancaiUrl(href.contains("http") ? href : "http:" + href);
            }

            if (e.getElementsByClass("bTit") != null) {
                jiancai.setJiancaiTitle(e.getElementsByClass("bTit").first().text());
            }

            jiancai.setJiancaiHot(80);

            if (e.getElementsByTag("img") != null) {
                String href = e.getElementsByTag("img").first().attr("src");
                jiancai.setJiancaiPic(href.contains("http") ? href : "http:" + href);
            }

            if (e.getElementsByClass("price") != null) {
                jiancai.setJiancaiPrice(e.getElementsByClass("price").first().text());
            }

            jiancai.setJiancaiDate(new Date());

            jiancai.save();

            x++;


        }


    }*/


    public static void main(String[] args) {

        JianCaiKit jiaJuKit = new JianCaiKit();

        AtomicInteger atomicInteger = new AtomicInteger(0);

        for (int x = 0; x < 8; x++) {



           /* POOL_EXECUTOR.execute(
                    () -> {*/

            System.out.println("正在抓取执行第  " + atomicInteger.get() + "  页的信息");


            jiaJuKit.spider(atomicInteger.get());
            atomicInteger.getAndIncrement();






            /*  });*/

        }


    }
}
