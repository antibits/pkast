package com.pkast.user.dao;

import com.pkast.modules.UserInfo;
import com.pkast.user.mapper.UserMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    //@Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    private UserMapper getMapper(){
        return sqlSessionTemplate.getMapper(UserMapper.class);
    }

    public boolean insertUser(UserInfo user){
        if(getMapper().addUser(user) > 0){
            return true;
        }
        return false;
    }

    public boolean updateUser(UserInfo user){
        if(getMapper().update(user) > 0){
            return true;
        }
        return false;
    }

    public UserInfo getUserByCarNo(String carNo){
        return getMapper().getUserByCarNo(carNo);
    }
}
