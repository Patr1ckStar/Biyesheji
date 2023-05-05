package com.xxx.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.CalendarInfo;
import com.xxx.modules.entity.EchartData;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XXX
 * @since 2021-03-16
 */
public interface CalendarService extends IService<CalendarInfo> {

    PageInfo<CalendarInfo> selectCalendarInfo(CalendarInfo calendarInfo, Integer pageNum, Integer pageSize);

    Integer saveAndUpdateCalendarInfo(CalendarInfo calendarInfo);

    Integer delCalendarInfo(Integer id);

    CalendarInfo selectCalendarInfoByCalendarDate(CalendarInfo calendarInfo);

    List<EchartData> selectEchartDataForStudentCheck(Integer userId, Integer month);
}
