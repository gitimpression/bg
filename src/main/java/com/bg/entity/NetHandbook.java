package com.bg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 手册
 * @author ctp
 * @date 2022/12/4 11:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NetHandbook {
    private Long id;
    private String title;
    private String content;
    private Long userId;
}
