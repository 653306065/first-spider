package org.example.tools;

import okhttp3.*;

import java.util.concurrent.TimeUnit;

public class OKHttpUtils {

    static OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();

    /**
     * 发送get的http请求，返回html字符串
     * @param url
     * @return
     */
    public static String get(String url) {
        try {
            //构建请求对象
            Request request = new Request.Builder().get().url(url).build();
            //执行请求，获取响应
            Response response = httpClient.newCall(request).execute();
            //判断响应是否超过
            if (response.isSuccessful()) {
                //处理响应body
                String html = response.body().string();
                response.body().close();
                response.close();
                return html;
            }
            response.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送get的http请求，返回byte[]
     * @param url
     * @return
     */
    public static byte[] getBytes(String url) {
        try {
            Request request = new Request.Builder().get().url(url).build();
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                byte[] bytes = response.body().bytes();
                response.body().close();
                response.close();
                return bytes;
            }
            response.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
