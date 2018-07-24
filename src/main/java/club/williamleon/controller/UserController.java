package club.williamleon.controller;

import club.williamleon.model.Result;
import club.williamleon.service.ImageService;
import club.williamleon.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Entity;
import java.util.stream.Collectors;

/**
 * Created by 53068 on 2018/4/15 0015.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private final ImageService imageService;

    @Autowired
    public UserController(ImageService imageService) {
        this.imageService = imageService;
    }

    @ResponseBody
    @PostMapping("/register")
    public Result register() {

        return null;
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login() {

        return null;
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result logout() {

        return null;
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
