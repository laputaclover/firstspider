import bean.CourtInfoBean;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
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
        System.out.println("交易信息页面");

        Pattern numPattern = Pattern.compile("[1-9]\\d*");
        Matcher matcher;

        String courtUid = page.getUrl().regex("[1-9]\\d*").toString();
        System.out.println("小区编号："+courtUid);
//        CourtInfoBean courtInfoBean = courtInfoBeanMap.get(courtUid);
        //获取地区信息
        System.getProperties().setProperty("webdriver.chrome.driver", "/Users/meijie/Library/chromedriver");
        WebDriver webDriver = new ChromeDriver();

        webDriver.get(page.getUrl().toString());
        String areaInfo = webDriver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[1]/div[1]/div/span")).getText();
        System.out.println("区域信息："+areaInfo);
//        courtInfoBean.setDomain(areaInfo.split("区")[0]);
//        courtInfoBean.setArea(areaInfo.split("区")[1]);

        //获取在售信息
        String onSaleNumStr = webDriver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[1]/div[2]/div[2]/div[2]")).getText();
        System.out.println("在售数量："+onSaleNumStr);
        matcher = numPattern.matcher(onSaleNumStr);
        matcher.find();
        System.out.println(matcher.group());
//        courtInfoBean.setOnSaleNum(Integer.parseInt(matcher.group()));
        //获取成交信息
        String done90Str = webDriver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[1]/div[2]/div[3]/a")).getText();
        System.out.println("90天成交："+done90Str);
        matcher = numPattern.matcher(done90Str);
        matcher.find();
        System.out.println(matcher.group());
//        courtInfoBean.setDone90(Integer.parseInt(matcher.group()));
        //获取看房信息
        String porspecting30Str = webDriver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[1]/div[2]/div[4]/div[2]")).getText();
        System.out.println("带看信息："+porspecting30Str);
        matcher = numPattern.matcher(porspecting30Str);
        matcher.find();
        System.out.println(matcher.group());
//        courtInfoBean.setPorspecting30(Integer.parseInt(matcher.group()));

        webDriver.close();

    }


    @Override

    public Site getSite() {

        return site;

    }


    public static void main(String[] args) {

        Spider.create(new BaiduProcessor()).thread(5)
//                .setDownloader(new SeleniumDownloader("/Users/meijie/Library/chromedriver"))
                .addUrl("https://sz.lianjia.com/chengjiao/c2411050683884/")

                .run();

    }


}
