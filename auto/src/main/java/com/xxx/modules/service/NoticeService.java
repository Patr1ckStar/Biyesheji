package com.xxx.modules.service;

import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XXX
 * @since 2021-03-31
 */
public interface NoticeService extends IService<Notice> {

    PageInfo<Notice> selectNoticeInfo(Notice notice, Integer pageNum, Integer pageSize);

    Integer saveAndUpdateNoticeInfo(Notice notice);

    Integer delNoticeInfo(Integer id);
}
