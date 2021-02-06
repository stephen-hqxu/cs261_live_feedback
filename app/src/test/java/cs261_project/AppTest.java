package cs261_project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppTest{

    @Test
    void test(){
        assertEquals(1, 1);
    }
}