package org.idlehelper;

import org.idlehelper.facade.ResourceService;
import org.idlehelper.facade.impl.ResourceServiceImpl;

public class IdleHelper {
    ResourceService resourceService;

    public IdleHelper(Double loadFactor) {
        this.resourceService = new ResourceServiceImpl(loadFactor);
    }

    public static void main(String[] args) throws InterruptedException {
        double loadFactor = 0.6;
        long byteLength = 1024 * 1024 * 512;
        if(args != null && args.length > 0 ){
            if(args[0] != null) {
                loadFactor = Double.parseDouble(args[0]);
            }
            if(args.length > 1){
                byteLength = Long.parseLong(args[1]) * 1024 * 1024;
            }
        }
        IdleHelper idleHelper = new IdleHelper(loadFactor);
        idleHelper.resourceService.allocate(byteLength);
        idleHelper.resourceService.idle();
    }
}
