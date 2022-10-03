package it.units.freedomsdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FreedomSdmApplicationTests {

    @Test
    void contextLoads() {
        // First writing, nothing to test
        int x = 0;
        Assertions.assertEquals(x, 1);
    }

}
