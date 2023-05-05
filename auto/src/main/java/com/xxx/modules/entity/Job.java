package com.xxx.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Job对象", description="")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String jobName;

    private String salaryOne;

    private String salaryTwo;

    private String salaryThree;

    private String salaryFour;

    private String salaryFive;



}
