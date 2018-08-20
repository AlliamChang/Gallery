package club.williamleon;

import club.williamleon.config.Constant;
import club.williamleon.util.MD5;
import club.williamleon.util.val.GroupRole;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 53068 on 2018/4/24 0024.
 */
public class Test {

    public static void main(String[] args) {
//        Pattern p = Pattern.compile(Constant.EMAIL_REGEX);
//        Matcher m = p.matcher("a_bc@a.com");
//        System.out.println(m.matches());
//        Matcher m2 = p.matcher("abc@a.c");
//        System.out.println(m2.matches());
//        System.out.println(GroupRole.PASSER.ordinal());
//        List<String> test = Arrays.asList("1", "2", "3");
//        System.out.println(StringUtils.collectionToDelimitedString(test, ",", "'", "'"));
        Test.todayMD5();
    }

    private static DateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static void todayMD5() {
        String user = "Mweprfre";
        String password = "77gheMjuo//*(mb";
        String todayDate = format.format(new Date());
        System.out.println(todayDate);
        String md5 = MD5.digest(user + password + todayDate);
        System.out.println(md5);
        System.out.println(md5.toUpperCase());
    }
}
