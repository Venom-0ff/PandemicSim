import java.awt.*;
import java.awt.event.*;
import java.util.Random;
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
    private final int POPULATION = 600;
    private Person[] personArray;

    /**
     * Starts the simulation of the pandemic.
     */
    public Simulation() {
        this.time = new Timer(REFRESH_TIME, new CollisionListener());

        personArray = new Person[POPULATION];
        // the first infected
        personArray[0] = new Person(0, WIDTH, HEIGHT);

        // TODO:
        // Populate the array with objects based on the user specified values.

        // array with dummy population, (no one has immunity)
        for (int i = 1; i < personArray.length; i++) {
            personArray[i] = new Person(1, WIDTH, HEIGHT);
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
            for (int i = 0; i < POPULATION; i++) {
                // move the population around with move()
                Person.move(personArray[i], WIDTH, HEIGHT);

                // increment the repaint cycle counter if the person is infected
                if (personArray[i].isInfected()) {
                    personArray[i].setCycleCount(personArray[i].getCycleCount() + 1);

                    // check if the counter >= 150 and determine the person's state
                    if (personArray[i].getCycleCount() >= 150) {
                        Random rand = new Random();
                        personArray[i].setIsAlive(!(rand.nextDouble(1.0) <= personArray[i].getDEATH_CHANCE(personArray[i].getImmunityStatus())));

                        if (personArray[i].isAlive()) {
                            personArray[i].setColour(Color.GREEN);

                            int newImmunityStatus = personArray[i].getImmunityStatus() < 3 ? 5 : personArray[i].getImmunityStatus();
                            personArray[i].setImmunityStatus(newImmunityStatus);
                        }
                        else {
                            personArray[i].setColour(Color.BLACK);

                            personArray[i].setXInc(0);
                            personArray[i].setYInc(0);
                        }

                        personArray[i].setIsInfected(false);
                    }
                }
            }

            // check for collisions among the population
            for (int i = 0; i < POPULATION; i++) {
                for (int j = i + 1; j < POPULATION; j++) {
                    Person.checkCollision(personArray[i], personArray[j]);
                }
            }

            repaint();
        } // end actionPerformed()
    } // end CollisionListener class

} // end class
