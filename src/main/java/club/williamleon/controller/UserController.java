package club.williamleon.controller;

import club.williamleon.model.RegisterUser;
import club.williamleon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 53068 on 2018/4/15 0015.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(RegisterUser registerUser) {
//        RegisterUser registerUser = new RegisterUser();
//        registerUser.setPasswd(passwd);
//        registerUser.setUsername(username);
        userService.register(registerUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request) {
        return userService.login(request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request);
        try {
            response.sendRedirect(request.getContextPath() + "/index");
        } catch (IOException e) {
            logger.error("Logout failed when try to redirect to logout page. Reason: " + e.getMessage());
        }
    }



//    @RequestMapping(value = "/photo", method = RequestMethod.POST)
//    public ModelAndView uploadPhoto(@RequestParam("file")MultipartFile file,
//                                    HttpSession session) {
//        ModelAndView view = new ModelAndView("redirect:/");
////        String contentType = file.getContentType();
////        String fileName = file.getOriginalFilename();
////        String filePath = request.getSession().getServletContext().getRealPath("user/");
////        System.out.println(filePath);
////        try {
////            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        imageService.store(file);
//        return view;
//    }
}
