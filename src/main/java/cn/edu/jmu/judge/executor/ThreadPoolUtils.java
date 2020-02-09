package cn.edu.jmu.judge.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xeathen
 * @date 2019/10/01
 **/
public class ThreadPoolUtils {

    private static volatile ExecutorService executor;

    private ThreadPoolUtils() {

    }

    public static ExecutorService getInstance() {
        if (executor == null) {
            synchronized (ThreadPoolUtils.class) {
                if (executor == null) {
                    executor = Executors.newFixedThreadPool(10);
                }
            }
        }
        return executor;
    }
}
