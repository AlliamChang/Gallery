package club.williamleon;

import club.williamleon.util.MD5;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

        List<String> test = new ArrayList<>();
        test.add(null);
        LocalDateTime date = LocalDateTime.of(2017, 2, 13, 0, 0);
        date = date.plusMonths(1).minusDays(13);
        System.out.print("\n\nasda\r\nasdf\r\n\r\n".replaceAll("(^([\r\n])*)|(([\r\n])*$)", "1"));
    }

    private static DateFormat format = new SimpleDateFormat("yyyyMMdd");

    public static void todayMD5() {
        String user = "Mweprfre";
        String password = "98gheMjuo//*(ma";
        String token = "53770416-4e03-4c4e-89a6-c8dcb2301910";
        String todayDate = format.format(new Date());
        System.out.println(todayDate);
        String md5 = MD5.digest(user + password + todayDate);
        System.out.println(md5);
        System.out.println(md5.toUpperCase());
    }
}
