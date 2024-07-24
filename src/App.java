
import javax.swing.JFrame;

public class App {

    public static void main(String[] args) throws Exception {
        int width = 640;
        int height = 500;
        JFrame frame = new JFrame("Air Journey");
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Background bg = new Background();     
        frame.add(bg);
        frame.pack();
        
        frame.requestFocus();
        frame.setVisible(true);
    }
}
