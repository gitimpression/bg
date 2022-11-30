package com.bg.entity;

import lombok.*;

/**
 * 用户日志类
 * @author ctp
 * @date 2022/11/29 23:29
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLog extends SimpleUserLog{
    private Long id;
    private Long userId;        // 操作者id
    private String method;      // 调用的方法
    private String params;      // 方法的参数
    private Long consumeTime;   // 消耗时间
}
