package com.example.eureka.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/3/14 20:47
 */
@Component
public class Sender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        String s = "hello" + new Date();
        System.out.println(s);
        this.amqpTemplate.convertAndSend("hello", s);
    }
}
