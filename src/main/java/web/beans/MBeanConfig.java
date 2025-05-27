package web.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@ApplicationScoped
public class MBeanConfig {
    @Inject
    private PointCounter pointCounter;
    @Inject
    private HitPercentage hitPercentage;
    private ObjectName pointsName;
    private ObjectName clickName;

    public void registerMBeans() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            pointsName = new ObjectName("web.beans:type=PointCounter");
            clickName = new ObjectName("web.beans:type=HitPercentage");

            mbs.registerMBean(pointCounter, pointsName);
            mbs.registerMBean(hitPercentage, clickName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterMBeans() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            if (pointsName != null) {
                mbs.unregisterMBean(pointsName);
            }
            if (clickName != null) {
                mbs.unregisterMBean(clickName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PointCounter getPointCounter() {
        return pointCounter;
    }

    public HitPercentage getHitPercentage() {
        return hitPercentage;
    }
}