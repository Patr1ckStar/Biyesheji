package com.xxx.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XXX
 * @since 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Calendar对象", description="")
@TableName(value = "calendar")
public class CalendarInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String currentDay;

    private Integer userId;

    @ApiModelProperty(value = "1--请假  2--打卡成功  3--开题  4--中期  5--中期")
    private String eventType;

    private String remark;

    private Integer month;



}
