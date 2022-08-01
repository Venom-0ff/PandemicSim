import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* The {@code Simulation} class represents a spread of infection among population.
* @author Stepan Kostyukov
*/
public class Simulation extends JPanel {
    // class members
    private Timer time;
    private final int WIDTH = 1920, HEIGHT = 1080;      // size of JPanel
	private final int REFRESH_TIME = 200;               // time in milliseconds between re-paints of the screen
	
    // TODO:
    // 1. Make POPULATION a user-defined value.
    // 2. Add other user-defined parameters (Levels of immunity for specified percentages of the population).
    private final int POPULATION = 100;
    private Person[] personArray;

    /**
     * Starts the simulation of the pandemic.
     */
    public Simulation() {
        this.time = new Timer(REFRESH_TIME, new CollisionListener());

        personArray = new Person[POPULATION];
        // TODO:
        // Populate the array with objects (just a dummy for now).
        for (int i = 0; i < personArray.length; i++) {
            personArray[i] = new Person();
        }

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.GRAY);

        this.time.start();

    } // end Simulation()

    // TODO:
    // Write a Pause()/Resume() functions.

    @Override
    public void paintComponent(Graphics g)//The Graphics object 'g' is your paint brush
	{
		super.paintComponent(g);
		
		for(int i = 0; i < personArray.length; i++)
		{
			//get the color
			g.setColor(personArray[i].getColour());
			g.fillOval(
                personArray[i].getXCoord(),
                personArray[i].getYCoord(),
                Person.getDiameter(),
                Person.getDiameter()
                );
		}
	}//end paintComponent over-ride


    private class CollisionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // move the population around with move()
            for (int i = 0; i < POPULATION; i++) {
                Person.move(personArray[i], WIDTH, HEIGHT);
            }

            // check for collisions among the population
            for (int i = 0; i < POPULATION; i++) {
                for (int j = 0; j < POPULATION; j++) {
                    Person.checkCollision(personArray[i], personArray[j]);
                }
            }

            repaint();
        } // end actionPerformed()
    } // end CollisionListener class

} // end class
