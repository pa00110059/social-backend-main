package com.rick.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rick.pojo.Headline;
import com.rick.pojo.vo.PortalVo;
import com.rick.service.HeadlineService;
import com.rick.mapper.HeadlineMapper;
import com.rick.utils.JwtHelper;
import com.rick.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pa001
 * @description 针对表【news_headline】的数据库操作Service实现
 * @createDate 2025-03-10 11:54:32
 */
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
        implements HeadlineService {

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 1.進行分頁查詢
     * 2. 分頁數據, 拼接到result即可
     * <p>
     * 注意1: 查詢需要自定義語句  自定義mapper的方法, 攜帶分頁
     * 注意2: 返回的結果List<Map>
     *
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {

        // 創建 MyBatis Plus 的分頁物件
        IPage<Map<String, Object>> page = new Page<>(portalVo.getPageNum(), portalVo.getPageSize());

        // 呼叫 Mapper
        IPage<Map<String, Object>> resultPage = headlineMapper.selectMyPage(page, portalVo);

        // 封裝分頁結果
        Map<String, Object> data = new HashMap<>();
        data.put("pageData", resultPage.getRecords());
        data.put("pageNum", resultPage.getCurrent());
        data.put("pageSize", resultPage.getSize());
        data.put("totalPage", resultPage.getPages());
        data.put("totalSize", resultPage.getTotal());

        return Result.ok(data);
    }

    /**
     * 根據id查詢詳情
     * <p>
     * 2.查詢對應的數據即可 【多表, 頭條和用戶表, 方法需要自定義 返回map即可】
     * 1.修改閱讀量 + 1 【version樂觀鎖, 當前數據對應的版本】
     *
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map<String, Object> data = headlineMapper.queryDetailMap(hid);

        if (data == null || data.isEmpty()) {
            return Result.build(null, 404, "未找到文章內容");
        }

        Map<String, Object> headlineMap = new HashMap<>();
        headlineMap.put("headline", data);

        System.out.println("後端返回數據: " + headlineMap);

        // 獲取當前的 version 和 pageViews
        Integer currentVersion = (Integer) data.get("version");
        Integer currentPageViews = (Integer) data.get("pageViews");

        if (currentVersion != null && currentPageViews != null) {
            // 更新閱讀量（使用 version 檢查）
            Headline headline = new Headline();
            headline.setHid(hid);
            headline.setVersion(currentVersion);
            headline.setPageViews(currentPageViews + 1);

            int updatedRows = headlineMapper.updateById(headline);
            if (updatedRows == 0) {
                return Result.build(null, 500, "更新閱讀量失敗（可能是樂觀鎖機制導致）");
            }
        }

        return Result.ok(headlineMap);
    }


    /**
     * 發布頭條方法
     *
     *  1.補全數據
     * @param headline
     * @return
     */
    @Override
    public Result publish(Headline headline,String token) {

        //token查詢用戶id
        int userId = jwtHelper.getUserId(token).intValue();

        //數據裝配
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);


        return Result.ok(null);
    }

    /**
     * 修改頭條數據
     *
     *  1.hid查詢數據的最新version
     *  2.修改數據的修改時間為當前
     *
     * @param headline
     * @return
     */
    @Override
    public Result updateData(Headline headline) {

        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version); //樂觀鎖
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}




