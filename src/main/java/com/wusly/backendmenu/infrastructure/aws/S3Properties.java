package com.wusly.backendmenu.infrastructure.aws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("aws")
public class S3Properties {
    private String accessKey;
    private String secretKey;
}
