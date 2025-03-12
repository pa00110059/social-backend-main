package com.rick.service;

import com.rick.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rick.utils.Result;

/**
* @author pa001
* @description 针对表【news_type】的数据库操作Service
* @createDate 2025-03-10 11:54:32
*/
public interface TypeService extends IService<Type> {

    /**
     * 查詢所有類別數據
     * @return
     */
    Result findAllTypes();
}
