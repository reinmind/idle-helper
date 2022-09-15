package org.idlehelper.facade.impl;

import org.idlehelper.facade.ResourceService;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ResourceServiceImpl implements ResourceService {

    OperatingSystemMXBean systemMXBean;

    ForkJoinPool forkJoinPool;

    AtomicBoolean idleFlag;

    Double maxLoadPercent;

    AtomicInteger counter;

    byte[][][] bytes;

    Integer MB_SIZE = 1024 * 1024;

    public ResourceServiceImpl(Double maxLoadPercent) {
        this.systemMXBean = ManagementFactory.getOperatingSystemMXBean();
        this.forkJoinPool = new ForkJoinPool(16);
        this.idleFlag = new AtomicBoolean(true);
        this.maxLoadPercent = maxLoadPercent;
        this.counter = new AtomicInteger(0);
    }

    @Override
    public OperatingSystemMXBean getOperatingSystemMXBean(){
        return this.systemMXBean;
    }

    @Override
    public double getSystemLoadAverage() {
        return systemMXBean.getSystemLoadAverage();
    }

    @Override
    public int getAvailableProcessors() {
        return systemMXBean.getAvailableProcessors();
    }

    @Override
    public String getArch() {
        return systemMXBean.getArch();
    }

    @Override
    public void idle() throws InterruptedException {
        FibonacciTask fibonacciTask = new FibonacciTask(30L);
        while(idleFlag.get()){
            double currentLoad = this.getSystemLoadAverage();
            if(currentLoad < maxLoadPercent * this.getAvailableProcessors()){
                ForkJoinTask<Long> task = forkJoinPool.submit(fibonacciTask);
                try {
                    Long aLong = task.get();
                    if(aLong > 0){
                        int i = counter.incrementAndGet();
                        if(i % 1000000 == 0){
                            System.out.println(counter);
                            counter.set(0);
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }else{
                System.out.println("Arch: " + this.getArch()
                        + " AvailableProcessors: "+ this.getAvailableProcessors()
                        +" Load: " + currentLoad);
                Thread.sleep(100L);
            }
        }
    }

    @Override
    public void stopIdle() {
        idleFlag.set(false);
        forkJoinPool.shutdown();
    }

    @Override
    public void allocate(long l3Size) {
        long l2size = l3Size / MB_SIZE ;
        bytes = new byte[(int)(l2size%Integer.MAX_VALUE)][1024][1024];
    }

}

class FibonacciTask extends RecursiveTask<Long> {

    Long workLoad;

    public FibonacciTask(Long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected Long compute() {
        if (workLoad == 1L || workLoad == 2L){
            return 1L;
        }
        FibonacciTask t1 = new FibonacciTask(workLoad - 1);
        FibonacciTask t2 = new FibonacciTask(workLoad - 2);
        try {
            return t1.fork().get() + t2.fork().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

