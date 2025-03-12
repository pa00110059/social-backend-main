package com.rick.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName news_type
 */

@Data
public class Type implements Serializable {
    @TableId
    private Integer tid;

    private String tname;

    @Version
    private Integer version;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}