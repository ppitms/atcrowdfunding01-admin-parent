package com.zhijia.crowd.test;

import com.zhijia.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * @author zhijia
 * @create 2022-03-15 20:17
 */
public class StringTest {

    @Test
    public void testMd5(){
        String source="12312";
        String encoded = CrowdUtil.md5(source);
        System.out.println(encoded);
    }
}
