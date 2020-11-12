package com.jiangqi.mymicroservice.example.util.contrl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class HealthContrl {
	
	@ApiOperation(value="健康检查")
    @GetMapping(value = "/health")
    @ResponseStatus(HttpStatus.OK)
    public String test()
    {
        return "ok";
    }
}
