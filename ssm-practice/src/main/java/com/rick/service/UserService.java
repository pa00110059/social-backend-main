package com.rick.service;

import com.rick.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rick.utils.Result;

/**
* @author pa001
* @description 针对表【news_user】的数据库操作Service
* @createDate 2025-03-10 11:54:32
*/
public interface UserService extends IService<User> {

    /**
     * 登入業務
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根據token獲取用戶數據
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 檢查帳號是否可用
     * @param username  帳號
     * @return
     */
    Result checkUsername(String username);

    /**
     * 註冊業務
     * @param user
     * @return
     */
    Result regist(User user);
}
