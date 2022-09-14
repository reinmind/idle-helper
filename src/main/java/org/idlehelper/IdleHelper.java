package org.idlehelper;

import org.idlehelper.facade.ResourceService;
import org.idlehelper.facade.impl.ResourceServiceImpl;

public class IdleHelper {
    ResourceService resourceService;

    public IdleHelper(Double loadFactor) {
        this.resourceService = new ResourceServiceImpl(loadFactor);
    }

    public static void main(String[] args) throws InterruptedException {
        double loadFactor ;
        if(args != null && args[0] != null){
            loadFactor = Double.parseDouble(args[0]);
        }else{
            loadFactor = 0.6;
        }
        IdleHelper idleHelper = new IdleHelper(loadFactor);
        idleHelper.resourceService.idle();
    }
}
