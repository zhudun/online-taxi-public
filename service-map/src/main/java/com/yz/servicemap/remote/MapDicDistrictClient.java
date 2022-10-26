package com.yz.servicemap.remote;

import com.yz.internalcommon.constant.AmapConfigConstants;
import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: yangzhen
 * @Date 2022/10/26-23:29
 * @Description: com.yz.servicemap.remote
 * @version: 1.0
 */
@Service
public class MapDicDistrictClient {
    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public String dicDistrict(String keywords) {
        //=2&key=<用户的key>
        //拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.DISTRICT_URL);
        url.append("?");
        url.append("keywords=" + keywords);
        url.append('&');
        url.append("subdistrict=3");
        url.append("&");
        url.append("key=" + amapKey);

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);



        return forEntity.getBody();
    }
}
