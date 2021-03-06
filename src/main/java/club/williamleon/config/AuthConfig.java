package club.williamleon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public AuthConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/gallery/**");
    }

    @Override
    public void configureDefaultServletHandling(
        DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/browse").setViewName("gallery");
//        registry.addViewController("/upload").setViewName("upload");
        registry.addViewController("/category").setViewName("category");
        registry.addViewController("/personal").setViewName("personal_info");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/creator").setViewName("gallery_creator");
        registry.addViewController("/list").setViewName("gallery_list");
    }
}
