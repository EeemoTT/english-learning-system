package com.studyEnglish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 * @since 2024-02-28
 */
@Getter
@Setter
  @ApiModel(value = "Question对象", description = "")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("题目名称")
      private String name;

      @ApiModelProperty("类型:选择，判断，问答")
      private Integer type;

      private String a;

      private String b;

      private String c;

      private String d;

      @ApiModelProperty("分数")
      private Integer score;


      @ApiModelProperty("答案")
      private String answer;

      @ApiModelProperty("解析")
      private String detail;


}
