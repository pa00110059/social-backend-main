package com.rick.pojo.vo;

import lombok.Data;

/**
 * @version 1.0
 * @ClassName
 * @Description
 * @Author rick
 * @Date
 */
@Data
public class PortalVo {

    private String keyWords;
    private int type = 0;

    private int pageNum = 1;
    private int pageSize = 10;
}
