package com.xxx.modules;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest
class PersonnelApplicationTests {

    @Test
    void contextLoads() {
        Calendar calendar = Calendar.getInstance();

        Integer currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int i = calendar.get(Calendar.MONTH) + 1;
        System.out.println(i);
        System.out.println(currentDay);
    }

}
