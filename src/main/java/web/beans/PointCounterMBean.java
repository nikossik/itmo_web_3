package web.beans;

import java.util.List;

public interface PointCounterMBean {
    
    interface BoundaryViolationListener {
        void onBoundaryViolation(double x, double y, String message);
    }
    
    int getTotalPointCount();
    
    int getPointsInAreaCount();
    
    void setCoordinateBoundaries(double minX, double maxX, double minY, double maxY);

    List<String> getBoundaryViolations();
} 