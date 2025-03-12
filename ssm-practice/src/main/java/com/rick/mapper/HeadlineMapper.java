package com.rick.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rick.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rick.pojo.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author pa001
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2025-03-10 11:54:32
* @Entity com.rick.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
    IPage<Map<String, Object>> selectMyPage(IPage<Map<String, Object>> page, @Param("portalVo") PortalVo portalVo);

    Map queryDetailMap(Integer hid);
}





