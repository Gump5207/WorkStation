import org.apache.log4j.Logger;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @ClassName: TestLog
 * @ProjectName WorkStation
 * @Description: TODO
 * @Author 15791
 * @Aate 2020/9/317:00
 */

public class TestLog {
    private static Logger logger = Logger.getLogger(TestLog.class);

    @Test
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

//        // 记录debug级别的信息
//        logger.debug("This is debug message.");
//        // 记录info级别的信息
//        logger.info("This is info message.");
//        // 记录error级别的信息
//        logger.error("This is error message.");
        Class cache = Integer.class.getDeclaredClasses()[0]; //1
        Field myCache = cache.getDeclaredField("cache"); //2
        myCache.setAccessible(true);//3
        Integer[] newCache = (Integer[]) myCache.get(cache); //4
        newCache[132] = newCache[133]; //5

        int a = 2;
        int b = a + a;
        System.out.printf("jj%d + %d = %d", a, a, b); //
        System.out.println("b:"+b);
    }
}
