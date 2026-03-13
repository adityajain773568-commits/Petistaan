package com.abhishekvermaa10;

import com.abhishekvermaa10.service.OwnerService;
import com.abhishekvermaa10.service.PetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class Demo {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);
    private final OwnerService ownerService;
    private final PetService petService;

    public static void main(String[] args) {
        SpringApplication.run(Demo.class, args);
    }


}
