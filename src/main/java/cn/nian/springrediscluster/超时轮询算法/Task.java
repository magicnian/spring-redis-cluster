package cn.nian.springrediscluster.超时轮询算法;

import lombok.Data;

@Data
public class Task {

    private String taskId;
    private String content;
    private String target;
}
