package com.studyEnglish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-19
 */
@Data
  @ApiModel(value = "Course对象", description = "")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("课程封面")
      private String img;

      @ApiModelProperty("课程名称")
      private String name;

      @ApiModelProperty("课程介绍")
      private String introduction;

      @ApiModelProperty("课程类型")
      private String type;

      @ApiModelProperty("课程视频")
      private String video;

      @ApiModelProperty("课程资料")
      private String file;

      @ApiModelProperty("推荐")
      private String recommend;


}
