package com.rick.service;

import com.rick.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rick.pojo.vo.PortalVo;
import com.rick.utils.Result;

/**
* @author pa001
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2025-03-10 11:54:32
*/
public interface HeadlineService extends IService<Headline> {

    /**
     * 首頁數據查詢
     * @param portalVo
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    /**
     * 根據id查詢詳情
     * @param hid
     * @return
     */
    Result showHeadlineDetail(Integer hid);


    /**
     * 發布頭條方法
     * @param headline
     * @return
     */
    Result publish(Headline headline,String token);

    /**
     * 修改頭條數據
     * @param headline
     * @return
     */
    Result updateData(Headline headline);
}
