package club.williamleon.controller;

import club.williamleon.model.Result;
import club.williamleon.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * Created by 53068 on 2018/5/16 0016.
 */
@Controller
@RequestMapping("/gallery")
public class GalleryController {

    private final ImageService imageService;

    @Autowired
    public GalleryController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/new")
    @ResponseBody
    public Result createGallery() {

        return null;
    }

    @GetMapping("/{id}/{name}")
    public Result invite() {

        return null;
    }

    @GetMapping("/{id}/accept")
    @ResponseBody
    public Result accept() {

        return null;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result getPhotos(@PathVariable Long id) {

        return new Result();
    }

    @GetMapping("/photo/{filename}")
    @ResponseBody
    public Result getPhotoDetail(@PathVariable String filename) {

        return new Result();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public Result uploadPhoto(@PathVariable Long id,
                              @RequestParam("file")MultipartFile file,
                              @RequestParam("title")String title) {
        imageService.store(file);

        return new Result();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Result deleteGallery(@PathVariable String id) {

        return new Result();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Result quitGallery() {

        return null;
    }

    @PostMapping("/comment/{filename}")
    @ResponseBody
    public Result comment() {

        return null;
    }

}
