package ua.nure.ponomarev.config;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ua.nure.ponomarev.aspect.DaoAspect;
import ua.nure.ponomarev.aspect.ServiceAspect;
import ua.nure.ponomarev.util.HibernateUtil;

import javax.persistence.EntityManagerFactory;

/**
 * @author Bogdan_Ponamarev.
 */
@Configuration
@EnableWebMvc
@Import({DaoAspect.class, ServiceAspect.class})
@EnableAspectJAutoProxy
@ComponentScan("ua.nure.ponomarev")
public class WebAppConfig implements WebMvcConfigurer {

    private HibernateUtil hibernateUtil = new HibernateUtil();

    // Позволяет видеть все ресурсы в папке pages, такие как картинки, стили и т.п.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/WEB-INF/**").addResourceLocations("/WEB-INF/");
    }

    // а этот бин инициализирует View нашего проекта
    // точно это же мы делали в mvc-dispatcher-servlet.xml
    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }


    @Bean
    public EntityManagerFactory sessionFactory() {
        return hibernateUtil.getSessionFactory();
    }

    @Bean
    public AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator(){
        return new AnnotationAwareAspectJAutoProxyCreator();
    }
}