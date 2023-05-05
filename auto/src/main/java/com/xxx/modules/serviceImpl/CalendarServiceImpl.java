package com.xxx.modules.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.CalendarInfo;
import com.xxx.modules.entity.EchartData;
import com.xxx.modules.mapper.CalendarMapper;
import com.xxx.modules.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XXX
 * @since 2021-03-16
 */
@Service
public class CalendarServiceImpl extends ServiceImpl<CalendarMapper, CalendarInfo> implements CalendarService {

    @Autowired
    private CalendarMapper calendarMapper;

    @Override
    public PageInfo<CalendarInfo> selectCalendarInfo(CalendarInfo calendarInfo, Integer pageNum, Integer pageSize) {
        QueryWrapper<CalendarInfo> wrapper = new QueryWrapper<>();
        if (calendarInfo.getUserId() !=null){
            wrapper.eq("user_id", calendarInfo.getUserId());
        }
        PageHelper.startPage(pageNum,pageSize);
        List<CalendarInfo> data = calendarMapper.selectList(wrapper);
        return new PageInfo<>(data);
    }

    @Override
    public Integer saveAndUpdateCalendarInfo(CalendarInfo calendarInfo) {
        Integer count;
        if (calendarInfo.getId() == null){
            count = calendarMapper.insert(calendarInfo);
        }else {
            count = calendarMapper.updateById(calendarInfo);
        }
        return count;
    }

    @Override
    public Integer delCalendarInfo(Integer id) {
        return calendarMapper.deleteById(id);
    }

    @Override
    public CalendarInfo selectCalendarInfoByCalendarDate(CalendarInfo calendarInfo) {
        QueryWrapper<CalendarInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("current_day", calendarInfo.getCurrentDay());
        wrapper.eq("user_id", calendarInfo.getUserId());
        CalendarInfo calendarInfo2 = calendarMapper.selectOne(wrapper);
        return calendarInfo2;
    }

    @Override
    public List<EchartData> selectEchartDataForStudentCheck(Integer userId, Integer month) {
        List<EchartData> data = new ArrayList<>();
        QueryWrapper<CalendarInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("month",month).eq("event_type","1");
        Integer count = calendarMapper.selectCount(wrapper);
        EchartData echartData = new EchartData();
        echartData.setName("正常");
        echartData.setValue(count);
        data.add(echartData);

        QueryWrapper<CalendarInfo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("user_id",userId).eq("month",month).eq("event_type","2");
        Integer count2 = calendarMapper.selectCount(wrapper2);
        EchartData echartData2 = new EchartData();
        echartData2.setName("请假");
        echartData2.setValue(count2);
        data.add(echartData2);

        Calendar calendar = Calendar.getInstance();
        int currentDay = countWorkDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);

        Integer count3;
//        Integer currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        count3 = currentDay - count - count2;
        EchartData echartData3 = new EchartData();
        echartData3.setName("缺勤");
        echartData3.setValue(count3);
        data.add(echartData3);
        return data;
    }

    //计算当前月份工作日
    public static int countWorkDay(int year,int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month-1);
        int max = c.get(Calendar.DAY_OF_MONTH);
        int start =1;
        int count = 0;
        while (start < max) {
            c.set(Calendar.DAY_OF_MONTH,start);
            if (isWorkDay(c)) {
                count++;
            }
            start++;
        }
        return count;
    }

    public static boolean isWorkDay(Calendar c) {
        int week = c.get(Calendar.DAY_OF_WEEK);
        return week != Calendar.SUNDAY && week != Calendar.SATURDAY;
    }
}
