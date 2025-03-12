package com.rick.controller;

import com.rick.pojo.User;
import com.rick.service.UserService;
import com.rick.utils.JwtHelper;
import com.rick.utils.Result;
import com.rick.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @ClassName
 * @Description
 * @Author rick
 * @Date
 */

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody User user) {

        Result result = userService.login(user);

        return result;
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token) {
        Result result = userService.getUserInfo(token);
        return result;

    }


    @PostMapping("checkUserName")
    public Result checkUserName(String username) {
        Result result = userService.checkUsername(username);
        return result;
    }

    @PostMapping("regist")
    public Result result(@RequestBody User user) {
        Result result = userService.regist(user);
        return result;
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token) {
        boolean expiration = jwtHelper.isExpiration(token);

        if(expiration) {
            //已經過期了
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        return Result.ok(null);

    }
}
