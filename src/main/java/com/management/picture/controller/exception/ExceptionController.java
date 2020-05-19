package com.management.picture.controller.exception;

import com.management.picture.model.ResultMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.UnsupportedEncodingException;

/**
 * Created on 2020/4/23.
 *
 * @author Yue Wu
 */
@RestController
@ApiIgnore
public class ExceptionController {

    @Autowired
    private ResultMap resultMap;

    @RequestMapping(path = "/unauthorized/{message}")
    @ApiOperation("Shiro认证异常处理的接口")
    @ApiImplicitParam(name = "message", value = "异常信息")
    public ResultMap unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        resultMap.clear();
        return resultMap.success().code(401).message(message);
    }
}
