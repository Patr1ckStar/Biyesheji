package com.xxx.modules.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.*;
import com.xxx.modules.mapper.*;
import com.xxx.modules.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JobMapper jobMapper;

    @Override
    public User login(String userName, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName).eq("password",password);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Autowired
    private CalendarMapper calendarMapper;
    @Autowired
    private DeptMapper deptMapper;

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

    @Override
    public PageInfo<User> selectUserInfo(User user, Integer pageNum, Integer pageSize) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        PageHelper.startPage(pageNum,pageSize);
        if (user.getRealName() !=null){
            wrapper.like("real_name",user.getRealName());
        }
        if (user.getId() !=null){
            wrapper.eq("id",user.getId());
        }
        Calendar calendar = Calendar.getInstance();
        int currentDay = countWorkDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);

        int i = calendar.get(Calendar.MONTH) + 1;


        List<User> data = userMapper.selectList(wrapper);
        for (User user1:data){
            Job job = jobMapper.selectById(user1.getJobId());
            if (job !=null){
                user1.setJobName(job.getJobName());
            }
            QueryWrapper<CalendarInfo> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("user_id", user1.getId()).eq("month", i).eq("event_type","1");
            int signDay = calendarMapper.selectCount(wrapper2);
            QueryWrapper<CalendarInfo> wrapper3 = new QueryWrapper<>();
            wrapper3.eq("user_id",user1.getId()).eq("month",i).eq("event_type","2");

            Integer count2 = calendarMapper.selectCount(wrapper3);
            Integer count3;

            count3 = currentDay - signDay - count2;
            user1.setQingjiaDay(count2);
            user1.setQiandaoDay(signDay);
            user1.setQueqinDay(count3);

            //统计员工出勤状况
            int i1 = (currentDay  - signDay - count2) * (-20);
            int i2 = signDay * 10;
            int i3 = i1+i2;
            user1.setSalaryFive(i3);
            if(user1.getSalaryOne()!=null){

            int i9 = Integer.parseInt(user1.getSalaryOne())+Integer.parseInt(user1.getSalaryTwo())+Integer.parseInt(user1.getSalaryThree())+Integer.parseInt(user1.getSalaryFour())+i3+user1.getJixiao();
            user1.setYearBonus(i9);
            }

            Dept dept = deptMapper.selectById(user1.getDeptId());
            if (dept !=null){
                user1.setDeptName(dept.getDeptName());
            }
        }
        return new PageInfo<>(data);
    }

    @Override
    public Integer saveAndUpdateUserInfo(User user) {
        Integer count;
        if (user.getId() == null){
            Job job = jobMapper.selectById(user.getJobId());

            user.setSalaryOne(job.getSalaryOne());
            user.setSalaryTwo(job.getSalaryTwo());
            user.setSalaryThree(job.getSalaryThree());
            user.setSalaryFour(job.getSalaryFour());
            user.setSalaryFive(0);
            count = userMapper.insert(user);
        }else {
            count = userMapper.updateById(user);
        }
        return count;
    }

    @Override
    public Integer delUserInfo(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public User selectUserInfoById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void releaseBonus(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("job_id",user.getJobId());
        List<User> data = userMapper.selectList(wrapper);
        for (User user1:data){
            if (user1.getYearBonus() == null){
                user1.setYearBonus(0);
            }
            Integer count = user1.getYearBonus() + user.getYearBonus();
            user1.setYearBonus(count);
            userMapper.updateById(user1);
        }
    }

}
