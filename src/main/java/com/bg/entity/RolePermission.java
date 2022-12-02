package com.bg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ctp
 * @date 2022/12/3 2:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolePermission {
    private Long id;
    private Long roleId;
    private Long permissionId;
}
