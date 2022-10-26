package com.yz.servicemap.service;

import com.yz.internalcommon.constant.AmapConfigConstants;
import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/10/26-23:07
 * @Description: com.yz.servicemap.service
 * @version: 1.0
 */
@Service
public class DicDistrictService {

    @Value("${amap.key}")
    private String amapKey;

    public ResponseResult initDicDistrict(String keywords){
        //=2&key=<用户的key>
        //拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.DISTRICT_URL);
        url.append("?");
        url.append("keywords="+keywords);
        url.append('&');
        url.append("subdistrict=3");
        url.append("&");
        url.append("key="+amapKey);




        //解析结果

        //插入数据库



        return ResponseResult.success();
    }
}
