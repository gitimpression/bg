package com.bg.entity;

import lombok.*;

/**
 * 权限表
 * @author ctp
 * @date 2022/11/27 22:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Permission {
    private Long id;
    private String name;
    private String remark;
}
