package com.studyEnglish.entity.wenxinyiyan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author eeemot_t
 * @Description TODO
 * @date 2024/3/8
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaiduChatMessage implements Serializable {
    private String role;
    private String content;
}
