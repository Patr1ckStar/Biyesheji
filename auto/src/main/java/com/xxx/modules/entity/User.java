package com.xxx.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author XXX
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    private String realName;

    private String userType;

    private String email;

    private String phone;

    private String address;


    private String salaryOne;


    private String salaryTwo;


    private String salaryThree;


    private String salaryFour;


    private Integer salaryFive;

    private Integer jixiao;

    private Integer jobId;

    private String sex;

    private String education;

    private Integer age;


    @TableField(exist = false)
    private String jobName;

    private Integer yearBonus;

    @TableField(exist = false)
    private String newMessage;

    private Integer deptId;

    @TableField(exist = false)
    private String deptName;



    @TableField(exist = false)
    private String createTime;

    private String imageUrl;

    @TableField(exist = false)
    private Integer qiandaoDay;

    @TableField(exist = false)
    private Integer qingjiaDay;

    @TableField(exist = false)
    private Integer queqinDay;
}
