package com.xxx.modules.service;

import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.modules.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XXX
 * @since 2021-03-16
 */
public interface UserService extends IService<User> {

    User login(String userName, String password);

    PageInfo<User> selectUserInfo(User user, Integer pageNum, Integer pageSize);

    Integer saveAndUpdateUserInfo(User user);

    Integer delUserInfo(Integer id);

    User selectUserInfoById(Integer id);

    void releaseBonus(User user);

}
