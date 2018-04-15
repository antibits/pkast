package com.pkast.user.dao;

import com.pkast.modules.UserInfo;
import com.pkast.user.mapper.UserMapper;
import com.pkast.utils.CollectionUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    private UserMapper getMapper(){
        return sqlSessionTemplate.getMapper(UserMapper.class);
    }

    public int insertUser(UserInfo user){
        return getMapper().addUser(user);
    }

    public int updateUser(UserInfo user){
        return getMapper().update(user);
    }

    public UserInfo getUserByCarNo(String carNo){
        List<UserInfo> userInfos = getMapper().getUserByCarNo(carNo);
        return CollectionUtil.isEmpty(userInfos)? null : userInfos.get(0);
    }

    public UserInfo getUserByWxNo(String userWxNo) {
        List<UserInfo> userInfos = getMapper().getUserByWxNo(userWxNo);
        return CollectionUtil.isEmpty(userInfos)? null : userInfos.get(0);
    }
}
