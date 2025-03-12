package com.rick.controller;

import com.rick.pojo.vo.PortalVo;
import com.rick.service.HeadlineService;
import com.rick.service.TypeService;
import com.rick.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @ClassName
 * @Description 首頁的控制層
 * @Author rick
 * @Date
 */


@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private HeadlineService headlineService;

    @GetMapping("findAllTypes")
    public Result findAllTypes() {
            Result result = typeService.findAllTypes();
            return result;
    }


    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewsPage(portalVo);
        return result;

    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result =  headlineService.showHeadlineDetail(hid);
        return result;

    }
}
