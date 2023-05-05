package com.xxx.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.modules.entity.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XXX
 * @since 2021-03-16
 */
public interface UserMapper extends BaseMapper<User> {


    List<User> selectByDeptId(Integer id);
}
