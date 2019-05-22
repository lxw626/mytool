package com.lxw.mytool.controller;

import com.lxw.mytool.entity.MatAndCost;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/helloRest")
public class HelloRest {
    @PostMapping("/helloPost")
    public String helloPost(@RequestBody MatAndCost matAndCost){
        System.out.println(matAndCost);
        return "post请求返回结果:"+matAndCost.toString();
    }
    @DeleteMapping("/helloDelete")
    public String helloDelete(@RequestBody MatAndCost matAndCost){
        System.out.println(matAndCost);
        return "delete请求返回结果:"+matAndCost.toString();
    }
    @PutMapping("/helloPut")
    public String helloPut(@RequestBody MatAndCost matAndCost){
        System.out.println(matAndCost);
        return "put请求返回结果:"+matAndCost.toString();
    }
}
