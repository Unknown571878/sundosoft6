package edu.du.project2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Project2ApplicationTests {

    @Test
    void 메일테스트(){
        System.out.println("메일발신자"+MailConst.mailSenderName);
    }

    @Test
    void test(){

    }

}
