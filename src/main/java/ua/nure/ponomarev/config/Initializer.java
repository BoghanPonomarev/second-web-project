package ua.nure.ponomarev.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import ua.nure.ponomarev.aspect.DaoAspect;
import ua.nure.ponomarev.aspect.ServiceAspect;
import ua.nure.ponomarev.web.filter.EntityManagerLiveControllerFilter;
import ua.nure.ponomarev.web.filter.SecurityFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author Bogdan_Ponamarev.
 */
public class Initializer implements WebApplicationInitializer {

    // Указываем имя нашему Servlet Dispatcher для мапинга
    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        // Регистрируем в контексте конфигурационный класс, который мы создадим ниже
        ctx.register(WebAppConfig.class, DaoAspect.class, ServiceAspect.class);
        servletContext.addListener(new ContextLoaderListener(ctx));
        ctx.setServletContext(servletContext);
        filterRegistration(servletContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
                new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
    }

    private void filterRegistration(ServletContext servletContext) {
        FilterRegistration entityManagerLiveControllerFilter = servletContext
                .addFilter("entityManagerLiveControllerFilter"
                        , EntityManagerLiveControllerFilter.class);
        entityManagerLiveControllerFilter.addMappingForUrlPatterns(null, true, "/*");
        FilterRegistration securityFilter = servletContext
                .addFilter("securityFilter"
                        , SecurityFilter.class);
        securityFilter.addMappingForUrlPatterns(null, false, "/*");
    }

}
