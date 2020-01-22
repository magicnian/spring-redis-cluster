package cn.nian.springrediscluster.controller;


import cn.nian.springrediscluster.utils.RedisUtils;
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


    @RequestMapping("/cluster/test")
    @ResponseBody
    public String clusterTest(@RequestParam("key") String key) {
        System.out.println("key=" + key);
        redisUtils.hset(key, "one", "123");
        return "success";
    }
}
