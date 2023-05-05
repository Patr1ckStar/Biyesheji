package com.xxx.modules.mapper;

import com.xxx.modules.entity.Apply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XXX
 * @since 2022-03-27
 */
public interface ApplyMapper extends BaseMapper<Apply> {

    List<Apply> selectList2(Apply apply);

}
