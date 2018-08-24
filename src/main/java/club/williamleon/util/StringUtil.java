package club.williamleon.util;

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

    private final static DateFormat formatExif = new SimpleDateFormat(
        "yyyy:MM:dd hh:mm:ss");

    private final static String EMAIL_REGEX = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";

    public static boolean isEmail(String str) {
        Pattern p = Pattern.compile(EMAIL_REGEX);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String formatDate(Date date) {
        return format.format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return format.parse(date);
    }

    public static Date parseExifDate(String date) throws ParseException {
        return formatExif.parse(date);
    }
}
