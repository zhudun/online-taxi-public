package com.yz.apiboss.service;

import com.yz.apiboss.clients.DriverUserCilent;
import com.yz.internalcommon.dto.DriverCarBindingRelationship;
import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/11/6-15:52
 * @Description: com.yz.apiboss.service
 * @version: 1.0
 */
@Service
public class DriverCarBindingRelationshipService {
    @Autowired
    private DriverUserCilent driverUserCilent;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship){
        return driverUserCilent.bind(driverCarBindingRelationship);
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship){
        return driverUserCilent.unbind(driverCarBindingRelationship);
    }


}
