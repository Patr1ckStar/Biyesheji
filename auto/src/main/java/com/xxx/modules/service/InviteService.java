package com.xxx.modules.service;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.modules.entity.Invite;

/**
 * invite
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-02-07
 */
public interface InviteService extends IService<Invite>{

    PageInfo<Invite> selectInviteInfo(Invite invite, Integer pageNum, Integer pageSize);

    void saveInviteInfo(Invite invite);

    void updateInviteInfo(Invite invite);

    void delInviteInfo(Integer id);

    void delBatchInviteInfo(String idList);


}