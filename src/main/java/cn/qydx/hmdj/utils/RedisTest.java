package cn.qydx.hmdj.utils;

import cn.qydx.hmdj.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class RedisTest {

    @Autowired
    private RedisUtil util;

    @Test
    public void test1(){
        util.set("k1", "v1");
    }

}
