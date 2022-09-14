package org.idlehelper;

import org.idlehelper.facade.ResourceService;
import org.idlehelper.facade.impl.ResourceServiceImpl;
import org.junit.Test;

import java.io.IOException;

public class LoadTest {
    ResourceService resourceService;

    public LoadTest() {
        this.resourceService = new ResourceServiceImpl(0.6);
    }

    @Test
    public void printLoad() throws InterruptedException {
        while(true){
            System.out.println("Arch: " + resourceService.getArch()
                    + " AvailableProcessors: "+ resourceService.getAvailableProcessors()
                    +" Load: " + resourceService.getSystemLoadAverage());
            Thread.sleep(500L);
        }
    }
}
