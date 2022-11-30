package com.bg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 返回给普通用户的简单日志
 * @author ctp
 * @date 2022/11/30 16:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimpleUserLog {
    private String createTime;  // 日志记录创建时间
    private String operation;   // 操作 Log注解的value值
    private String ip;          // ip地址
}
