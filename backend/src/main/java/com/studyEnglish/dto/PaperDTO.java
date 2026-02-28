package com.studyEnglish.dto;

import lombok.Data;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/2/29
 **/

@Data
public class PaperDTO {
    private Integer type1;  //选择
    private Integer type2;  //判断
    private Integer type3;  //问答
    private Integer paperId;
}
