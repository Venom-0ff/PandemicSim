import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;

/**
* The {@code Simulation} class represents a spread of infection among population.
* @author Stepan Kostyukov
*/
public class Simulation extends JPanel {
    // class members
    public Timer time;
    private final int WIDTH = 1920, HEIGHT = 1080;      // size of JPanel
	private final int REFRESH_TIME = 200;               // time in milliseconds between re-paints of the screen
	
    private int population = 100;
    private int oneShotPercent = 0;
    private int twoShotsPercent = 0;
    private int threeShotsPercent = 0;
    private int recoveredPercent = 0;
    private ArrayList<Person> personArray;

    /**
     * Starts the simulation of the pandemic.
     */
    public Simulation(int populationNum, int oneShotPercent, int twoShotsPercent, int threeShotsPercent, int recovered) {
        JFrame frame = new JFrame("Pandemic Simulator");
		
		// boilerplate
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setSize(1920,1080);
		frame.setLocationRelativeTo(null);
		
		frame.getContentPane().setBackground(Color.DARK_GRAY);

        this.time = new Timer(REFRESH_TIME, new CollisionListener());

        this.population = populationNum;
        this.oneShotPercent = oneShotPercent;
        this.twoShotsPercent = twoShotsPercent;
        this.threeShotsPercent = threeShotsPercent;
        this.recoveredPercent = recovered;

        personArray = new ArrayList<Person>();
        // the first infected person
        personArray.add(new Person(0, WIDTH, HEIGHT));

        if (this.oneShotPercent != 0) {
            for (int i = 0; i < this.population / this.oneShotPercent * 100; i++) {
                personArray.add(new Person(2, WIDTH, HEIGHT));
            }
        }

        if (this.twoShotsPercent != 0) {
            for (int i = 0; i < this.population / this.twoShotsPercent * 100; i++) {
                personArray.add(new Person(3, WIDTH, HEIGHT));
            }
        }

        if (this.threeShotsPercent != 0) {
            for (int i = 0; i < this.population / this.threeShotsPercent * 100; i++) {
                personArray.add(new Person(4, WIDTH, HEIGHT));
            }
        }

        if (this.recoveredPercent != 0) {
            for (int i = 0; i < this.population / this.recoveredPercent * 100; i++) {
                personArray.add(new Person(5, WIDTH, HEIGHT));
            }
        }

        for (int i = 0; i < this.population - personArray.size(); i++) {
            personArray.add(new Person(1, WIDTH, HEIGHT));
        }

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.GRAY);

		frame.add(this);
		frame.pack();

		frame.setVisible(true);

        this.time.start();

    } // end Simulation()

    // TODO:
    // Write a Pause()/Resume() functions.

    @Override
    public void paintComponent(Graphics g)//The Graphics object 'g' is your paint brush
	{
		super.paintComponent(g);
		
		for(int i = 0; i < personArray.size(); i++)
		{
			//get the color
			g.setColor(personArray.get(i).getColour());
			g.fillOval(
                personArray.get(i).getXCoord(),
                personArray.get(i).getYCoord(),
                Person.getDiameter(),
                Person.getDiameter()
                );
		}
	}//end paintComponent over-ride


    private class CollisionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < personArray.size(); i++) {
                // move the population around with move()
                Person.move(personArray.get(i), WIDTH, HEIGHT);

                // increment the repaint cycle counter if the person is infected
                if (personArray.get(i).isInfected()) {
                    personArray.get(i).setCycleCount(personArray.get(i).getCycleCount() + 1);

                    // check if the counter >= 150 and determine the person's state
                    if (personArray.get(i).getCycleCount() >= 150) {
                        Random rand = new Random();
                        personArray.get(i).setIsAlive(!(rand.nextDouble(1.0) <= personArray.get(i).getDEATH_CHANCE(personArray.get(i).getImmunityStatus())));

                        if (personArray.get(i).isAlive()) {
                            personArray.get(i).setColour(Color.GREEN);

                            int newImmunityStatus = personArray.get(i).getImmunityStatus() < 3 ? 5 : personArray.get(i).getImmunityStatus();
                            personArray.get(i).setImmunityStatus(newImmunityStatus);
                        }
                        else {
                            personArray.get(i).setColour(Color.BLACK);

                            personArray.get(i).setXInc(0);
                            personArray.get(i).setYInc(0);
                        }

                        personArray.get(i).setIsInfected(false);
                    }
                }
            }

            // check for collisions among the population
            for (int i = 0; i < personArray.size(); i++) {
                for (int j = i + 1; j < personArray.size(); j++) {
                    Person.checkCollision(personArray.get(i), personArray.get(j));
                }
            }

            repaint();
        } // end actionPerformed()
    } // end CollisionListener class

} // end class
