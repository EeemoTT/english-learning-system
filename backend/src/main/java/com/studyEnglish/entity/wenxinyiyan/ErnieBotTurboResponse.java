package com.studyEnglish.entity.wenxinyiyan;

import lombok.Data;

import java.io.Serializable;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/3/8
 **/
@Data
public class ErnieBotTurboResponse implements Serializable {
    private String id;
    private String object;
    private Integer created;

    private String sentence_id;
    private Boolean is_end;
    private Boolean is_truncated;
    private String result;
    private Boolean need_clearHistory;

    private Usage usage;

    @Data
    public static class Usage implements Serializable {
        private Integer prompt_tokens;
        private Integer completion_tokens;
        private Integer total_tokens;

    }
}

