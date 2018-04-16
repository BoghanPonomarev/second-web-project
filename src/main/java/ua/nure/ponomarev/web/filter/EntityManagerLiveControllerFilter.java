package ua.nure.ponomarev.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.nure.ponomarev.holder.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
@Component
public class EntityManagerLiveControllerFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerLiveControllerFilter.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManagerHolder entityManagerHolder;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Filter was initiated , class: " + EntityManagerLiveControllerFilter.class);
        //Need for Autowiring in filter
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(isResource(request)){
            chain.doFilter(request,response);
            return;
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManagerHolder.putEntityManager(entityManager);
        chain.doFilter(request,response);
        entityManager.close();
    }

    @Override
    public void destroy() {
        logger.info("Filter was destroyed , class: " + EntityManagerLiveControllerFilter.class);
    }

    private boolean isResource(ServletRequest request){
        String url = (( HttpServletRequest)request).getRequestURI();
        return url.contains(".html")||url.contains(".img")||
                url.contains(".jsp")||url.contains(".css")||url.contains(".js");
    }
}
