package com.bg.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author ctp
 * @date 2022/11/22 3:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String headImg;
    private Integer roleId;
    private Integer userStatus;
    private String createTime;
    private String loginTime;
    private String email;
}
