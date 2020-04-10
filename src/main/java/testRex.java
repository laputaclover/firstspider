import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testRex {
    public static void main(String[] args) {
        String str = "<span class=\"lh\">(京)-经营性-2017-0020</span>";
        Pattern p = Pattern.compile("<.*?\\>");
        Matcher m = p.matcher(str);
        System.out.println(m.replaceAll(""));
    }
}
