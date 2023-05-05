package com.xxx.modules.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.modules.entity.User;
import com.xxx.modules.mapper.InviteMapper;
import com.xxx.modules.entity.Invite;
import com.xxx.modules.mapper.UserMapper;
import com.xxx.modules.service.InviteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import com.xxx.modules.utils.TimeUtil;

/**
 * invite
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-02-07
 */
@Service
public class InviteServiceImpl extends ServiceImpl<InviteMapper, Invite> implements InviteService {

    @Autowired
    private InviteMapper inviteMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Override
    public PageInfo<Invite> selectInviteInfo(Invite invite, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Invite> wrapper = new QueryWrapper<>();
        if (invite.getRealName() !=null){
            wrapper.like("real_name",invite.getRealName());
        }
        if (invite.getType() !=null){
            wrapper.eq("type",invite.getType());
        }
        List<Invite> data = inviteMapper.selectList(wrapper);
        return new PageInfo<>(data);
    }

    @Override
    public void saveInviteInfo(Invite invite) {
        if(invite.getImageUrl()==null){
            invite.setImageUrl("");
        }
        invite.setCreateTime(TimeUtil.getCurrentTime());
        if(invite.getType().toString().equals("2")){
            // 设置日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            int day=0;
            try {
                date=  sdf2.parse(invite.getInterviewTime());
                System.out.println(date);

                String today = sdf.format(date);
                // 转换为整数
                day = Integer.parseInt(today);
                User user1 = userService.selectUserInfoById(invite.getUserid());
                String gongzi = "";
                if(day<10){
                    gongzi = String.valueOf(Float.valueOf(user1.getSalaryFour())*0);
                }else if(day>10&&day<20){
                    gongzi = String.valueOf(Float.valueOf(user1.getSalaryFour())*0.33);

                }else{
                    gongzi = String.valueOf(Float.valueOf(user1.getSalaryFour())*0.66);

                }


                invite.setGongzi(gongzi);

            } catch (ParseException e) {
                System.err.println("日期格式无效: " + invite.getInterviewTime());
            }
            // 将Date对象格式化成字符串

                }

        inviteMapper.insert(invite);
        if(invite.getType().toString().equals("1")){
            //入职
            User user = new User();
            user.setPassword("123456");
            user.setUserName(invite.getPhone());
            user.setEmail(invite.getEmail());
            user.setPhone(invite.getPhone());
            user.setEducation(invite.getEducation());
            user.setSex(invite.getSex());
            user.setAge(invite.getAge());
            user.setRealName(invite.getRealName());
            user.setImageUrl(invite.getImageUrl());
            user.setUserType("2");
            user.setJobId(invite.getJobId());
            user.setDeptId(invite.getDeptId());
            userService.saveAndUpdateUserInfo(user);
        }else {
            //离职


            userService.delUserInfo(invite.getUserid());
        }
    }

    @Override
    public void updateInviteInfo(Invite invite) {
        inviteMapper.updateById(invite);
    }

    @Override
    public void delInviteInfo(Integer id) {
        inviteMapper.deleteById(id);
    }

    @Override
    public void delBatchInviteInfo(String idList) {
        List<String> list = Arrays.asList(idList.split(","));
        inviteMapper.deleteBatchIds(list);
    }


}