package com.yz.servicemap.service;

import com.yz.internalcommon.constant.AmapConfigConstants;
import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.dto.DicDistrict;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicemap.mapper.DicDistrictMapper;
import com.yz.servicemap.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    public ResponseResult initDicDistrict(String keywords){

        //请求地图
        String dicDistrictResult = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrictResult);
        //解析结果
        JSONObject dicDistrictJsonObject = JSONObject.fromObject(dicDistrictResult);
        int status = dicDistrictJsonObject.getInt(AmapConfigConstants.STATUS);
        if(status!=1){
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(),CommonStatusEnum.MAP_DISTRICT_ERROR.getMessage());
        }
        JSONArray countryJsonArray = dicDistrictJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
        for (int country = 0; country < countryJsonArray.size(); country++) {
            JSONObject countryJsonObject = countryJsonArray.getJSONObject(country);
            String countryAddressCode = countryJsonObject.getString(AmapConfigConstants.ADCODE);
            String countryAddressName = countryJsonObject.getString(AmapConfigConstants.NAME);
            String countryParentAddressCode = "0";
            String countryLevel = countryJsonObject.getString(AmapConfigConstants.LEVEL);
            insertDicDistrict(countryAddressCode,countryAddressName,countryParentAddressCode,countryLevel);
            JSONArray provinceJsonArray = countryJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
            for (int province = 0; province < provinceJsonArray.size(); province++) {
                JSONObject provinceJsonObject = provinceJsonArray.getJSONObject(province);
                String provinceAddressCode = provinceJsonObject.getString(AmapConfigConstants.ADCODE);
                String provinceAddressName = provinceJsonObject.getString(AmapConfigConstants.NAME);
                String provinceParentAddressCode = countryAddressCode;
                String provinceLevel = provinceJsonObject.getString(AmapConfigConstants.LEVEL);
                insertDicDistrict(provinceAddressCode,provinceAddressName,provinceParentAddressCode,provinceLevel);
                JSONArray cityJsonArray = provinceJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
                for (int city = 0; city < cityJsonArray.size(); city++) {
                    JSONObject cityJsonObject = cityJsonArray.getJSONObject(city);
                    String cityAddressCode = cityJsonObject.getString(AmapConfigConstants.ADCODE);
                    String cityAddressName = cityJsonObject.getString(AmapConfigConstants.NAME);
                    String cityParentAddressCode = provinceAddressCode;
                    String cityLevel = cityJsonObject.getString(AmapConfigConstants.LEVEL);

                    insertDicDistrict(cityAddressCode,cityAddressName,cityParentAddressCode,cityLevel);
                    JSONArray districtJsonArray = cityJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
                    for (int district = 0; district < districtJsonArray.size();district++) {
                        JSONObject districtJsonObject = districtJsonArray.getJSONObject(district);
                        String districtAddressCode = districtJsonObject.getString(AmapConfigConstants.ADCODE);
                        String districtAddressName = districtJsonObject.getString(AmapConfigConstants.NAME);
                        String districtParentAddressCode = cityAddressCode;
                        String districtLevel = districtJsonObject.getString(AmapConfigConstants.LEVEL);

                        if(districtLevel.equals(AmapConfigConstants.STREET)){
                            continue;
                        }
                        insertDicDistrict(districtAddressCode,districtAddressName,districtParentAddressCode,districtLevel);
                    }
                }
            }
        }

        return ResponseResult.success("");
    }

    public void insertDicDistrict(String addressCode,String addressName,String parentAddressCode,String level){
        //数据库对象
        DicDistrict dicDistrict = new DicDistrict();
        dicDistrict.setAddressCode(addressCode);
        dicDistrict.setAddressName(addressName);
        int levelInt = generateLevel(level);
        dicDistrict.setLevel(levelInt);
        dicDistrict.setParentAddressCode(parentAddressCode);
        //插入数据库
        dicDistrictMapper.insert(dicDistrict);

    }

    /**
     * 根据级别（国家、省份，市，地区） 返回对应的数字
     * @param level
     * @return
     */
    public int generateLevel(String level){
        int levelInt = 0;
        if(level.trim().equals("country")){
            levelInt = 0;
        }else if(level.trim().equals("province")) {
            levelInt = 1;
        }else if(level.trim().equals("city")){
            levelInt = 2;
        }else if(level.trim().equals("district")){
            levelInt = 3;
        }
        return levelInt;
    }
}
