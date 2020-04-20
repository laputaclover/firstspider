import bean.CourtInfoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testRex {
    public static void main(String[] args) {
        String str = "2003年建成";
        Pattern numPattern = Pattern.compile("[1-9]\\d*");
        Matcher matcher = numPattern.matcher(str);
//        List<String> list = new ArrayList<>();
//        while (matcher.find()) {
//            list.add(matcher.group());
//        }
//        System.out.println(list);
        matcher.find();
        String res = matcher.group();
        System.out.println(res);

        CourtInfoBean courtInfoBean = new CourtInfoBean();
        courtInfoBean.setPorspecting30(172);
        courtInfoBean.setDomain("南山");
        courtInfoBean.setName("校区");
        courtInfoBean.setArea("西里");
        courtInfoBean.setPrice(10000);
        courtInfoBean.setOnSaleNum(20);
        courtInfoBean.setAge(2008);
        courtInfoBean.setDone90(5);
        courtInfoBean.setUid("12341234");
        System.out.println(courtInfoBean);

//        Map<String,String> listMap = new HashMap<>();

    }
}
