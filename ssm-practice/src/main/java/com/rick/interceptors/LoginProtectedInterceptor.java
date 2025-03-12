package com.rick.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick.utils.JwtHelper;
import com.rick.utils.Result;
import com.rick.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @version 1.0
 * @ClassName
 * @Description 登入攔截器, 檢查請求頭中是否包含有效token
 *                  有, 有效, 放行
 *                  沒有, 無效, 返回504
 * @Author rick
 * @Date
 */
@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //從請求頭中獲取token
        String token = request.getHeader("token");
        //檢查是否有效
        boolean expiration = jwtHelper.isExpiration(token);
        //有效放行
        if (!expiration) {
            //放行, 沒有過期
            return true;
        }
        //無效返回504的狀態json
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);

        return false;
    }
}
