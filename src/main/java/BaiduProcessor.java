import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author code4crafter@gmail.com <br>
 */

public class BaiduProcessor implements PageProcessor {


    private Site site = Site

            .me()

//            .setDomain("blog.sina.com.cn")

            .setSleepTime(3000)

            .setUserAgent(

                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");


    @Override

    public void process(Page page) {


        Pattern p = Pattern.compile("<.*?\\>");
        Matcher m = p.matcher(page.getHtml().xpath("//*[@id='s-bottom-layer-right']/span[2]").toString());
        System.out.println(m.replaceAll(""));
        page.putField("text", m.replaceAll(""));

    }


    @Override

    public Site getSite() {

        return site;

    }


    public static void main(String[] args) {

        Spider.create(new BaiduProcessor()).addUrl("https://www.baidu.com/")

                .run();

    }

}
