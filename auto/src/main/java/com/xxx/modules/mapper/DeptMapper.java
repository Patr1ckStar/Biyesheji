package com.xxx.modules.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.modules.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * dept
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-02-07
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

	
}