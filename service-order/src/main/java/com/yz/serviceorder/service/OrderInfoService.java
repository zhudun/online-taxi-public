package com.yz.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.constant.IdentityConstants;
import com.yz.internalcommon.constant.OrderConstants;
import com.yz.internalcommon.dto.Car;
import com.yz.internalcommon.dto.OrderInfo;
import com.yz.internalcommon.dto.PriceRule;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import com.yz.internalcommon.request.PriceRuleIsNewRequest;
import com.yz.internalcommon.response.OrderDriverResponse;
import com.yz.internalcommon.response.TerminalResponse;
import com.yz.internalcommon.response.TrsearchResponse;
import com.yz.internalcommon.util.RedisPrefixUtils;
import com.yz.serviceorder.mapper.OrderInfoMapper;
import com.yz.serviceorder.remote.ServiceDriverUserClient;
import com.yz.serviceorder.remote.ServiceMapClient;
import com.yz.serviceorder.remote.ServicePriceClient;
import com.yz.serviceorder.remote.ServiceSsePushClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yangzhen
 * @Date 2022/11/12-15:52
 * @Description: com.yz.serviceorder.service
 * @version: 1.0
 */
@Service
@Slf4j
public class OrderInfoService {
    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    ServicePriceClient servicePriceClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ServiceMapClient serviceMapClient;


    /**
     * ????????????
     * @param orderRequest
     * @return
     */
    public ResponseResult add(OrderRequest orderRequest){
        //?????????????????????????????????
        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(orderRequest.getAddress());
        log.info("???????????????????????????????????????:"+availableDriver.getData());
        if(!availableDriver.getData()){
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(),CommonStatusEnum.CITY_DRIVER_EMPTY.getMessage());
        }

        //????????????????????????????????????????????????
        PriceRuleIsNewRequest priceRuleIsNewRequest = new PriceRuleIsNewRequest();
        priceRuleIsNewRequest.setFareType(orderRequest.getFareType());
        priceRuleIsNewRequest.setFareVersion(orderRequest.getFareVersion());
        ResponseResult<Boolean> aNew = servicePriceClient.isNew(priceRuleIsNewRequest);
        if(!aNew.getData()){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(),CommonStatusEnum.PRICE_RULE_CHANGED.getMessage());
        }


        //??????key?????????????????????key
        if (isBlackDevice(orderRequest))
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getMessage());

        //?????????????????????????????????????????????????????????????????????
        if(isPassengerOrderGoingon(orderRequest.getPassengerId())>0){
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(),CommonStatusEnum.ORDER_GOING_ON.getMessage());
        }

        //????????????????????????????????????????????????
        if(!isPriceRuleExists(orderRequest)){
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getCode(),CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getMessage());
        }

        //????????????
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest,orderInfo);
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);


        orderInfoMapper.insert(orderInfo);

        // ?????????????????????
        for (int i =0;i<6;i++){
            // ?????? dispatchRealTimeOrder
            int result = dispatchRealTimeOrder(orderInfo);
            if (result == 1){
                break;
            }
            if (i == 5){
                // ????????????
                orderInfo.setOrderStatus(OrderConstants.ORDER_INVALID);
                orderInfoMapper.updateById(orderInfo);
            }else {
                // ??????20s
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return ResponseResult.success();
    }

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ServiceSsePushClient serviceSsePushClient;

    /**
     * ????????????????????????
     * ????????????1???????????????
     * @param orderInfo
     */
    public  int  dispatchRealTimeOrder(OrderInfo orderInfo){
        log.info("????????????");
        int result = 0;
        //2km
        String depLatitude = orderInfo.getDepLatitude();
        String depLongitude = orderInfo.getDepLongitude();

        String center = depLatitude+","+depLongitude;

        List<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);
        //????????????
        ResponseResult<List<TerminalResponse>> listResponseResult =null;
        radius:
        for (int i = 0; i < radiusList.size(); i++) {
            Integer radius = radiusList.get(i);
            listResponseResult = serviceMapClient.terminalAroundsearch(center,radius);
            log.info("????????????"+radius+"???????????????????????????,?????????"+ JSONArray.fromObject(listResponseResult.getData()).toString());
            //????????????

            //????????????
            List<TerminalResponse> data = listResponseResult.getData();
            // ?????????????????????????????????????????????
//            List<TerminalResponse> data = new ArrayList<>();
            for (int j = 0; j < data.size(); j++) {
                TerminalResponse terminalResponse = data.get(j);
                long carId = terminalResponse.getCarId();

                String longitude = terminalResponse.getLongitude();
                String latitude = terminalResponse.getLatitude();

                //?????????????????????????????????
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode()) {
                    log.info("????????????ID:" + carId+"???????????????");
                    continue;
                } else {
                    log.info("??????ID:" + carId+"??????????????????????????????");
                    OrderDriverResponse orderDriverResponse = availableDriver.getData();
                    Long driverId = orderDriverResponse.getDriverId();
                    String driverPhone = orderDriverResponse.getDriverPhone();
                    String licenseId = orderDriverResponse.getLicenseId();
                    String vehicleNo = orderDriverResponse.getVehicleNo();
                    String vehicleTypeFromCar = orderDriverResponse.getVehicleType();

                    // ????????????????????????????????????
                    String vehicleType = orderInfo.getVehicleType();
                    if (!vehicleType.trim().equals(vehicleTypeFromCar.trim())){
                        System.out.println("???????????????");
                        continue ;
                    }

                    String lockKey = (driverId+"").intern();
                    RLock lock = redissonClient.getLock(lockKey);
                    lock.lock();

                    //??????????????????????????????????????????
                    if(isDriverOrderGoingon(driverId)>0){
                        lock.unlock();
                        continue;
                    }
                    //?????? ???????????????????????????
                    // ????????????????????????
                    QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
                    carQueryWrapper.eq("id",carId);
                    // ?????????????????????????????????????????????
                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(driverPhone);
                    orderInfo.setCarId(carId);
                    // ???????????????
                    orderInfo.setReceiveOrderCarLongitude(longitude);
                    orderInfo.setReceiveOrderCarLatitude(latitude);

                    orderInfo.setReceiveOrderTime(LocalDateTime.now());
                    orderInfo.setLicenseId(licenseId);
                    orderInfo.setVehicleNo(vehicleNo);
                    orderInfo.setVehicleType(vehicleType);
                    orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER);

                    orderInfoMapper.updateById(orderInfo);
                    // ????????????
                    JSONObject driverContent = new  JSONObject();
                    driverContent.put("orderId",orderInfo.getId());
                    driverContent.put("passengerId",orderInfo.getPassengerId());
                    driverContent.put("passengerPhone",orderInfo.getPassengerPhone());
                    driverContent.put("departure",orderInfo.getDeparture());
                    driverContent.put("depLongitude",orderInfo.getDepLongitude());
                    driverContent.put("depLatitude",orderInfo.getDepLatitude());

                    driverContent.put("destination",orderInfo.getDestination());
                    driverContent.put("destLongitude",orderInfo.getDestLongitude());
                    driverContent.put("destLatitude",orderInfo.getDestLatitude());

                    serviceSsePushClient.push(driverId, IdentityConstants.DRIVER_IDENTITY,driverContent.toString());

                    // ????????????
                    JSONObject passengerContent = new  JSONObject();
                    passengerContent.put("orderId",orderInfo.getId());
                    passengerContent.put("driverId",orderInfo.getDriverId());
                    passengerContent.put("driverPhone",orderInfo.getDriverPhone());
                    passengerContent.put("vehicleNo",orderInfo.getVehicleNo());
                    // ?????????????????????????????????
                    ResponseResult<Car> carById = serviceDriverUserClient.getCarById(carId);
                    Car carRemote = carById.getData();

                    passengerContent.put("brand", carRemote.getBrand());
                    passengerContent.put("model",carRemote.getModel());
                    passengerContent.put("vehicleColor",carRemote.getVehicleColor());

                    passengerContent.put("receiveOrderCarLongitude",orderInfo.getReceiveOrderCarLongitude());
                    passengerContent.put("receiveOrderCarLatitude",orderInfo.getReceiveOrderCarLatitude());

                    serviceSsePushClient.push(orderInfo.getPassengerId(), IdentityConstants.PASSENGER_IDENTITY,passengerContent.toString());
                    result = 1;
                    lock.unlock();
                    break radius;



                }


            }

            //????????????????????????????????????????????????


            //????????????????????????????????????

            //????????????????????????????????????

        }
        return  result;

    }

    /**
     * ????????????????????????
     * @param orderRequest
     * @return
     */
    private boolean isPriceRuleExists(OrderRequest orderRequest){
        String fareType =orderRequest.getFareType();
        int index=fareType.indexOf("$");
        String cityCode =fareType.substring(0,index);
        String vehicleType =fareType.substring(index+1);

        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);

        ResponseResult<Boolean> booleanResponseResult = servicePriceClient.ifPriceExists(priceRule);
        return booleanResponseResult.getData();
    }

    /**
     * ??????????????????
     * @param orderRequest
     * @return
     */
    private boolean isBlackDevice(OrderRequest orderRequest) {
        //???????????????????????????????????????????????????
        String deviceCode  = orderRequest.getDeviceCode();
        //??????key
        String deviceCodeKey = RedisPrefixUtils.blackDeviceCodePrefix+deviceCode;
        Boolean aBoolean = stringRedisTemplate.hasKey(deviceCodeKey);
        if(aBoolean){
            String s = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            int i = Integer.parseInt(s);
            if(i>=2){
                return true;
            }else{
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        }else{
            stringRedisTemplate.opsForValue().setIfAbsent(deviceCodeKey,"1",1L,TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * ???????????????????????????????????????
     * @param passengerId
     * @return
     */
    private int isPassengerOrderGoingon(Long passengerId){
        // ?????????????????????????????????????????????
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id",passengerId);
        queryWrapper.and(wrapper->wrapper.eq("order_status",OrderConstants.ORDER_START)
                .or().eq("order_status",OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status",OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status",OrderConstants.PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.PASSENGER_GETOFF)
                .or().eq("order_status",OrderConstants.TO_START_PAY)
        );


        Integer validOrderNumber = orderInfoMapper.selectCount(queryWrapper);

        return validOrderNumber;

    }

    /**
     * ???????????????????????????????????????
     * @param driverId
     * @return
     */
     private int isDriverOrderGoingon(Long driverId){
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("driver_id",driverId);
        queryWrapper.and(wrapper->wrapper.eq("order_status",OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status",OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status",OrderConstants.PICK_UP_PASSENGER)
        );
        Integer validOrderNumber = orderInfoMapper.selectCount(queryWrapper);
        log.info("??????ID???"+driverId+"???????????????????????????????????????"+validOrderNumber);
        return validOrderNumber;
    }
    /**
     * ????????????
     * @param orderRequest
     * @return
     */
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest){
        Long orderId = orderRequest.getOrderId();
        LocalDateTime toPickUpPassengerTime = orderRequest.getToPickUpPassengerTime();
        String toPickUpPassengerLongitude = orderRequest.getToPickUpPassengerLongitude();
        String toPickUpPassengerLatitude = orderRequest.getToPickUpPassengerLatitude();
        String toPickUpPassengerAddress = orderRequest.getToPickUpPassengerAddress();
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);

        orderInfo.setToPickUpPassengerAddress(toPickUpPassengerAddress);
        orderInfo.setToPickUpPassengerLatitude(toPickUpPassengerLatitude);
        orderInfo.setToPickUpPassengerLongitude(toPickUpPassengerLongitude);
        orderInfo.setToPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstants.DRIVER_TO_PICK_UP_PASSENGER);

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success();

    }

    /**
     * ???????????????????????????
     * @param orderRequest
     * @return
     */
    public ResponseResult arrivedDeparture(OrderRequest orderRequest){
        Long orderId = orderRequest.getOrderId();

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",orderId);

        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);
        orderInfo.setOrderStatus(OrderConstants.DRIVER_ARRIVED_DEPARTURE);

        orderInfo.setDriverArrivedDepartureTime(LocalDateTime.now());
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    /**
     * ??????????????????
     * @param orderRequest
     * @return
     */
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest){
        Long orderId = orderRequest.getOrderId();

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);

        orderInfo.setPickUpPassengerLongitude(orderRequest.getPickUpPassengerLongitude());
        orderInfo.setPickUpPassengerLatitude(orderRequest.getPickUpPassengerLatitude());
        orderInfo.setPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstants.PICK_UP_PASSENGER);

        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    /**
     * ??????????????????????????????????????????
     * @param orderRequest
     * @return
     */
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest){
        Long orderId = orderRequest.getOrderId();

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);

        orderInfo.setPassengerGetoffTime(LocalDateTime.now());
        orderInfo.setPassengerGetoffLongitude(orderRequest.getPassengerGetoffLongitude());
        orderInfo.setPassengerGetoffLatitude(orderRequest.getPassengerGetoffLatitude());

        orderInfo.setOrderStatus(OrderConstants.PASSENGER_GETOFF);
        // ??????????????????????????????,?????? service-map
        ResponseResult<Car> carById = serviceDriverUserClient.getCarById(orderInfo.getCarId());
        Long starttime = orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
       //??????????????????
        Long endtime = orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()+7200000*8;
//       ??????????????????
//        Long endtime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("???????????????"+starttime);
        System.out.println("???????????????"+endtime);
        ResponseResult<TrsearchResponse> trsearch = serviceMapClient.trsearch(carById.getData().getTid(), starttime,endtime);
        TrsearchResponse data = trsearch.getData();
        Long driveMile = data.getDriveMile();
        Long driveTime = data.getDriveTime();

        orderInfo.setDriveMile(driveMile);
        orderInfo.setDriveTime(driveTime);

        // ????????????
        String address = orderInfo.getAddress();//????????????????????????
        String vehicleType = orderInfo.getVehicleType();
        ResponseResult<Double> doubleResponseResult = servicePriceClient.calculatePrice(driveMile.intValue(), driveTime.intValue(), address, vehicleType);
        Double price = doubleResponseResult.getData();
        orderInfo.setPrice(price);

        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    /**
     * ??????
     * @param orderRequest
     * @return
     */
    public ResponseResult pay(OrderRequest orderRequest){

        Long orderId = orderRequest.getOrderId();
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

        orderInfo.setOrderStatus(OrderConstants.SUCCESS_PAY);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    /**
     * ????????????
     * @param orderId ??????Id
     * @param identity  ?????????1????????????2?????????
     * @return
     */
    public ResponseResult cancel(Long orderId, String identity){
        // ????????????????????????
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        Integer orderStatus = orderInfo.getOrderStatus();

        LocalDateTime cancelTime = LocalDateTime.now();
        Integer cancelOperator = null;   //
        Integer cancelTypeCode = null;   //??????????????????

        // ????????????
        int cancelType = 1;

        // ???????????????????????????
        // ?????????????????????
        if (identity.trim().equals(IdentityConstants.PASSENGER_IDENTITY)){
            switch (orderStatus){
                // ????????????
                case OrderConstants.ORDER_START:                                               //1
                    cancelTypeCode = OrderConstants.CANCEL_PASSENGER_BEFORE;
                    break;
                // ??????????????????
                case OrderConstants.DRIVER_RECEIVE_ORDER:                                      //2
                    LocalDateTime receiveOrderTime = orderInfo.getReceiveOrderTime();
                    long between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                    if (between > 1){
                        cancelTypeCode = OrderConstants.CANCEL_PASSENGER_ILLEGAL;
                    }else {
                        cancelTypeCode = OrderConstants.CANCEL_PASSENGER_BEFORE;
                    }
                    break;
                // ??????????????????
                case OrderConstants.DRIVER_TO_PICK_UP_PASSENGER:                               //3
                    // ????????????????????????
                case OrderConstants.DRIVER_ARRIVED_DEPARTURE:                                  //4
                    cancelTypeCode = OrderConstants.CANCEL_PASSENGER_ILLEGAL;
                    break;
                default:
                    log.info("??????????????????");
                    cancelType = 0;
                    break;
            }
        }

        // ?????????????????????
        if (identity.trim().equals(IdentityConstants.DRIVER_IDENTITY)){
            switch (orderStatus){
                // ????????????
                // ??????????????????
                case OrderConstants.DRIVER_RECEIVE_ORDER:                              //2
                case OrderConstants.DRIVER_TO_PICK_UP_PASSENGER:                       //3
                case OrderConstants.DRIVER_ARRIVED_DEPARTURE:                          //4
                    LocalDateTime receiveOrderTime = orderInfo.getReceiveOrderTime();
                    long between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                    if (between > 1){
                        cancelTypeCode = OrderConstants.CANCEL_DRIVER_ILLEGAL;         //5
                    }else {
                        cancelTypeCode = OrderConstants.CANCEL_DRIVER_BEFORE;          //2
                    }
                    break;

                default:
                    log.info("??????????????????");
                    cancelType = 0;
                    break;
            }
        }


        if (cancelType == 0){
            return ResponseResult.fail(CommonStatusEnum.ORDER_CANCEL_ERROR.getCode(),CommonStatusEnum.ORDER_CANCEL_ERROR.getMessage());
        }

        orderInfo.setCancelTypeCode(cancelTypeCode);
        orderInfo.setCancelTime(cancelTime);
        orderInfo.setCancelOperator(Integer.parseInt(identity));
        orderInfo.setOrderStatus(OrderConstants.ORDER_CANCEL);

        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    public ResponseResult pushPayInfo(OrderRequest orderRequest) {

        Long orderId = orderRequest.getOrderId();

        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfo.setOrderStatus(OrderConstants.TO_START_PAY);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();

    }
}
