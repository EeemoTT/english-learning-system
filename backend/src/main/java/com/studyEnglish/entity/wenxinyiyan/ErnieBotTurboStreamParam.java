package com.studyEnglish.entity.wenxinyiyan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/3/8
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErnieBotTurboStreamParam implements Serializable {

    private List<BaiduChatMessage> messages;
    private Boolean stream;
    private String user_id;

    public boolean isStream() {
        return Objects.equals(this.stream, true);
    }
}

