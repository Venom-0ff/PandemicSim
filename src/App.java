import java.awt.*;
import javax.swing.*;

/**
* The {@code App} class is a driver class, it contains the main() function.
* @author Stepan Kostyukov
*/
public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pandemic Simulator");
		
		// boilerplate
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setSize(1920,1080);
		frame.setLocationRelativeTo(null);
		
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		
		frame.add(new Simulation());
		frame.pack();

		frame.setVisible(true);
    } // end main()

} // end class
