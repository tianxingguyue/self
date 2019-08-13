package com.job.api;


import com.job.core.interceptor.FeignSupportConfig;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "${jzs.feign-server.gateway:jzs-gateway-server}", configuration = FeignSupportConfig.class)
public interface AdminApi {





}
