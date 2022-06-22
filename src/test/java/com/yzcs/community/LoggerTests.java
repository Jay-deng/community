package com.yzcs.community;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerTests.class);

    @Test
    public void testLogger() {
        System.out.println(LOGGER.getName());

        // 以下日志级别由低到高，当在application.properties里配置相应的日志级别时，也会运行比它高级别的日志模式
        LOGGER.debug("debug logger");
        LOGGER.info("info logger");
        LOGGER.warn("warn logger");
        LOGGER.error("error logger");
    }
}
