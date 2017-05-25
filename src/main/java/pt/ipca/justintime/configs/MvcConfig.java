package pt.ipca.justintime.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    /*  @Bean
      public ReloadableResourceBundleMessageSource messageSource(){
          ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
          messageSource.setBasename("classpath:messages");
          messageSource.setDefaultEncoding("UTF-8");
          return messageSource;
      }

      @Bean
      public CookieLocaleResolver localeResolver(){
          CookieLocaleResolver localeResolver = new CookieLocaleResolver();
          localeResolver.setDefaultLocale(Locale.ENGLISH);
          localeResolver.setCookieName("my-locale-cookie");
          localeResolver.setCookieMaxAge(3600);
          return localeResolver;
      }

      @Bean
      public LocaleChangeInterceptor localeInterceptor(){
          LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
          interceptor.setParamName("lang");
          return interceptor;
      }*/
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        //Employee
        registry.addViewController("/newemployee").setViewName("newemployee");
        registry.addViewController("/results").setViewName("results");
        registry.addViewController("/showallemployees").setViewName("showallemployees");
        registry.addViewController("/editemployee/edit").setViewName("editemployee/edit");
        registry.addViewController("/editemployee").setViewName("editemployee");
        registry.addViewController("/edit").setViewName("edit");
        //Project   
        registry.addViewController("/showproject").setViewName("showproject");
        registry.addViewController("/newproject").setViewName("newproject");
        registry.addViewController("/projectresult").setViewName("projectresult");
        registry.addViewController("/editproject/edit").setViewName("editproject/edit");
        registry.addViewController("/editproject").setViewName("editproject");
        //Team
        registry.addViewController("/newteam").setViewName("newteam");
        registry.addViewController("/teamresult").setViewName("teamresult");
        registry.addViewController("/editteam").setViewName("editteam");
        registry.addViewController("/deleteteam").setViewName("deleteteam");
    }
}


