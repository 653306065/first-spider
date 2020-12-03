package org.example;

import org.example.tools.JsoupUtil;
import org.example.tools.OKHttpUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class Spider {

    //topList的url
    public static String topUrl = "https://wallhaven.cc/toplist?page=";

    //开始页
    public static int startPage = 1;

    //结束页
    public static int endPage = 125;

    //文件保存地址
    public static String savePath = "C:\\wallhaven\\topList\\";

    /**
     * 获取top的壁纸列表
     */
    public void getTopList() {
        while (true) {
            //获取指定页的html结果
            Document document = JsoupUtil.getDocument(topUrl + startPage);
            //没有获取到html就跳过这次
            if(Objects.isNull(document)){
                continue;
            }
            //获取获取预览图列表，获取方法有点类型 js里面取html上的元素
            Elements elements = document.getElementsByClass("thumb-listing-page").get(0).getElementsByTag("ul").get(0).getElementsByTag("li");
            for (Element element : elements) {
                //获取预览图的id
                String id = element.getElementsByTag("figure").get(0).attr("data-wallpaper-id");
                //根据网页的结构，构造好对应的图片url
                String context = id.substring(0, 2);
                //png格式的url (png和jpg只会存在一个)
                String pngImageUrl = "https://w.wallhaven.cc/full/" + context + "/wallhaven-" + id + ".png";
                //jpg格式的url
                String jpgImageUrl = "https://w.wallhaven.cc/full/" + context + "/wallhaven-" + id + ".jpg";
                //获取png格式的图片
                byte[] pngByte = OKHttpUtils.getBytes(pngImageUrl);
                if (Objects.nonNull(pngByte)) {
                    //保存图片
                    byteToFile(pngByte, savePath + id + ".png");
                }
                //获取png格式的图片
                byte[] jpgByte = OKHttpUtils.getBytes(jpgImageUrl);
                if (Objects.nonNull(jpgByte)) {
                    //保存图片
                    byteToFile(jpgByte, savePath + id + ".jpg");
                }
                System.out.println(id + ",下载完成");
            }
            //开始页等于结束页就退出
            if (startPage == endPage) {
                break;
            }
            startPage++;
        }
    }

    /**
     * 把byte[]输出到指定文件
     * @param bytes
     * @param path
     */
    public static void byteToFile(byte[] bytes, String path) {
        File localFile = new File(path);
        try {
            //根据绝对路径初始化文件
            if (!localFile.exists()) {
                localFile.getParentFile().mkdirs();
                localFile.createNewFile();
            } else {
                System.out.println(path + ",已存在");
                return;
            }
            // 输出流
            OutputStream os = new FileOutputStream(localFile);
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            localFile.delete();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Spider spider = new Spider();
        spider.getTopList();
    }

}
