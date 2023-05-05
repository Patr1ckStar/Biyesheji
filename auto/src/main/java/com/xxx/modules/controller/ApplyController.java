package com.xxx.modules.controller;


import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.Apply;
import com.xxx.modules.service.ApplyService;
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
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/personnel/apply")
public class ApplyController {
    
    @Autowired
    private ApplyService applyService;


    @GetMapping("/getApplyInfo")
    public Result getApplyInfo(Apply Apply, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize,
                                  HttpServletRequest request){
        try{
            PageInfo<Apply> data = applyService.selectApplyInfo(Apply,pageNum,pageSize);
            return ResultUtil.success(1,"成功",data);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @PostMapping("/saveAndUpdateApplyInfo")
    public Result saveAndUpdateApplyInfo(Apply Apply){
        try {
            Integer count = applyService.saveAndUpdateApplyInfo(Apply);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @DeleteMapping("/delApplyInfo")
    public Result delApplyInfo(Integer id){
        try {
            Integer count = applyService.delApplyInfo(id);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

}

