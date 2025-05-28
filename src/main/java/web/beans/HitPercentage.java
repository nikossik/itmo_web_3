package web.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.concurrent.atomic.AtomicInteger;

@Named("hitPercentage")
@ApplicationScoped
public class HitPercentage implements HitPercentageMBean {

    private final AtomicInteger totalClicks = new AtomicInteger();
    private final AtomicInteger hitClicks = new AtomicInteger();

    public void addPoint(boolean isHit) {
        totalClicks.incrementAndGet();
        if (isHit) {
            hitClicks.incrementAndGet();
        }
    }

    public void reset() {
        totalClicks.set(0);
        hitClicks.set(0);
    }

    @Override
    public int getTotalClicks() {
        return totalClicks.get();
    }

    @Override
    public int getHitClicks() {
        return hitClicks.get();
    }

    @Override
    public double getHitPercentage() {
        int total = totalClicks.get();
        return total == 0 ? 0.0 : (double) hitClicks.get() / total * 100.0;
    }
}