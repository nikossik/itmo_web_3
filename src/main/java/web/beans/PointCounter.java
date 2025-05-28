package web.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import web.tables.Result;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Named("pointCounter")
@ApplicationScoped
public class PointCounter extends NotificationBroadcasterSupport implements PointCounterMBean, Serializable {

    private final AtomicInteger totalPointCount = new AtomicInteger();
    private final AtomicInteger pointsInAreaCount = new AtomicInteger();
    private long notificationSequence = 1;

    // Границы области
    private final double minX = -3.0, maxX = 3.0;
    private final double minY = -5.0, maxY = 5.0;

    public void addPoint(Result result) {
        totalPointCount.incrementAndGet();

        if (result.isHit()) {
            pointsInAreaCount.incrementAndGet();
        }

        double x = result.getX();
        double y = result.getY();

        if (x < minX || x > maxX || y < minY || y > maxY) {
            String message = "Координата вне границ: (" + x + ", " + y + ")";
            Notification notification = new Notification(
                "BoundaryViolation",
                "PointCounter",
                notificationSequence++,
                System.currentTimeMillis(),
                message
            );
            sendNotification(notification);
        }
    }

    public void reset() {
        totalPointCount.set(0);
        pointsInAreaCount.set(0);
        notificationSequence = 1;
    }

    @Override
    public int getTotalPointCount() {
        return totalPointCount.get();
    }

    @Override
    public int getPointsInAreaCount() {
        return pointsInAreaCount.get();
    }
}