package org.idlehelper.facade;

import java.lang.management.OperatingSystemMXBean;

public interface ResourceService {
    OperatingSystemMXBean getOperatingSystemMXBean();

    double getSystemLoadAverage();

    int getAvailableProcessors();

    String getArch();

    void idle() throws InterruptedException;

    void stopIdle();
}
