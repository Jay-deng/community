package com.yzcs.community;

import com.yzcs.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "这里严禁赌博，严禁嫖娼，严禁开票，严禁吸毒！";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        text = "这里严禁🐔赌🐔博🐔，严🐔禁🐔嫖🐔娼🐔，严禁开🐔票🐔，严禁🐔吸毒！🐔 fuc🐔k";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
