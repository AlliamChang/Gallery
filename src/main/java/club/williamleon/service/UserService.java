package club.williamleon.service;

import club.williamleon.model.RegisterUser;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 53068 on 2018/4/26 0026.
 */
public interface UserService {

    ResponseEntity login(HttpServletRequest request);

    ResponseEntity<String> register(RegisterUser registerUser);

    void logout(HttpServletRequest request);

    void bindEmail();


}
