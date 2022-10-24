package com.yz.apipassenger.controller;

import com.yz.apipassenger.service.TokenService;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/24-14:15
 * @Description: com.yz.apipassenger.controller
 * @version: 1.0
 */
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse){

        String refreshTokenSrc = tokenResponse.getRefreshToken();
        System.out.println("原来的refreshToken"+refreshTokenSrc);

        return tokenService.refreshToken(refreshTokenSrc);

    }

}
