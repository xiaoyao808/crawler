package com.cpx.service;

import com.cpx.mapper.JsoupMapper;
import com.cpx.pojo.AppInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MyActivemqMessageListener implements MessageListener {
    @Autowired
    private JsoupMapper jsoupMapper;
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            JSONObject json = JSONObject.fromObject(text);

            System.out.println(json.get("appid")+"---"+json.get("imagePath")+"---"+json.get("appAndroidDownUrl"));

            AppInfo appInfo = new AppInfo();

            String appid = json.get("appid").toString();

            //下载图片
        //    String imageUrl = json.get("imagePath").toString();
        //    String fileName = FileUtils.getFileName(imageUrl);
//            String imagePath = FileUtils.saveBinary(imageUrl, fileName, "image");
           /* InputStream in = FileUtils.saveBinary(imageUrl, fileName);
            String imagePath = FtpUtils.uploadFile("image", UUID.randomUUID().toString()+fileName, in);
            //下载应用
            String appUrl = json.get("imagePath").toString();
            String appfileName = FileUtils.getFileName(appUrl);
            InputStream appIn = FileUtils.saveBinary(appUrl, fileName);
            String appPath = FtpUtils.uploadFile("androidApp",UUID.randomUUID().toString()+fileName, in);*/

          /*  appInfo.setId(appid);
            appInfo.setAppIcon(imagePath);
            appInfo.setAppApkPath(appPath);
            appInfo.setStatus(1);
            appMapper.update(appInfo);*/

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }/* catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
