package com.cnh.tlrevenuerecognition.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PublicURLs {

    @Value("${app.public-urls}")
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }
}