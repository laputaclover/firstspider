import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSelenium {

    public static void main(String[] args) {
        System.getProperties().setProperty("webdriver.chrome.driver", "/Users/meijie/Library/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://sz.lianjia.com/chengjiao/c2411050683884/");
        WebElement webElement = webDriver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[1]/div[2]/div[2]/div[2]"));
        System.out.println(webElement.getText());
        webDriver.close();
//        System.out.println("结束");
    }
}
