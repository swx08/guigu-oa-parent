package com.guigu.wechat.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * ClassName:WechatAccountConfig
 * Package:com.guigu.wechat
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/24 - 10:55
 * @Version:v1.0
 */

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    //读取配置文件中的这两值
    private String mpAppId;

    private String mpAppSecret;
}
