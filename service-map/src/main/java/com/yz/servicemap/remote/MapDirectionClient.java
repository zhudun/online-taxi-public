package com.yz.servicemap.remote;

import com.yz.internalcommon.constant.AmapConfigConstants;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.jws.Oneway;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-20:46
 * @Description: com.yz.servicemap.remote
 * @version: 1.0
 */
@Service
@Slf4j
public class MapDirectionClient {


    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;


    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        //组装请求调用url
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstants.DIRECTION_URL);
        urlBuilder.append("?");
        urlBuilder.append("origin=" + depLongitude + "," + depLatitude);
        urlBuilder.append("&");
        urlBuilder.append("destination=" + destLongitude + "," + destLatitude);
        urlBuilder.append("&");
        urlBuilder.append("extensions=all");
        urlBuilder.append("&");
        urlBuilder.append("output=json");
        urlBuilder.append("&");
        urlBuilder.append("key=" + amapKey);
        log.info(urlBuilder.toString());

        //调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        String directionString = directionEntity.getBody();
        log.info("高德地图，路径规划，返回信息：" + directionString);
        DirectionResponse directionResponse = parseDirectionEntity(directionString);
        return directionResponse;
    }

    //解析接口
    private DirectionResponse parseDirectionEntity(String directionString) {

        DirectionResponse directionResponse = null;
        try {
            //最外层
            JSONObject result = JSONObject.fromObject(directionString);
            if (result.has(AmapConfigConstants.STATUS)) {
                int status = result.getInt(AmapConfigConstants.STATUS);
                if (status == 1) {
                    if (result.has(AmapConfigConstants.ROUTE)) {
                        JSONObject routeObject = result.getJSONObject(AmapConfigConstants.ROUTE);
                        JSONArray pathsArray = routeObject.getJSONArray(AmapConfigConstants.PATHS);
                        JSONObject pathObject = pathsArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                        if (pathObject.has(AmapConfigConstants.DISTANCE)) {
                            int distance = pathObject.getInt(AmapConfigConstants.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if (pathObject.has(AmapConfigConstants.DURATION)) {
                            int duration = pathObject.getInt(AmapConfigConstants.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return directionResponse;

    }


}
