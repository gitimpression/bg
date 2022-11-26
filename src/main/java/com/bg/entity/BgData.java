package com.bg.entity;

import lombok.*;

/**
 * 数据字典
 * @author ctp
 * @date 2022/11/27 1:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BgData {
    private Long id;
    private String key;
    private String value;
    private Integer valueId;
}
