package com.bg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 公告
 * @author ctp
 * @date 2022/11/30 19:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice {
    private Long id;
    private Long publisherId;
    private String title;
    private String content;
    private String createTime;
    private Long visits;
    private String publisherName;  // 考虑到性能问题，把username也一同放进去，如果只存用户id，每次查看都要联合查询
}
