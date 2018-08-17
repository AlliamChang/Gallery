package club.williamleon.util;

import club.williamleon.config.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author I343702 SAP
 */
public class StringUtil {

    private final static DateFormat format = new SimpleDateFormat(
        "yyyyMMddhhmmss");

    public static boolean isEmail(String str) {
        Pattern p = Pattern.compile(Constant.EMAIL_REGEX);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String formatDate(Date date) {
        return format.format(date);
    }

    public static Date parseDate(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
