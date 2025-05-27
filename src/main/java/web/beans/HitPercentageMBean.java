package web.beans;

public interface HitPercentageMBean {
    
    double getHitPercentage();
    
    void updateHitPercentage();
    
    int getTotalClicks();
    
    void setTotalClicks(int totalClicks);
    
    int getHitClicks();
    
    void setHitClicks(int hitClicks);
    
    void incrementTotalClicks();
    
    void incrementHitClicks();
} 