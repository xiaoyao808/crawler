package com.cpx.service;

import com.cpx.interfaces.JsoupService;
import com.cpx.mapper.JsoupMapper;
import com.cpx.pojo.AppInfo;
import com.cpx.utils.ReadJsoupLogs;
import net.sf.json.JSONObject;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import com.cpx.utils.ReadJsoupLogs;

import javax.jms.*;
import java.io.IOException;
import java.util.UUID;

@Service
public class JsoupServiceImpl implements JsoupService {
    @Autowired
    private JsoupMapper jsoupMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination queueTestDestination;

    private  boolean jsoupFlag = false;
    private int typeNum = 1;
    private int pageNum = 1;
    private int appNum = 0;
    private Logger logger = LogManager.getLogger(JsoupServiceImpl.class);

    String user = ActiveMQConnection.DEFAULT_USER;

    String password = ActiveMQConnection.DEFAULT_PASSWORD;

    String url = "tcp://localhost:61616/";

    String subject = "test.queue";

    //创建连接工厂
   /* ConnectionFactory contectionFactory = new ActiveMQConnectionFactory( user, password, url);

    public void sendMessageToMq(String json){


        try{
            //创建连接
            Connection connection = contectionFactory.createConnection();

            connection.start();
            //创建session会话
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(subject);

            MessageProducer producer = session.createProducer(destination);


            TextMessage message = session.createTextMessage();

            message.setText(json);

            producer.send(message);

            System.out.println("--发送消息：");



            session.commit();

            session.close();

            connection.close();

        }catch (JMSException e) {

            e.printStackTrace();

        }
    }*/

    public void switchJsoup(boolean flag) {
        if(flag){
            jsoupFlag = false;
            jsouLq();
        }else{
          jsoupFlag = true;
        }
    }
    private static Document doc = null;

    public void jsouLq() {

        /**
         *
         * 读取日志，如果日志中有值给
         * private int typeNum = 1;
         *     private int pageNum = 1;
         *     private int appNum = 1;
         *
         * 如果日志中没有值，那就继续从1开始爬取
         */
        try{
            JSONObject jsonObject = ReadJsoupLogs.result();
            typeNum = Integer.parseInt(jsonObject.get("typeNum").toString());
            pageNum = Integer.parseInt(jsonObject.get("pageNum").toString());
            appNum = Integer.parseInt(jsonObject.get("appNum").toString())+1;
        }catch (Exception e){
                logger.error("日志为空，初始化爬取数据");
        }
        //访问要爬取的页面
        try {
            doc = Jsoup.connect("https://www.liqucn.com/rj/").get();
        }catch (IOException r){
            r.printStackTrace();
        }
        //获取页面中的元素并且循环查询
        Elements elements = doc.select("div.sift a");

        for (int i = typeNum; i < elements.size(); i++) {
            //记录爬取到第几个类型的数据
             typeNum = i;
            //如果穿入的值为false则停止循环
            if(jsoupFlag){
                break;
            }
            Element a = elements.get(i);
            String href = a.attr("href");
            try{
                getType(href);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        typeNum = 1;
    }
    public void getType(String href){
          int endPageNum = 0;
        try {
            doc = Jsoup.connect(href).get();
            String href1 = doc.select("div.page a").attr("href");
            doc = Jsoup.connect(href + href1).get();
           endPageNum = Integer.parseInt(doc.select("div.page span.current").text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i =pageNum ; i < endPageNum; i++) {
            pageNum = i;
            //如果穿入的值为false则停止循环
            if(jsoupFlag){
                break;
            }
            try {
                doc = Jsoup.connect(href+"?page="+i).get();
            }catch (IOException e){
                e.printStackTrace();
            }
            Elements select = doc.select("ul.tip_blist li");
            for (int j = appNum; j < select.size(); j++) {
                appNum = j;
                //如果穿入的值为false则停止循环
                if(jsoupFlag){
                    break;
                }
                String appHref = select.get(j).select("div.tip_list a").first().attr("href");
                getAppInfo(appHref);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("typeNum",typeNum);
                jsonObject.put("pageNum",pageNum);
                jsonObject.put("appNum",appNum);
                logger.fatal("position="+jsonObject.toString());

            }
            appNum = 0;
        }
        pageNum = 1;

    }
    public void getAppInfo(String href){
        try {
            doc = Jsoup.connect(href).get();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return;
        }
        String imagePath = doc.select("div.info_con img").first().attr("src");
        String appName = doc.select("div.info_con h1").text();
        String appType = doc.select("div.info_con em").first().text();
        String appTime = doc.select("div.info_con em").get(1).text();
        String appSystem = doc.select("div.info_con p").last().select("em").first().text();
        String appCompany = doc.select("div.info_con p").last().select("em").last().text();
        Elements appUrlElement = doc.select("div.apk_btn");

        String appAndroidDownUrl = null;
        String iosAndroidDownUrl = null;
        //判断是安卓版还是苹果版本并且获取相应的下载路径
        for (int i=0;i<appUrlElement.size();i++){
            if(appUrlElement.get(i).select(".btn_android")!=null){
                String appAndroidHref = appUrlElement.get(i).select(".btn_android").attr("href");
                try {
                    doc = Jsoup.connect(appAndroidHref).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                appAndroidDownUrl = doc.select("div.apk_btn a").attr("href");
            }
//            if(appUrlElement.get(i).select(".btn_ios")!=null){
//                String iosAndroidHref = appUrlElement.get(i).select(".btn_ios").attr("href");
//                doc = Jsoup.connect(iosAndroidHref).get();
//                doc.select("");
//            }
        }

        AppInfo appInfo = new AppInfo();
        String appid = UUID.randomUUID().toString();
        appInfo.setId(appid);
        appInfo.setName(appName);
        appInfo.setAppSize("");
        appInfo.setAppSystem(appSystem);
        appInfo.setType(appType);
        appInfo.setStatus(0);

        jsoupMapper.insert(appInfo);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid",appid);
        jsonObject.put("imagePath",imagePath);
        jsonObject.put("appAndroidDownUrl",appAndroidDownUrl);
        sendMessageToMq(jsonObject.toString());
    }
   private void sendMessageToMq(final String jsonObject){
        jmsTemplate.send(queueTestDestination ,new MessageCreator(){
            public Message createMessage(Session session) throws JMSException {
              return  session.createTextMessage(jsonObject);
            }
        });
    }
}
