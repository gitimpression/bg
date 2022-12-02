package com.bg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 角色表
 * @author ctp
 * @date 2022/12/3 2:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    private Long id;
    private String name;
}
