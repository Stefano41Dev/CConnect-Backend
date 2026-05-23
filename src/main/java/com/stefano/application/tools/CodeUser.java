package com.stefano.application.tools;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CodeUser {
    public String codeRandom() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }
}
