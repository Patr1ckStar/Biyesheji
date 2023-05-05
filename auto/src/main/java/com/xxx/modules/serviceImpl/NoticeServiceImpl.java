package com.xxx.modules.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.Notice;
import com.xxx.modules.mapper.NoticeMapper;
import com.xxx.modules.service.NoticeService;
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
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    
    @Override
    public PageInfo<Notice> selectNoticeInfo(Notice notice, Integer pageNum, Integer pageSize) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        PageHelper.startPage(pageNum,pageSize);
        if (notice.getNotice()!=null){
            wrapper.like("notice",notice.getNotice());
        }
        List<Notice> data = noticeMapper.selectList(wrapper);
        return new PageInfo<>(data);
    }

    @Override
    public Integer saveAndUpdateNoticeInfo(Notice notice) {  
        Integer count;
        if (notice.getId() == null){
            count = noticeMapper.insert(notice);
        }else {
            count = noticeMapper.updateById(notice);
        }
        return count;
    }

    @Override
    public Integer delNoticeInfo(Integer id) {
        return noticeMapper.deleteById(id);
    }
}
