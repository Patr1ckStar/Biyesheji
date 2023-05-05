package com.xxx.modules.controller;


import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.Notice;
import com.xxx.modules.service.NoticeService;
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
@RequestMapping("/personnel/notice")
public class NoticeController {
    
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/getNoticeInfo")
    public Result getNoticeInfo(Notice notice, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize,
                             HttpServletRequest request){
        try{
            PageInfo<Notice> data = noticeService.selectNoticeInfo(notice,pageNum,pageSize);
            return ResultUtil.success(1,"成功",data);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @PostMapping("/saveAndUpdateNoticeInfo")
    public Result saveAndUpdateNoticeInfo(Notice notice){
        try {
            Integer count = noticeService.saveAndUpdateNoticeInfo(notice);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @DeleteMapping("/delNoticeInfo")
    public Result delNoticeInfo(Integer id){
        try {
            Integer count = noticeService.delNoticeInfo(id);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }


}

