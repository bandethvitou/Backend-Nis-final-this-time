package com.sarana_NIS.newTown_backend.security.encoding;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public HexPasswordEncoder hexPasswordEncoder() {
        return new HexPasswordEncoder();
    }

    @Bean
    public HexPasswordDecoder hexPasswordDecoder() {
        return new HexPasswordDecoder();
    }
}