package cn.nian.springrediscluster.controller;


import cn.nian.springrediscluster.utils.RedisUtils;
import cn.nian.springrediscluster.超时轮询算法.Task;
import cn.nian.springrediscluster.超时轮询算法.TaskSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private TaskSchedule taskSchedule;


    @RequestMapping("/cluster/test")
    @ResponseBody
    public String clusterTest(@RequestParam("key") String key) {
        System.out.println("key=" + key);
        redisUtils.hset(key, "one", "123");
        return "success";
    }

    @RequestMapping("/time/add")
    @ResponseBody
    public String addTask(@RequestBody Task task) {
        taskSchedule.addTask(task);
        return "success";
    }
}
