package pt.ipca.justintime.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

}

