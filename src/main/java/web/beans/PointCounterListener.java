package web.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("pointCounterListener")
@ApplicationScoped
public class PointCounterListener implements PointCounterMBean.BoundaryViolationListener {
    
    private static final Logger logger = Logger.getLogger(PointCounterListener.class.getName());
    
    @Override
    public void onBoundaryViolation(double x, double y, String message) {
        logger.log(Level.WARNING, "Boundary violation detected: Point ({0}, {1}) - {2}", 
                new Object[]{x, y, message});
    }
} 