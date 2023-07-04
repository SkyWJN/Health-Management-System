package com.wujiajun.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wujiajun
 * @date 2023/5/11/ 12:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxRun {
    private String openid;

    private String time;

    private Integer step;
}
