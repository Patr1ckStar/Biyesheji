package com.xxx.modules.service;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.modules.entity.Dept;

/**
 * dept
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-02-07
 */
public interface DeptService extends IService<Dept>{

    PageInfo<Dept> selectDeptInfo(Dept dept, Integer pageNum, Integer pageSize);

    void saveDeptInfo(Dept dept);

    void updateDeptInfo(Dept dept);

    void delDeptInfo(Integer id);

    void delBatchDeptInfo(String idList);


}