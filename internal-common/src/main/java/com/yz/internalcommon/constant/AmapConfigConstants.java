package com.yz.internalcommon.constant;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-20:54
 * @Description: com.yz.internalcommon.constant
 * @version: 1.0
 */
public class AmapConfigConstants {

    /**
     * 行政地区查询
     */
    public static final String DISTRICT_URL = "https://restapi.amap.com/v3/config/district";

    /**
     * 路径规划地址
     */
    public static final String DIRECTION_URL = "https://restapi.amap.com/v3/direction/driving";

    /**
     * 新增服务
     */
    public static final String SERVICE_ADD_URL = "https://tsapi.amap.com/v1/track/service/add";

    /**
     * 创建终端
     */
    public static final String TERMINAL_ADD = "https://tsapi.amap.com/v1/track/terminal/add";

    /**
     * 创建轨迹
     */
    public static final String TRACK_ADD = "https://tsapi.amap.com/v1/track/trace/add";

    /**
     * 轨迹点上传
     */
    public static final String POINT_UPLOAD = "https://tsapi.amap.com/v1/track/point/upload";

    /**
     * 终端搜索
     */
    public static final String TERMINAL_AROUNDSEARCH = "https://tsapi.amap.com/v1/track/terminal/aroundsearch";

    /**
     * 查询轨迹结果（包括：路程和时长）
     */
    public static final String TERMINAL_TRSEARCH = "https://tsapi.amap.com/v1/track/terminal/trsearch";



    /**
     * 路径规划 json status值
     */
    public static final String STATUS = "status";

    /**
     * 路径规划 json 地区值
     */
    public static final String DISTRICTS = "districts";

    /**
     * 路径规划 json 地区邮政编码
     */
    public static final String ADCODE = "adcode";

    /**
     * 路径规划 json 地区名称
     */
    public static final String NAME = "name";

    /**
     * 路径规划 json 地区等级
     */
    public static final String LEVEL = "level";

    /**
     * 路径规划 json 地区街道
     */
    public static final String STREET = "street";


    /**
     * 路径规划 json route值
     */
    public static final String ROUTE = "route";


    /**
     * 路径规划 json paths值
     */
    public static final String PATHS = "paths";

    /**
     * 路径规划 json paths值
     */
    public static final String DISTANCE = "distance";

    /**
     * 路径规划 json paths值
     */
    public static final String DURATION = "duration";


}
