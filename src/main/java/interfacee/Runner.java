package interfacee;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import java.awt.Font;
import javax.swing.UIManager;

public class Runner {


    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "lcd"); // use font antialiasing
        System.setProperty("swing.aatext", "true");

        FlatRobotoFont.install();
        FlatArcDarkOrangeIJTheme.setup();
        java.awt.EventQueue.invokeLater(() -> {

            new visualGraphX().setVisible(true);
            UIManager.put("defaulFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 15));
            UIManager.put("Panel.arc", 300);
            UIManager.put("Button.arc", 300);
            UIManager.put("Border.arc", 100);
            UIManager.put("CheckBox.arc", 100);
            UIManager.put("ProgressBar.arc", 100);
            UIManager.put("TextComponent.arc", 100);

        });
        
    }
}
