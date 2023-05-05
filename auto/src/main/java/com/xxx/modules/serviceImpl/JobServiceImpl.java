package com.xxx.modules.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.Job;
import com.xxx.modules.mapper.JobMapper;
import com.xxx.modules.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XXX
 * @since 2021-03-31
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Autowired
    private JobMapper jobMapper;
    
    @Override
    public PageInfo<Job> selectJobInfo(Job job, Integer pageNum, Integer pageSize) {
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        PageHelper.startPage(pageNum,pageSize);
        if (job.getJobName()!=null){
            wrapper.like("job_name",job.getJobName());
        }
        List<Job> data = jobMapper.selectList(wrapper);
        return new PageInfo<>(data);
    }

    @Override
    public Integer saveAndUpdateJobInfo(Job job) {
        Integer count;
        if (job.getId() == null){
            count = jobMapper.insert(job);
        }else {
            count = jobMapper.updateById(job);
        }
        return count;
    }

    @Override
    public Integer delJobInfo(Integer id) {
        return jobMapper.deleteById(id);
    }
}
