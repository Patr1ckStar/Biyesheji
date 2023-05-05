package com.xxx.modules.controller;


import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.CalendarInfo;
import com.xxx.modules.entity.EchartData;
import com.xxx.modules.service.CalendarService;
import com.xxx.modules.utils.Result;
import com.xxx.modules.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XXX
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/personnel/calendar")
public class CalendarController {
    
    @Autowired
    private CalendarService calendarService;

    @GetMapping("/getCalendarInfo")
    public Result getCalendarInfo(CalendarInfo calendarInfo, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize,
                                  HttpServletRequest request){
        try{
            StringBuilder imageUrl = new StringBuilder();
            imageUrl.append(request.getScheme()).append("://")
                    .append(request.getServerName()).append(":")
                    .append(request.getServerPort()).append("/image/");
            String baseUrl = imageUrl.toString();
            PageInfo<CalendarInfo> data = calendarService.selectCalendarInfo(calendarInfo,pageNum,pageSize);
            return ResultUtil.successForDataAndImage(1,"成功",data,baseUrl);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @PostMapping("/saveAndUpdateCalendarInfo")
    public Result saveAndUpdateCalendarInfo(CalendarInfo calendarInfo){
        try {
            CalendarInfo calendarInfo2 = calendarService.selectCalendarInfoByCalendarDate(calendarInfo);
            if (calendarInfo2 !=null){
                return ResultUtil.error(-2,"当前日期已签到,无需重复签到");
            }
            Integer count = calendarService.saveAndUpdateCalendarInfo(calendarInfo);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"发生异常,签到失败");
        }
    }

    @DeleteMapping("/delCalendarInfo")
    public Result delCalendarInfo(Integer id){
        try {
            Integer count = calendarService.delCalendarInfo(id);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @GetMapping("/getEchartDataForStudentCheck")
    public Result getEchartDataForStudentCheck(Integer userId,Integer month){
        try{
            List<EchartData> data = calendarService.selectEchartDataForStudentCheck(userId,month);
            return ResultUtil.success(1,"成功",data);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }

    }

}

