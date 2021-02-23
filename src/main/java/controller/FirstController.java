package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * @ClassName: FirstController
 * @ProjectName WorkStation
 * @Description: TODO
 * @Author 15791
 * @Aate 2020/8/3118:44
 */
public class FirstController {

    @FXML
    TabPane tabPane;
    @FXML
    Tab home;

    @PostConstruct
    public void init(){
        final InputStream png = getClass().getResourceAsStream("/image/33.png");
        final Image image = new Image(png);
        System.out.println("图片导入成功！");
        home.setGraphic(new ImageView(image));
    }
}
