package org.learningredis.chapter.five.highreads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/2
 */
public class MasterSlaveLoadTest {

    public static void main(String[] args) throws InterruptedException {
        MasterSlaveLoadTest test = new MasterSlaveLoadTest();
        test.setup();

        // make it sleep so that the master finishes writing the values
        // in the datastore otherwise reads will have either null values
        // Or old values
//        Thread.sleep(60000);

//        test.readFromMasterNode(); // USECASE-1
        test.readFromSlaveNodes(); // USECASE-2
    }

    private List<Thread> threadList = new ArrayList<>();

    private void setup() {
//        Thread pumpData = new Thread(new PumpData());
//        pumpData.start();
        // 防止数据未写完，放到主线程执行
        System.out.println(new Date());
        new PumpData().run();
        System.out.println(new Date());
    }

    private void readFromMasterNode() {
        long startTime = System.currentTimeMillis();
        for (int number = 1; number < 11; ++number) {
            Thread thread = new Thread(new FetchData(number, startTime, "localhost", 6379));
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
            thread.start();
        }
    }

    private void readFromSlaveNodes() {
        long startTime = System.currentTimeMillis();
        int number = 1;
        for (; number < 6; ++number) {
            Thread thread = new Thread(new FetchData(number, startTime, "localhost", 6381));
            threadList.add(thread);
        }
        for (; number < 11; ++number) {
            Thread thread = new Thread(new FetchData(number, startTime, "localhost", 6380));
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
            thread.start();
        }
    }
}
