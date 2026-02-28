package com.studyEnglish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2024-03-01
 */
@Getter
@Setter
  @TableName("test_paper")
@ApiModel(value = "TestPaper对象", description = "")
public class TestPaper implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
        private Integer id;

      @ApiModelProperty("考试ID")
      private Integer testId;

      @ApiModelProperty("试卷ID")
      private Integer paperId;


}
