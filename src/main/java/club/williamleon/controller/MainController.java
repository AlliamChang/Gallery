package club.williamleon.controller;

import club.williamleon.config.SessionParam;
import club.williamleon.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Created by 53068 on 2018/4/9 0009.
 */
@RestController
@RequestMapping("/")
public class MainController {

    private final ImageService imageService;

    @Autowired
    private SessionParam sessionParam;

    @Autowired
    public MainController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView view ;
        if (sessionParam.getUserId() == null) {
            view = new ModelAndView("index");
        }else {
            view = new ModelAndView("gallery");
        }
//        view.addObject("files", imageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(MainController.class,
//                        "serveFile", path.getFileName().toString()).build().toString()
//        ).collect(Collectors.toList()));

        return view;
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = imageService.loadAsResource(filename);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }else {
            try {
                return ResponseEntity.ok().lastModified(file.lastModified()).header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
    }
}
