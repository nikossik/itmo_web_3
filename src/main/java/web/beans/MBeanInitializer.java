package web.beans;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MBeanInitializer implements ServletContextListener {

    @Inject
    private MBeanRegistry mBeanRegistry;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        mBeanRegistry.registerMBeans();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        mBeanRegistry.unregisterMBeans();
    }
}