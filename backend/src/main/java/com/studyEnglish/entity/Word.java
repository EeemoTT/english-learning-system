package com.studyEnglish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@Data
  @ApiModel(value = "Word对象", description = "")
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("单词")
      private String word;

      @ApiModelProperty("音标")
      private String ukphone;

      @ApiModelProperty("翻译")
      private String tran;

      @ApiModelProperty("词书")
      private String book;


}
