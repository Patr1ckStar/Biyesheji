package com.xxx.modules.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * dept
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-02-07
 */
@Data
@TableName("dept")
public class Dept {

    /**
     * 主键id
     */
    
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	    /**
     * 部门名
     */
    	private String deptName;
	    /**
     * 创建时间
     */
    	private String createTime;
	    /**
     * 描述信息
     */
    	private String remark;

	@TableField(exist = false)
    	private List<User> userList;
	}