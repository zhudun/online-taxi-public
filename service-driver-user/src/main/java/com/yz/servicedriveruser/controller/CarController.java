package com.yz.servicedriveruser.controller;


import com.yz.internalcommon.dto.Car;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yz
 * @since 2022-11-04
 */
@RestController
public class CarController {

    @Autowired
    CarService carService;

    @RequestMapping("/car")
    public ResponseResult addCar(@RequestBody Car car){

        return carService.addCar(car);
    }

    @GetMapping("/car")
    public ResponseResult<Car> getCarById(Long carId){

        return carService.getCarById(carId);
    }

}
