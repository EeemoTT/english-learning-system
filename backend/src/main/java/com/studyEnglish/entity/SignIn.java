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
 * @since 2024-02-24
 */
@Data
  @ApiModel(value = "SignIn对象", description = "")
public class SignIn implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户ID")
      private Integer userId;

      @ApiModelProperty("签到时间")
      private String time;

      @ApiModelProperty("签到当天时间")
      private String day;


}
