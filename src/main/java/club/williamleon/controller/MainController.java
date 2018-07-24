package club.williamleon.controller;

import club.williamleon.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.stream.Collectors;

/**
 * Created by 53068 on 2018/4/9 0009.
 */
@Controller
@RequestMapping("/")
public class MainController {

    private final ImageService imageService;

    @Autowired
    public MainController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        view.addObject("files", imageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(MainController.class,
                        "serveFile", path.getFileName().toString()).build().toString()
        ).collect(Collectors.toList()));

        return view;
    }

    @GetMapping("/gallery")
    public ModelAndView gallery(@RequestParam("name")String name) {
        ModelAndView view = new ModelAndView();

        return view;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = imageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
