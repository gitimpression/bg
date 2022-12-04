package com.bg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 网络服务 > 菜单
 * @author ctp
 * @date 2022/12/4 8:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NetMenu {
    private Long id;
    private String name;
    private String remark;
    private Long userId;
}
