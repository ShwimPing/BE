package com.shwimping.be.user.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "fcmApiClient", url = "https://fcm.googleapis.com/v1")
public interface FcmApiClient {

    @PostMapping(value = "/projects/{projectName}/messages:send", consumes = "application/json")
    String sendMessage(@PathVariable("projectName") String projectName,
                       @RequestHeader("Authorization") String authorizationToken,
                       @RequestBody String message);
}
