package ua.nure.ponomarev.model.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ua.nure.ponomarev.model.aspect.DaoAspect;
import ua.nure.ponomarev.model.aspect.ServiceAspect;

/**
 * @author Bogdan_Ponamarev.
 *
 * Class that configures hibernate and web side of spring
 */
@Configuration
@EnableWebMvc
@Import({DaoAspect.class, ServiceAspect.class})
@EnableAspectJAutoProxy
@ComponentScan("ua.nure.ponomarev.web")
@EnableTransactionManagement
public class WebAppConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/WEB-INF/**").addResourceLocations("/WEB-INF/");
        logger.info("Resource handler was initialized");
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        logger.info("Internal view resolver was initialized");
        return resolver;
    }


    @Bean
    public SessionFactory sessionFactory() {
        logger.info("Start building session factory");
        LocalSessionFactoryBuilder builder =
                new LocalSessionFactoryBuilder(null);
        builder.scanPackages("ua.nure.pnomarev");
        return builder.buildSessionFactory(new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build());
    }

    @Bean
    public AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator() {
        return new AnnotationAwareAspectJAutoProxyCreator();
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}