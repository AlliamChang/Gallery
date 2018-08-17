package club.williamleon.util;

import javax.servlet.http.Cookie;

/**
 * @author I343702 SAP
 */
public class CookiesUtil {

    public static Cookie getCookie(Cookie[] cookies, String cookieName) {
        if (cookies == null || cookieName == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }
}
