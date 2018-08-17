package club.williamleon.service.impl;

import club.williamleon.config.Constant;
import club.williamleon.config.SessionParam;
import club.williamleon.domain.PasswdEntity;
import club.williamleon.domain.UserEntity;
import club.williamleon.model.RegisterUser;
import club.williamleon.repo.PasswdRepo;
import club.williamleon.repo.UserRepo;
import club.williamleon.service.UserService;
import club.williamleon.util.CookiesUtil;
import club.williamleon.util.MD5;
import club.williamleon.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionParam sessionParam;

    @Autowired
    private PasswdRepo passwdRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseEntity<String> login(HttpServletRequest request) {
        ResponseEntity<String> responseEntity;
        if (StringUtils.hasLength(sessionParam.getToken())) {
            PasswdEntity user = new PasswdEntity(sessionParam.getUsername(),
                sessionParam.getToken());
            String token = CookiesUtil
                .getCookie(request.getCookies(), "token")
                .getValue();
            if (passwdRepo.existsByUsernameAndToken(sessionParam.getUsername(),
                sessionParam.getToken()) &&
                sessionParam.getToken().equals(token)) {
                responseEntity = new ResponseEntity<>(HttpStatus.OK);
            } else {
                if (request.getSession() != null) {
                    request.getSession().invalidate();
                }
                responseEntity = new ResponseEntity<>("Log again",
                    HttpStatus.UNAUTHORIZED);
            }
        } else {
            String account = request.getParameter(Constant.ACCOUNT);
            String passwd = request.getParameter(Constant.PASSWORD);
            if (account != null && passwd != null) {
                if (StringUtil.isEmail(account)) {
                    UserEntity user = userRepo.findByEmail(account);
                    if (user == null) {
                        responseEntity = new ResponseEntity<>(
                            "Email doesn't exist",
                            HttpStatus.UNAUTHORIZED);
                        return responseEntity;
                    }
                    account = user.getUsername();
                }
                PasswdEntity access = passwdRepo.findByUsername(account);
                if (access == null) {
                    responseEntity = new ResponseEntity<>(
                        "Please check your account",
                        HttpStatus.UNAUTHORIZED);
                } else if (!MD5.equal(passwd, access.getPassword())) {
                    responseEntity = new ResponseEntity<>(
                        "Please check your password",
                        HttpStatus.UNAUTHORIZED);
                } else {
                    String token = MD5.encrypt(new Date().toString());
                    access.setToken(token);
                    passwdRepo.save(access);
                    sessionParam.setUserId(userRepo.findIdByUsername(account));
                    sessionParam.setUsername(account);
                    sessionParam.setToken(token);
                    responseEntity = new ResponseEntity<>(token, HttpStatus.OK);
                    logger.info("{} successfully logged in.", account);
                }
            } else {
                responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        return responseEntity;
    }

    @Override
    public void logout(HttpServletRequest request) {
        try {
            String username = sessionParam.getUsername();
            if (request.getSession() != null) {
                request.getSession().invalidate();
            }
            logger.info("{} has successfully logged out.", username);
        } catch (Exception e) {
            logger.error("Logout failed. Reason: {}", e.getMessage());
        }
    }

    @Transactional
    @Override
    public void register(RegisterUser registerUser) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerUser.getUsername());
        PasswdEntity passwdEntity = new PasswdEntity(registerUser.getUsername(),
            registerUser.getPasswd());

        userRepo.save(userEntity);
        passwdRepo.save(passwdEntity);
    }

    @Override
    public void bindEmail() {

    }
}
