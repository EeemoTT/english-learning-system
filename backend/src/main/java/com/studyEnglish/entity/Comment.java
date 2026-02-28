package com.studyEnglish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
 * @since 2024-02-27
 */
@Getter
@Setter
  @ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("内容")
      private String content;

      @ApiModelProperty("评论人id")
      private Integer userId;

      @ApiModelProperty("评论时间")
      private LocalDateTime time;

      @ApiModelProperty("父id")
      private Integer pid;
      @TableField(exist = false)
      private String pNickname;
      @TableField(exist = false)
      private Integer pUserId;

      @ApiModelProperty("最上级评论id")
      private Integer oid;

      @ApiModelProperty("关联帖子id")
      private Integer communityId;

      @TableField(exist = false)
      private String nickname;
      @TableField(exist = false)
      private String avatar;

      @TableField(exist = false)
      private List<Comment> children;



}
