package com.studyEnglish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
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
  @TableName("memory_record")
@ApiModel(value = "MemoryRecord对象", description = "")
public class MemoryRecord implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("单词id")
      private Integer wordId;

      @ApiModelProperty("用户id")
      private Integer userId;

      @ApiModelProperty("下次复习时间")
      private Date nextmemoryTime;

      @ApiModelProperty("阶段")
      private Integer phase;


}
