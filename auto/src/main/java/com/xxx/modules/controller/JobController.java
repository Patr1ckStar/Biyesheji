package com.xxx.modules.controller;


import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.Job;
import com.xxx.modules.service.JobService;
import com.xxx.modules.utils.Result;
import com.xxx.modules.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XXX
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/personnel/job")
public class JobController {
    
    @Autowired
    private JobService jobService;

    @GetMapping("/getJobInfo")
    public Result getJobInfo(Job job, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize,
                              HttpServletRequest request){
        try{
            PageInfo<Job> data = jobService.selectJobInfo(job,pageNum,pageSize);
            return ResultUtil.success(1,"成功",data);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @PostMapping("/saveAndUpdateJobInfo")
    public Result saveAndUpdateJobInfo(Job job){
        try {
            Integer count = jobService.saveAndUpdateJobInfo(job);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @DeleteMapping("/delJobInfo")
    public Result delJobInfo(Integer id){
        try {
            Integer count = jobService.delJobInfo(id);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

}

