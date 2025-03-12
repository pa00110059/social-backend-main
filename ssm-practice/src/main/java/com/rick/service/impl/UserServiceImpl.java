package com.rick.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rick.pojo.User;
import com.rick.service.UserService;
import com.rick.mapper.UserMapper;
import com.rick.utils.JwtHelper;
import com.rick.utils.MD5Util;
import com.rick.utils.Result;
import com.rick.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pa001
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2025-03-10 11:54:32
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 登入業務
     * <p>
     * 1.根據帳號查詢用戶對象    - loginUser
     * 2.如果用戶對象為null, 查詢失敗, 帳號錯誤! 501
     * 3.對比, 密碼 , 失敗   返回503的錯誤
     * 4.根據用戶id生成一個token, token -> result 返回
     *
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {

        //根據帳號查詢數據
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);

        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //對比密碼
        if (!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())) {
            //登入成功

            //根據用戶id生成 token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            //將token封裝到result返回
            Map data = new HashMap();
            data.put("token", token);


            return Result.ok(data);
        }
        //密碼錯誤

        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     *  1.檢查token是否在有效期限
     *  2.根據token解析用戶userId
     *  3.根據用戶id查詢用戶數據
     *  4.去掉密碼, 封裝result結果返回
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        //是否過期? true過期
        boolean expiration = jwtHelper.isExpiration(token);

        if(expiration) {
            //失效, 未登入看待
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        int userId = jwtHelper.getUserId(token).intValue();

        User user = userMapper.selectById(userId);
        user.setUserPwd("");

        Map data = new HashMap();
        data.put("loginUser", user);

        return Result.ok(data);
    }

    /**
     *      根據帳號是否可用
     *          1.根據帳號進行count查詢
     *          2.count == 0 可用
     *          3.count > 0 不可用
     * @param username  帳號
     * @return
     */
    @Override
    public Result checkUsername(String username) {

        LambdaQueryWrapper<User> queryWrapper
                = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        Long count = userMapper.selectCount(queryWrapper);

        if (count == 0) {
            return Result.ok(null);
        }

        return Result.build(null, ResultCodeEnum.USERNAME_USED);
    }

    /**
     * 註冊業務
     *  1.檢查帳號是否已經被註冊
     *  2.密碼加密處理
     *  3.帳號數據保存
     *  4.返回結果
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper
                = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);

        if (count > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));

        userMapper.insert(user);

        return Result.ok(null);
    }
}




