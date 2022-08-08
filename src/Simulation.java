import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

import java.util.ArrayList;

/**
* The {@code Simulation} class represents a spread of infection among population.
* @author Stepan Kostyukov
* @author Andrew Belmont De Avila
*/
public class Simulation extends JPanel {
    // class members
    private Timer time;
    private int simCycleCount = 0;
    private boolean isOnPause = false;
    public boolean isOnPause() { return this.isOnPause; }
    private final int WIDTH = 800, HEIGHT = 600;      // size of JPanel
	private final int REFRESH_TIME = 200;             // time in milliseconds between re-paints of the screen
	
    private int population = 100;
    private int oneShotPercent = 0;
    private int twoShotsPercent = 0;
    private int threeShotsPercent = 0;
    private int recoveredPercent = 0;
    private ArrayList<Person> personArray;

    private JProgressBar progressBar;
    private JLabel lblInf;
    private JLabel lblNonVac;
    private JLabel lbl1Shot;
    private JLabel lbl2Shots;
    private JLabel lbl3Shots;
    private JLabel lblNatImm;
    private JLabel lblRecovered;
    private JLabel lblDied;

    /**
     * Starts the simulation of the pandemic.
     */
    public Simulation(int populationNum, int oneShotPercent, int twoShotsPercent, int threeShotsPercent, int recovered) {
        JFrame frame = new JFrame("Pandemic Simulator");
		
		// boilerplate
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(WIDTH+100,HEIGHT+100);
		frame.setLocationRelativeTo(null);
        frame.setResizable(false);

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
            for (int i = 0; i < this.population * this.oneShotPercent / 100; i++) {
                personArray.add(new Person(2, WIDTH, HEIGHT));
            }
        }

        if (this.twoShotsPercent != 0) {
            for (int i = 0; i < this.population * this.twoShotsPercent / 100; i++) {
                personArray.add(new Person(3, WIDTH, HEIGHT));
            }
        }

        if (this.threeShotsPercent != 0) {
            for (int i = 0; i < this.population * this.threeShotsPercent / 100; i++) {
                personArray.add(new Person(4, WIDTH, HEIGHT));
            }
        }

        if (this.recoveredPercent != 0) {
            for (int i = 0; i < this.population * this.recoveredPercent / 100; i++) {
                personArray.add(new Person(5, WIDTH, HEIGHT));
            }
        }

        for (int i = 0; i < this.population - personArray.size(); i++) {
            personArray.add(new Person(1, WIDTH, HEIGHT));
        }

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.GRAY);
        
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(8, 2, 10, 10));

        infoPanel.add(new JLabel("Number of infected persons:"));
        lblInf = new JLabel(String.valueOf(0));
        infoPanel.add(lblInf);

        infoPanel.add(new JLabel("Number of non-vaccinated persons infected:"));
        lblNonVac = new JLabel(String.valueOf(0));
        infoPanel.add(lblNonVac);

        infoPanel.add(new JLabel("Number of one-shot-vaccinated people infected:"));
        lbl1Shot = new JLabel(String.valueOf(0));
        infoPanel.add(lbl1Shot);

        infoPanel.add(new JLabel("Number of two-shot-vaccinated people infected:"));
        lbl2Shots = new JLabel(String.valueOf(0));
        infoPanel.add(lbl2Shots);

        infoPanel.add(new JLabel("Number of three-shot-vaccinated people infected:"));
        lbl3Shots = new JLabel(String.valueOf(0));
        infoPanel.add(lbl3Shots);

        infoPanel.add(new JLabel("Number of naturally immune people re-infected:"));
        lblNatImm = new JLabel(String.valueOf(0));
        infoPanel.add(lblNatImm);

        infoPanel.add(new JLabel("Number of infected people who have recovered:"));
        lblRecovered = new JLabel(String.valueOf(0));
        infoPanel.add(lblRecovered);

        infoPanel.add(new JLabel("Number of infected people who have died:"));
        lblDied = new JLabel(String.valueOf(0));
        infoPanel.add(lblDied);

        frame.add(progressBar, BorderLayout.NORTH);
		frame.add(this, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.EAST);
		frame.pack();

		frame.setVisible(true);

        this.time.start();


    } // end Simulation()

    /**
     * {@code Pause()} allows to pause a running simulation.
     */
    public void Pause() {
        if (!this.isOnPause()) {
            this.time.stop();
            this.isOnPause = true;
        }
    }

    /**
     * {@code Resume()} allows to resume previously paused simmulation.
     */
    public void Resume() {
        if (this.isOnPause()) {
            this.time.start();
            this.isOnPause = false;
        }
    }

    @Override
    public void paintComponent(Graphics g)
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
	} // end paintComponent()


    private class CollisionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int infSum = 0;
            int nonVacSum = 0;
            int oneShotSum = 0;
            int twoShotsSum = 0;
            int threeShotsSum = 0;
            int natImmSum = 0;
            int recovSum = 0;
            int diedSum = 0;

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
                        
                        personArray.get(i).setCycleCount(0);
                        personArray.get(i).setIsInfected(false);
                        infSum--;
                    }
                }
            }

            for (int i = 0; i < personArray.size(); i++) {
                if (!personArray.get(i).isAlive()) {
                    diedSum++;
                }
                else if (personArray.get(i).isInfected()) {
                    infSum++;
                    if (personArray.get(i).getImmunityStatus() == 1) { nonVacSum++; }
                    if (personArray.get(i).getImmunityStatus() == 2) { oneShotSum++; }
                    if (personArray.get(i).getImmunityStatus() == 3) { twoShotsSum++; }
                    if (personArray.get(i).getImmunityStatus() == 4) { threeShotsSum++; }
                    if (personArray.get(i).getImmunityStatus() == 5) { natImmSum++; }
                }
                else {
                    if (personArray.get(i).getImmunityStatus() == 5) { recovSum++; }
                }
                
            }

            lblInf.setText(String.valueOf(infSum));
            lblNonVac.setText(String.valueOf(nonVacSum));
            lbl1Shot.setText(String.valueOf(oneShotSum));
            lbl2Shots.setText(String.valueOf(twoShotsSum));
            lbl3Shots.setText(String.valueOf(threeShotsSum));
            lblNatImm.setText(String.valueOf(natImmSum));
            lblRecovered.setText(String.valueOf(recovSum));
            lblDied.setText(String.valueOf(diedSum));

            // check for collisions among the population
            for (int i = 0; i < personArray.size(); i++) {
                for (int j = i + 1; j < personArray.size(); j++) {
                    Person.checkCollision(personArray.get(i), personArray.get(j));
                }
            }

            // stop the simulation ofter 450 cycles
            if (simCycleCount > 450) {
                time.stop();
                // TODO:
                // After the simulation has finished, use the data generated to calculate and display this information:
                // 1)	Percentage of the total population that contracted the disease.
                // 2)	Percentage of unvaccinated persons who contracted the disease.
                // 3)	Percentage of one-shot-vaccinated persons who contracted the disease.
                // 4)	Percentage of two-shot-vaccinated persons who contracted the disease.
                // 5)	Percentage of three-shot-vaccinated persons who contracted the disease.
                // 6)	Percentage of those naturally immune persons who got re-infected.
                // 7)	Percentage of all those who contracted the disease that recovered.
                // 8)	Death Rate Percentage of all those who contracted the disease that died, broken down by their immunity status.

            }
            else {
                // fill the progressBar
                progressBar.setValue(simCycleCount * 100 / 450);
            }
            
            simCycleCount++;
            repaint();
        } // end actionPerformed()
    } // end CollisionListener class

} // end class
