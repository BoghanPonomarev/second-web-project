package ua.nure.ponomarev.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Bogdan_Ponamarev.
 */
@Component
public class HibernateUtil {

    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    private static final SessionFactory sessionFactory;

    static {
        try {
            StandardServiceRegistry standardRegistry =
                    new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metaData =
                    new MetadataSources(standardRegistry).getMetadataBuilder().build();
            sessionFactory = metaData.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            logger.error("Error occurred: "+ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
