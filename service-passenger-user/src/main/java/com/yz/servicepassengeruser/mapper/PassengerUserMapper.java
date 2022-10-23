package com.yz.servicepassengeruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.servicepassengeruser.dto.PassengerUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yangzhen
 * @Date 2022/10/23-0:16
 * @Description: com.yz.servicepassengeruser.mapper
 * @version: 1.0
 */
@Repository
public interface PassengerUserMapper extends BaseMapper<PassengerUser> {
}
