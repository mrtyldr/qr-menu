package com.wusly.backendmenu.infrastructure.aws;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class S3Utils {

    public static GeneratePresignedUrlRequest getPublicUrlRequest(String url) {
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        return new GeneratePresignedUrlRequest(
                "menuprojesi",
                "restaurant/%s".formatted(url)
        )
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
    }
}
