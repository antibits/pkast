package com.pkast.bbs.mapper;

import com.pkast.bbs.module.BbsDbModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BbsMapper {
    List<BbsDbModel> getPublishBbs(@Param("bbsType")String bbsType, @Param("offset")int offset, @Param("limit")int limit);

    void addPulishBbs(BbsDbModel bbsModel);

    void delPulishBbs(@Param("bbsId")String bbsId);
}
