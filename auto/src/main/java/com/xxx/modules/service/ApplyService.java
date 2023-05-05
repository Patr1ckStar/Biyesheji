package com.xxx.modules.service;

import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.Apply;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XXX
 * @since 2022-03-27
 */
public interface ApplyService extends IService<Apply> {

    PageInfo<Apply> selectApplyInfo(Apply apply, Integer pageNum, Integer pageSize);

    Integer saveAndUpdateApplyInfo(Apply apply) throws UnsupportedEncodingException, MessagingException;

    Integer delApplyInfo(Integer id);
}
