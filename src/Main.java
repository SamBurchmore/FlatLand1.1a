import Controller.MainController;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        FlatIntelliJLaf.setup();
        UIManager.put("Panel.background", new Color(224, 224, 224));
        MainController mainController = new MainController(50, 0, 8, 1.5, 8);
    }
}