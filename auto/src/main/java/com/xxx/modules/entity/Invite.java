package com.xxx.modules.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * invite
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-02-07
 */
@Data
@TableName("invite")
public class Invite {

    /**
     * 主键id
     */
    
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	    /**
     * 姓名
     */
    	private String realName;
	    /**
     * 联系方式
     */
    	private String phone;
	    /**
     * 学历
     */
    	private String education;
	    /**
     * 照片
     */
    	private String imageUrl;
	    /**
     * 描述信息
     */
    	private String remark;
	    /**
     * 是否录用
     */
    	private String isHire;
	    /**
     * 创建时间
     */
    	private String createTime;
	    /**
     * 面试时间
     */
    	private String interviewTime;

    	private Integer type;

	private Integer age;
	private String sex;
	private String email;

	private Integer userid;

	@TableField(exist = false)
	private Integer jobId;
	@TableField(exist = false)
	private Integer deptId;

	private String gongzi;
}