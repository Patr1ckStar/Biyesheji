package com.xxx.modules.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.modules.entity.User;
import com.xxx.modules.mapper.DeptMapper;
import com.xxx.modules.entity.Dept;
import com.xxx.modules.mapper.UserMapper;
import com.xxx.modules.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Arrays;
import com.xxx.modules.utils.TimeUtil;

/**
 * dept
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-02-07
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<Dept> selectDeptInfo(Dept dept, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        if (dept.getDeptName() !=null){
            wrapper.like("dept_name",dept.getDeptName());
        }
        List<Dept> data = deptMapper.selectList(wrapper);
        for(Dept d: data){
            List<User> userList = userMapper.selectByDeptId(d.getId());
            d.setUserList(userList);
        }
        return new PageInfo<>(data);
    }

    @Override
    public void saveDeptInfo(Dept dept) {
        dept.setCreateTime(TimeUtil.getCurrentTime());
        deptMapper.insert(dept);
    }

    @Override
    public void updateDeptInfo(Dept dept) {
        deptMapper.updateById(dept);
    }

    @Override
    public void delDeptInfo(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void delBatchDeptInfo(String idList) {
        List<String> list = Arrays.asList(idList.split(","));
        deptMapper.deleteBatchIds(list);
    }


}