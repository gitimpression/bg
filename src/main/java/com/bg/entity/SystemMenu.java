package com.bg.entity;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ctp
 * @date 2022/11/23 21:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SystemMenu implements Comparable<SystemMenu> , Serializable {
    private Long id;
    private String name;
    private String url;
    private String path;
    private String component;
    private Long parentId;
    private String iconClz;
    private String enabled;
    private String keepAlive;
    private Long permissionId;
    private List<SystemMenu> children = new ArrayList<>();// 封装数据返回给前端

    @Override
    public int compareTo(SystemMenu o) {
//        return Integer.parseInt(Long.toString(o.getId())) - Integer.parseInt(Long.toString(this.getId())); // 降序
        return Integer.parseInt(Long.toString(this.getId())) - Integer.parseInt(Long.toString(o.getId())); // 升序
    }
}
