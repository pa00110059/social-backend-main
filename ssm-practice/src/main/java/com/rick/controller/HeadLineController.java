package com.rick.controller;

import com.rick.pojo.Headline;
import com.rick.service.HeadlineService;
import com.rick.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @ClassName
 * @Description
 * @Author rick
 * @Date
 */
@RestController
@RequestMapping("headline")
@CrossOrigin
public class HeadLineController {
    @Autowired
    private HeadlineService headlineService;

    //登入以後才可以訪問
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline,@RequestHeader String token) {
        Result result = headlineService.publish(headline,token);
        return result;

    }

    @PostMapping("findHeadlineByHid")
    public Result findHeadLineByHid(Integer hid) {
        Headline headline = headlineService.getById(hid);
        Map data = new HashMap();
        data.put("headline",headline);
        return Result.ok(data);
    }


    @PostMapping("update")
    public Result update(@RequestBody Headline headline) {
        Result result = headlineService.updateData(headline);
        return result;
    }

    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid) {
        headlineService.removeById(hid);
        return Result.ok(null);
    }
}
