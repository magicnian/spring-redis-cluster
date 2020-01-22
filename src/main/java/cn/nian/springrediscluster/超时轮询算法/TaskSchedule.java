package cn.nian.springrediscluster.超时轮询算法;


import cn.nian.springrediscluster.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TaskSchedule {


    private static Logger logger = LoggerFactory.getLogger(TaskSchedule.class);

    private static final String KEY = "task:schedule:%s";

    private volatile AtomicInteger count = new AtomicInteger(0);


    @Autowired
    private RedisUtils redisUtils;

    @Scheduled(cron = "0/1 * * * * ?")
    public void doTask() {
        int now = count.get() == 30 ? 0 : count.get() + 1;

        Map<Object, Object> taskMaps = redisUtils.hmget(String.format(KEY, now));
        if (taskMaps.size() == 0)
            logger.info("second:{} | no tasks", now);
        else
            taskMaps.forEach((k, v) -> {
                logger.info("task:{} | target:{} | start finish", k, ((Task) v).getTarget());
            });
        redisUtils.delete(String.format(KEY, now));

        if (count.get() == 30) {
            count.set(0);
        } else {
            count.incrementAndGet();
        }
    }

    public void addTask(Task task) {
        logger.info("addTask | second:{}", count.get());
        redisUtils.hset(String.format(KEY, count.get()), task.getTaskId(), task);
    }
}
