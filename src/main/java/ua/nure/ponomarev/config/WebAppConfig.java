package ua.nure.ponomarev.config;

import org.hibernate.SessionFactory;
import org.hibernate.service.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ua.nure.ponomarev.util.HibernateUtil;

/**
 * @author Bogdan_Ponamarev.
 */
@Configuration
@EnableWebMvc
@ComponentScan("ua.nure.ponomarev")
public class WebAppConfig implements WebMvcConfigurer {

    private HibernateUtil hibernateUtil=new HibernateUtil();

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

 /*    @Bean
    public AbstractPlatformTransactionManager transactionManager(SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
     }*/

     @Bean
    public SessionFactory sessionFactory(){
        return hibernateUtil.getSessionFactory();
     }

     @Bean
    public ConnectionProvider connectionProvider(){
        return new DatasourceConnectionProviderImpl();
     }

}