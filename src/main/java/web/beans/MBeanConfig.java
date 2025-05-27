package web.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@ApplicationScoped
public class MBeanConfig {
    @Inject
    private PointCounter pointCounter;
    @Getter
    @Inject
    private HitPercentage hitPercentage;
    private ObjectName pointCounterName;
    private ObjectName hitPercentageName;

    public void registerMBeans() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            pointCounterName = new ObjectName("web.beans:type=PointCounter");
            hitPercentageName = new ObjectName("web.beans:type=HitPercentage");

            mbs.registerMBean(pointCounter, pointCounterName);
            mbs.registerMBean(hitPercentage, hitPercentageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterMBeans() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            if (pointCounterName != null) {
                mbs.unregisterMBean(pointCounterName);
            }
            if (hitPercentageName != null) {
                mbs.unregisterMBean(hitPercentageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}