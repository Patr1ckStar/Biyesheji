package com.xxx.modules.service;

import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XXX
 * @since 2021-03-31
 */
public interface JobService extends IService<Job> {

    PageInfo<Job> selectJobInfo(Job job, Integer pageNum, Integer pageSize);

    Integer saveAndUpdateJobInfo(Job job);

    Integer delJobInfo(Integer id);
}
