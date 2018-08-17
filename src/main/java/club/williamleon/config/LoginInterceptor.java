package club.williamleon.config;

import club.williamleon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (userService.login(request).getStatusCode() == HttpStatus.OK) {
            return true;
        }else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
