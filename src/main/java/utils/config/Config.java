package utils.config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * @ClassName: Config
 * @ProjectName WorkStation
 * @Description: 读取配置信息的类
 * @Author 15791
 * @Aate 2020/9/217:35
 */
public class Config {

    public final void readConfig(){
        String path = this.getClass().getClassLoader().getResource("").getPath();
        try {
            File f = new File(path + "config.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(f);
            Element element = doc.getRootElement();
            Iterator<Element> reader1 = element.elementIterator("reader");
            Element foo;
            foo = reader1.next();
            System.out.println("IP:" + foo.elementText("IP"));

//            for (Iterator i = element.elementIterator("reader"); i.hasNext();) {
//                  foo = (Element) i.next();
//                  System.out.println("IP:" + foo.elementText("IP"));
//                  System.out.println("端口:" + foo.elementText("port"));
//            }
        } catch (Exception e) {
         e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Config().readConfig();
    }
}
