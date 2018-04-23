package ua.nure.ponomarev.model.config;

import lombok.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ua.nure.ponomarev.model.aspect.DaoAspect;
import ua.nure.ponomarev.model.aspect.ServiceAspect;
import ua.nure.ponomarev.web.filter.SecurityFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author Bogdan_Ponamarev.
 *
 * Class that connects spring and web context
 */
public class Initializer implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

    /**
     * Method that initializes spring context,web dispatcher servlet and scans packages
     * @param servletContext - web servlet context
     * @throws ServletException - if some problem with servlets was occurred
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(WebAppConfig.class);
        servletContext.addListener(new ContextLoaderListener(springContext));
        springContext.setServletContext(servletContext);
        filterRegistration(servletContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
                new DispatcherServlet(springContext));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
        springContext.scan("ua.nure.ponomarev.model");
        springContext.refresh();
        springContext.register(DaoAspect.class,ServiceAspect.class);
    }

    /**
     * Method that initializes and configures all web filters
     * @param servletContext - web servlet context
     */
    private void filterRegistration(ServletContext servletContext) {
        FilterRegistration securityFilter = servletContext
                .addFilter("securityFilter"
                        , SecurityFilter.class);
        securityFilter.addMappingForUrlPatterns(null, false, "/*");
    }

}
