package com.example.demo.api.controller;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
public class OrderController {



    @GetMapping(value = "/order")
    private void order(){

    }

}
