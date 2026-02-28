package com.studyEnglish.common;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.studyEnglish.entity.wenxinyiyan.Token;
import lombok.extern.slf4j.Slf4j;


/**
 * @author eeemot_t
 * @Description TODO 获取API访问token
 * access_token默认有效期为30天，失效需要重新获取
 * @date 2024/3/8
 **/
@Slf4j
public class ApiConstant {
    //发起会话接口
    public static final String ERNIE_BOT_TURBO_INSTANT = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=";
    public static String getToken(String apiKey, String secretKey) {
        String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + apiKey + "&client_secret=" + secretKey;
        String s = HttpUtil.get(url);
        Token bean = JSONUtil.toBean(s, Token.class);
        return bean.getAccess_token();
    }

}

