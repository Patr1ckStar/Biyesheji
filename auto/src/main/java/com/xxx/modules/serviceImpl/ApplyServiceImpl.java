package com.xxx.modules.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.Apply;
import com.xxx.modules.mapper.ApplyMapper;
import com.xxx.modules.service.ApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.modules.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-03-27
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public PageInfo<Apply> selectApplyInfo(Apply apply, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Apply> data = applyMapper.selectList2(apply);
        return new PageInfo<>(data);
    }

    @Value("${spring.mail.host}")
    private String springMailHost;
    @Value("${spring.mail.username}")
    private String springMailUserName;
    @Value("${spring.mail.password}")
    private String springMailPassword;


    @Override
    public Integer saveAndUpdateApplyInfo(Apply apply) throws UnsupportedEncodingException, MessagingException {
        int count;
        if (apply.getId() == null){
            count = applyMapper.insert(apply);
        }else {
            count = applyMapper.updateById(apply);
            if (apply.getType().equals("0")){
                if (apply.getStatus().equals("1")){
                    MailUtil.sendMail(springMailHost,springMailUserName,springMailPassword,springMailUserName,apply.getEmail(),"请假申请", apply.getRealName()+"您好,你的请假申请已经通过");
                }
            }
            if (apply.getType().equals("1")){
                if (apply.getStatus().equals("1")){
                    MailUtil.sendMail(springMailHost,springMailUserName,springMailPassword,springMailUserName,apply.getEmail(),"出差申请", apply.getRealName()+"您好,你的出差申请已经通过");
                }
            }
            if (apply.getType().equals("2")){
                if (apply.getStatus().equals("1")){
                    MailUtil.sendMail(springMailHost,springMailUserName,springMailPassword,springMailUserName,apply.getEmail(),"外出申请", apply.getRealName()+"您好,你的外出申请已经通过");
                }
            }
        }
        return count;
    }

    @Override
    public Integer delApplyInfo(Integer id) {
        return applyMapper.deleteById(id);
    }
}
