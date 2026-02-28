package com.studyEnglish.dto;

import lombok.Data;

import java.util.List;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/2/29
 **/

@Data
public class PaperByHandDTO {
    private List<Integer> handleQuestionIds;
    private Integer paperId;
}
