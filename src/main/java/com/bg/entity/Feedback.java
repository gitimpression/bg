package com.bg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ctp
 * @date 2022/12/4 13:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Feedback {
    private Long id;
    private String title;
    private String content;
    private Long usrId;
    private String time;
}
