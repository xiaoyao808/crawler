package com.cpx.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class TetWechatMenu {
    public static void main(String[] args) throws IOException {
        //获取ACCESS_TOKEN
        String access_tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=wxceefc0a0cfb35f45&appid=APPID&secret=19d5b5232b29d3a6d3f8a163edfb05e6";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(access_tokenUrl);
        HttpResponse execute = httpClient.execute(get);
        String tokenRsult = EntityUtils.toString(execute.getEntity());

        JSONObject jsonObject = JSONObject.fromObject(tokenRsult);
        //String access_token = (String) jsonObject.get("access_token");
        String access_token = "21_24lVZYoedL0xE2-nJxIT2x1gcep-hFxhh8vWeUmgAGWiIzwIWzZrFDUXtxXh3EupJuDhnbAtyv4Q-AuZ30YhD5SJC9kuqf8HRTxA1Rrn_rjaJ60x-WhVlCmqlK3r-5IhzXGfbfnEOZAUCaj2TOXdAFAZUD";
        String menuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
        String menuParam = "{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲123456\",\"key\":\"V1001_TODAY_MUSIC\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"miniprogram\",\"name\":\"wxa\",\"url\":\"http://mp.weixin.qq.com\",\"appid\":\"wx286b93c14bbf93aa\",\"pagepath\":\"pages/lunar/index\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}";

        HttpClient httpClien = new DefaultHttpClient();
        HttpPost post = new HttpPost(menuUrl);
        StringEntity entity = new StringEntity(menuParam, Charset.forName("UTF-8"));
        post.setEntity(entity);
        HttpResponse httpResponse = httpClien.execute(post);
       //返回过来的实体对象转成string类型
        String toString = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(toString);
    }
}
