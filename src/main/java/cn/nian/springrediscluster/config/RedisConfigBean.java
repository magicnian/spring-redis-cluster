package cn.nian.springrediscluster.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class RedisConfigBean {
    private Integer maxRedirects;
    private List<String> nodes;
}
