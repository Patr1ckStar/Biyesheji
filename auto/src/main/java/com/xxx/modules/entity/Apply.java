package com.xxx.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author XXX
 * @since 2022-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Apply对象", description="")
public class Apply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String reason;

    private Integer userId;

    private String type;

    private String startTime;

    private String endTime;

    @TableField(exist = false)
    private String realName;

    @TableField(exist = false)
    private String email;

    private String status;



}
