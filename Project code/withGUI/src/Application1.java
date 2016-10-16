import javax.swing.UIManager;
import java.awt.*;

public class Application1 {
    boolean packFrame = false;

    public Application1() {
        Frame1 frame = new Frame1();
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//централізація
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

    /** Main method */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager
                    .getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Application1();
    }
}