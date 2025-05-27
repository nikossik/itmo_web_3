package web.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Named("hitPercentage")
@ApplicationScoped
public class HitPercentage implements HitPercentageMBean {
    
    private int totalClicks = 0;
    private int hitClicks = 0;
    
    private double cachedPercentage = 0.0;
    private boolean percentageCacheValid = false;
    
    @Override
    public int getTotalClicks() {
        return totalClicks;
    }
    
    @Override
    public int getHitClicks() {
        return hitClicks;
    }
    
    @Override
    public double getHitPercentage() {
        if (!percentageCacheValid) {
            updateHitPercentage();
        }
        
        return roundToTwoDecimalPlaces(cachedPercentage);
    }
    
    @Override
    public void updateHitPercentage() {
        
        if (totalClicks == 0) {
            cachedPercentage = 0.0;
            percentageCacheValid = true;
            return;
        }

        double hitRatio = (double) hitClicks / totalClicks;
        
        cachedPercentage = hitRatio * 100.0;
        
        percentageCacheValid = true;
    }
    
    @Override
    public void setTotalClicks(int totalClicks) {
        if (totalClicks < 0) {
            throw new IllegalArgumentException("Total clicks cannot be negative");
        }
        
        this.totalClicks = totalClicks;
        
        if (this.hitClicks > this.totalClicks) {
            this.hitClicks = this.totalClicks;
        }
        
        percentageCacheValid = false;
    }
    
    @Override
    public void setHitClicks(int hitClicks) {
        if (hitClicks < 0) {
            throw new IllegalArgumentException("Hit clicks cannot be negative");
        }
        
        if (hitClicks > totalClicks) {
            throw new IllegalArgumentException("Hit clicks cannot exceed total clicks");
        }
        
        this.hitClicks = hitClicks;
        
        percentageCacheValid = false;
    }
    
    @Override
    public void incrementTotalClicks() {
        totalClicks++;
        
        percentageCacheValid = false;
    }
    
    @Override
    public void incrementHitClicks() {
        if (hitClicks >= totalClicks) {
            throw new IllegalStateException("Cannot increment hit clicks beyond total clicks");
        }
        
        hitClicks++;
        
        percentageCacheValid = false;
    }
    
    private double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public void addPoint(boolean isHit) {
        totalClicks++;
        
        if (isHit) {
            hitClicks++;
        }
        
        percentageCacheValid = false;
    }
    
    public void reset() {
        totalClicks = 0;
        hitClicks = 0;
        cachedPercentage = 0.0;
        percentageCacheValid = true;
    }
} 