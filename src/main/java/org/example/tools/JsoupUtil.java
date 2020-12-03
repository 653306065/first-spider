package org.example.tools;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {

    /**
     * 发送请求,把html转化为Document对象
     * @param url
     * @return
     */
    public static Document getDocument(String url) {
        String html = OKHttpUtils.get(url);
        if(StringUtils.isBlank(html)){
            return null;
        }
        return Jsoup.parse(html);
    }
}
