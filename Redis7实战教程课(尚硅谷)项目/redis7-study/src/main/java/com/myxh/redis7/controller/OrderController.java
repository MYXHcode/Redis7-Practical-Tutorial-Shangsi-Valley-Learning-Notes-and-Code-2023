package com.myxh.redis7.controller;

import com.myxh.redis7.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author MYXH
 * @date 2023/11/28
 */
@Tag(name = "订单接口")
@Slf4j
@RestController()
@RequestMapping("/order")
public class OrderController
{
    @Resource
    private OrderService orderService;

    @Operation(summary = "新增订单")
    @PostMapping("add")
    public void addOrder()
    {
        orderService.addOrder();
    }

    @Operation(summary = "按照 keyId 查询订单信息")
    @GetMapping("{keyId}")
    public String findUserById(@Parameter(name = "keyId", required = true)
                               @PathVariable("keyId") Integer keyId)
    {
        return orderService.getOrderById(keyId);
    }
}
