package web.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import web.tables.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Named("pointCounter")
@ApplicationScoped
public class PointCounter implements PointCounterMBean {
    
    private int totalPointCount = 0;
    private int pointsInAreaCount = 0;
    
    private double minX = -3.0;
    private double maxX = 3.0;
    private double minY = -5.0;
    private double maxY = 5.0;
    
    private final List<String> boundaryViolations = new ArrayList<>();
    
    private final List<BoundaryViolationListener> listeners = new CopyOnWriteArrayList<>();
    
    public void addPoint(Result result) {
        totalPointCount++;
        
        double x = result.getX();
        double y = result.getY();
        
        if (result.isHit()) {
            pointsInAreaCount++;
        }
        
        boolean isOutsideBoundaries = false;
        String message = null;
        
        if (x < minX) {
            isOutsideBoundaries = true;
            message = "X coordinate (" + x + ") is less than minimum boundary (" + minX + ")";
        } else if (x > maxX) {
            isOutsideBoundaries = true;
            message = "X coordinate (" + x + ") is greater than maximum boundary (" + maxX + ")";
        } else if (y < minY) {
            isOutsideBoundaries = true;
            message = "Y coordinate (" + y + ") is less than minimum boundary (" + minY + ")";
        } else if (y > maxY) {
            isOutsideBoundaries = true;
            message = "Y coordinate (" + y + ") is greater than maximum boundary (" + maxY + ")";
        }
        
        if (isOutsideBoundaries) {
            boundaryViolations.add(message);
            notifyListeners(x, y, message);
        }
    }
    
    private void notifyListeners(double x, double y, String message) {
        for (BoundaryViolationListener listener : listeners) {
            listener.onBoundaryViolation(x, y, message);
        }
    }
    
    @Override
    public int getTotalPointCount() {
        return totalPointCount;
    }
    
    @Override
    public int getPointsInAreaCount() {
        return pointsInAreaCount;
    }
    
    @Override
    public void setCoordinateBoundaries(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
    
    @Override
    public List<String> getBoundaryViolations() {
        return new ArrayList<>(boundaryViolations);
    }
    
    public void resetCounters() {
        totalPointCount = 0;
        pointsInAreaCount = 0;
        boundaryViolations.clear();
    }
} 