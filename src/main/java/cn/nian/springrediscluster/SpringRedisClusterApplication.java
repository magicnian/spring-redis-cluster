package cn.nian.springrediscluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringRedisClusterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisClusterApplication.class, args);
	}

}
