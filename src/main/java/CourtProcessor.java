import bean.CourtInfoBean;
import com.alibaba.fastjson.JSON;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class CourtProcessor implements PageProcessor {

    private static final String URL_PAGE = "\\/sf1l2l3ba40ea10000bp300ep550\\/$";
    private static final String URL_PRICEINFO = "\\/pg\\d*sf1l2l3ba40ea10000bp300ep550\\/$";
    private static final String URL_COURT = "/xiaoqu/\\d*";
    private static final String URL_COURTSALES = "/chengjiao/c\\d*";

    private Set<String> courtSet = new HashSet<>();

    private Map<String, CourtInfoBean> courtInfoBeanMap = new HashMap<>();

    WebDriver webDriver = new ChromeDriver();

    static {
        System.getProperties().setProperty("webdriver.chrome.driver", "/Users/meijie/Library/chromedriver");
    }




    private Site site = Site
            .me()
//            .setDomain("blog.sina.com.cn")
            .setSleepTime(300)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");


    @Override
    public void process(Page page) {

        //初始化selenium


        //增加全部符合条件的列表
        if (page.getUrl().regex(URL_PAGE).match()) {
            System.out.println("获取目录页面");
            String s = page.getHtml().xpath("//*[@id=\"content\"]/div[1]/div[8]/div[2]/div/@page-data").toString();
            //获取最大页码
//            System.out.println(s);
            String totalPage = JSON.parseObject(s).get("totalPage").toString();
            int total = Integer.parseInt(totalPage);
            String basic = page.getUrl().toString();
            Pattern p = Pattern.compile("sf1l2l3ba40ea10000bp300ep550");
            Matcher m = p.matcher(basic);

            IntStream.range(0, total).forEach(i -> {
                String targetPage = m.replaceAll("pg" + (i + 1) + "sf1l2l3ba40ea10000bp300ep550");
//                System.out.println("增加需获取的列表"+targetPage);
                page.addTargetRequest(targetPage);
            });

            //文章页
        } else if(page.getUrl().regex(URL_PRICEINFO).match()){
            System.out.println("内容清单页面");
            List<String> allInfo = page.getHtml().xpath("//*[@id=\"content\"]/div[1]/ul/li").all();
            for (String infoLi: allInfo
                 ) {
                Html infoHtml = new Html(infoLi);
                String courtLink = infoHtml.xpath("//div[1]/div[2]/div/a[1]").links().toString();
//                System.out.println(courtName);
//                int length = courtLink.split("/").length;
//                String courtNum = courtLink.split("/")[length - 1];
                courtSet.add(courtLink);
                System.out.println("增加采集页面"+courtLink);
            }

//            System.out.println(courtSet);
            for (String courtLink:courtSet
                 ) {
                page.addTargetRequest(courtLink);
            }
        }else if (page.getUrl().regex(URL_COURT).match()){
            System.out.println("小区详情页面");

            Pattern numPattern = Pattern.compile("\\d*");

            CourtInfoBean courtInfoBean = new CourtInfoBean();
            //获取小区id
            String courtUid = page.getUrl().toString().split("/")[4];
            System.out.println(courtUid);
            courtInfoBean.setUid(courtUid);
            //获取小区名称
            String courtName = page.getHtml().xpath("//div[4]/div/div[1]/h1/text()").toString();
            courtInfoBean.setName(courtName);
            System.out.println(courtName);
            //获取小区年龄
            String ageStr = page.getHtml().xpath("//div[6]/div[2]/div[2]/div[1]/span[2]/text()").regex("\\d*").toString();
            courtInfoBean.setAge(Integer.parseInt(ageStr));
//            System.out.println(ageStr);
            //获取小区建筑数量
            String buildingNumStr = page.getHtml().xpath("//div[6]/div[2]/div[2]/div[6]/span[2]/text()").regex("\\d*").toString();
            courtInfoBean.setBuildingNum(Integer.parseInt(buildingNumStr));
            System.out.println(buildingNumStr);
            //获取小区价格
            int courtPrice = Integer.parseInt(page.getHtml().xpath("//div[6]/div[2]/div[1]/div/span[1]/text()").toString());
            courtInfoBean.setPrice(courtPrice);
//            System.out.println(courtPrice);
            courtInfoBeanMap.put(courtUid,courtInfoBean);
            System.out.println("增加小区: "+courtName);

            page.addTargetRequest(page.getHtml().xpath("//*[@id=\"frameDeal\"]/a").links().toString());

        }else if (page.getUrl().regex(URL_COURTSALES).match()){
            System.out.println("交易信息页面");

            Pattern numPattern = Pattern.compile("[1-9]\\d*");
            Matcher matcher;

            String courtUid = page.getUrl().regex("[1-9]\\d*").toString();
            System.out.println("小区编号："+courtUid);
            CourtInfoBean courtInfoBean = courtInfoBeanMap.get(courtUid);
            //获取地区信息
            WebDriver webDriver = new ChromeDriver();

            webDriver.get(page.getUrl().toString());
            String areaInfo = webDriver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[1]/div[1]/div/span")).getText();
            System.out.println("区域信息："+areaInfo);
            courtInfoBean.setDomain(areaInfo.split("区")[0]);
            courtInfoBean.setArea(areaInfo.split("区")[1]);

            //获取在售信息
            String onSaleNumStr = webDriver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[1]/div[2]/div[2]/div[2]")).getText();
            System.out.println("在售数量："+onSaleNumStr);
            matcher = numPattern.matcher(onSaleNumStr);
            matcher.find();
            courtInfoBean.setOnSaleNum(Integer.parseInt(matcher.group()));
            //获取成交信息
            String done90Str = webDriver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[1]/div[2]/div[3]/a")).getText();
            System.out.println("90天成交："+done90Str);
            matcher = numPattern.matcher(done90Str);
            matcher.find();
            courtInfoBean.setDone90(Integer.parseInt(matcher.group()));
            //获取看房信息
            String porspecting30Str = webDriver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[1]/div[2]/div[4]/div[2]")).getText();
            System.out.println("带看信息："+porspecting30Str);
            matcher = numPattern.matcher(porspecting30Str);
            matcher.find();
            courtInfoBean.setPorspecting30(Integer.parseInt(matcher.group()));

            webDriver.close();
        }
    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
        CourtProcessor courtProcessor = new CourtProcessor();

        Spider.create(courtProcessor).addUrl("https://sz.lianjia.com/ershoufang/nanshanqu/sf1l2l3ba40ea10000bp300ep550/")

                .run();

//        System.out.println(courtProcessor.courtInfoBeanMap);


        for (Map.Entry<String,CourtInfoBean> entry : courtProcessor.courtInfoBeanMap.entrySet()){
            System.out.println(entry.getValue());
        }




    }

}
