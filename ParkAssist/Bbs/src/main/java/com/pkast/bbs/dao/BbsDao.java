package com.pkast.bbs.dao;

import com.pkast.bbs.mapper.BbsMapper;
import com.pkast.bbs.module.BbsDbModel;
import com.pkast.db.DBNameUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BbsDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private BbsMapper mapper = null;

    private synchronized BbsMapper getMapper(){
        if(mapper == null){
            mapper = sqlSessionTemplate.getMapper(BbsMapper.class);
        }
        return mapper;
    }

    public List<BbsDbModel> getBbsItem(String dbName, String bbsType, int offset, int limit){
        DBNameUtil.setDbName(dbName);
        return getMapper().getPublishBbs(bbsType, offset, limit);
    }

    public void addBbs(String dbName, BbsDbModel newBbs){
        DBNameUtil.setDbName(dbName);
        getMapper().addPulishBbs(newBbs);
    }

    public void deleteBbs(String dbName, String bbsId){
        DBNameUtil.setDbName(dbName);
        getMapper().delPulishBbs(bbsId);
    }

}
