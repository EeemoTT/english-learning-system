package com.studyEnglish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-05
 */
@Getter
@Setter
  @TableName("date_record")
@ApiModel(value = "DateRecord对象", description = "")
public class DateRecord implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("学习当日")
      private Date date;

      @ApiModelProperty("学习单词数")
      private Integer learnCount;

      @ApiModelProperty("复习单词数")
      private Integer reviewCount;

      @ApiModelProperty("学习时间")
      private Integer learningTime;

      @ApiModelProperty("用户id")
      private Integer userId;


}
