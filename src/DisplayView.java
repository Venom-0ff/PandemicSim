import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
* The {@code DisplayView} class displays GUI, takes and validates user input.
* @author Stepan Kostyukov
* @author Andrew Belmont De Avila
*/
public class DisplayView extends JFrame {
    private Simulation simulation = null;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;

    private JMenuItem startItem;
    private JMenuItem pauseItem;
    private JMenuItem resumeItem;
    private JMenuItem aboutItem;

    private JPanel mainPanel;
    private JPanel butPanel;

    private JSlider population;
    private JSlider oneShotVac;
    private JSlider twoShotsVac;
    private JSlider threeShotsVac;
    private JSlider recovered;

    private JButton startButton;
    private JButton pauseButton;
    private JButton resumeButton;

    private int populationNum = 100;
    private int oneShotPercent = 0;
    private int twoShotsPercent = 0;
    private int threeShotsPercent = 0;
    private int recoveredPercent = 0;

    /**
     * {@code DisplayView} constructor.
     */
    public DisplayView() {
        super("Pandemic Simulator Controls");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(800, 600);
		this.setLocationRelativeTo(null);
        this.setResizable(false);

        Listener listener = new Listener();

        // menu bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu ("File");
        helpMenu = new JMenu ("Help");

        startItem = new JMenuItem ("Start Simulation");
        startItem.addActionListener(listener);
        fileMenu.add(startItem);

        pauseItem = new JMenuItem ("Pause Simulation");
        pauseItem.addActionListener(listener);
        fileMenu.add(pauseItem);

        resumeItem = new JMenuItem ("Resume Simulation");
        resumeItem.addActionListener(listener);
        fileMenu.add(resumeItem);
        
        aboutItem = new JMenuItem ("About");
        aboutItem.addActionListener(listener);
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        // container panel
        mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,2, 0, 20));

        // sliders
        population = new JSlider (100, 1000, 100);
        population.setName("Population");
        population.setOrientation (JSlider.HORIZONTAL);
        population.setMinorTickSpacing (50);
        population.setMajorTickSpacing (100);
        population.setPaintTicks (true);
        population.setPaintLabels (true);
        population.setSnapToTicks(true);
        population.setToolTipText ("Population of the data set.");
        population.addChangeListener(listener);
        mainPanel.add(new JLabel("Population: "));
        mainPanel.add(population);

        oneShotVac = new JSlider (0, 100, 0);
        oneShotVac.setName("One Shot");
        oneShotVac.setOrientation (JSlider.HORIZONTAL);
        oneShotVac.setMinorTickSpacing (5);
        oneShotVac.setMajorTickSpacing (10);
        oneShotVac.setPaintTicks (true);
        oneShotVac.setPaintLabels (true);
        oneShotVac.setSnapToTicks(true);
        oneShotVac.setToolTipText ("Percent of population who recieved one shot of the vaccine.");
        oneShotVac.addChangeListener(listener);
        mainPanel.add(new JLabel("One shot %: "));
        mainPanel.add(oneShotVac);

        twoShotsVac = new JSlider (0, 100, 0);
        twoShotsVac.setName("Two Shots");
        twoShotsVac.setOrientation (JSlider.HORIZONTAL);
        twoShotsVac.setMinorTickSpacing (5);
        twoShotsVac.setMajorTickSpacing (10);
        twoShotsVac.setPaintTicks (true);
        twoShotsVac.setPaintLabels (true);
        twoShotsVac.setSnapToTicks(true);
        twoShotsVac.setToolTipText ("Percent of population who recieved two shots of the vaccine.");
        twoShotsVac.addChangeListener(listener);
        mainPanel.add(new JLabel("Two shots %: "));
        mainPanel.add(twoShotsVac);

        threeShotsVac = new JSlider (0, 100, 0);
        threeShotsVac.setName("Three Shots");
        threeShotsVac.setOrientation (JSlider.HORIZONTAL);
        threeShotsVac.setMinorTickSpacing (5);
        threeShotsVac.setMajorTickSpacing (10);
        threeShotsVac.setPaintTicks (true);
        threeShotsVac.setPaintLabels (true);
        threeShotsVac.setSnapToTicks(true);
        threeShotsVac.setToolTipText ("Percent of population who recieved three shots of the vaccine.");
        threeShotsVac.addChangeListener(listener);
        mainPanel.add(new JLabel("Three shots %: "));
        mainPanel.add(threeShotsVac);

        recovered = new JSlider (0, 100, 0);
        recovered.setName("Recovered");
        recovered.setOrientation (JSlider.HORIZONTAL);
        recovered.setMinorTickSpacing (5);
        recovered.setMajorTickSpacing (10);
        recovered.setPaintTicks (true);
        recovered.setPaintLabels (true);
        recovered.setSnapToTicks(true);
        recovered.setToolTipText ("Percent of population who recovered from the illness.");
        recovered.addChangeListener(listener);
        mainPanel.add(new JLabel("Recovered %: "));
        mainPanel.add(recovered);

        // button container
        butPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start Simulation");
        pauseButton = new JButton("Pause Simulation");
        resumeButton = new JButton("Resume Simulation");
        startButton.addActionListener(listener);
        pauseButton.addActionListener(listener);
        resumeButton.addActionListener(listener);
        butPanel.add(startButton);
        butPanel.add(pauseButton);
        butPanel.add(resumeButton);
        
        // add components
        this.add(menuBar, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(butPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    } // end constructor

    /**
	 * {@code Listener} class that handles and validates user input events.
	 */
    private class Listener implements ActionListener, ChangeListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getActionCommand().equals("Start Simulation")) {
                if (simulation == null) {
                    simulation = new Simulation(populationNum, oneShotPercent, twoShotsPercent, threeShotsPercent, recoveredPercent);
                }
                else if (simulation.isFinished()) { JOptionPane.showMessageDialog(null, "Simulation has already finished!"); }
                else { JOptionPane.showMessageDialog(null, "Simulation is already running!"); }
            }

            if (ev.getActionCommand().equals("Pause Simulation")) {
                if (simulation != null && !simulation.isOnPause() && !simulation.isFinished()) { simulation.Pause(); }
                else if (simulation != null && simulation.isFinished()) { JOptionPane.showMessageDialog(null, "Simulation has already finished!"); }
                else { JOptionPane.showMessageDialog(null, "Simulation is not running!"); }
            }

            if (ev.getActionCommand().equals("Resume Simulation")) {
                if (simulation != null && simulation.isOnPause() && !simulation.isFinished()) { simulation.Resume(); }
                else if (simulation != null && simulation.isFinished()) { JOptionPane.showMessageDialog(null, "Simulation has already finished!"); }
                else if (simulation != null && !simulation.isOnPause()) { JOptionPane.showMessageDialog(null, "Simulation is not on pause!"); }
                else { JOptionPane.showMessageDialog(null, "Simulation is not running!"); }
            }

            if (ev.getActionCommand().equals("About")) {
                JOptionPane.showMessageDialog(null, "This project was made by:\nAndrew Belmont De Avila,\nStepan Kostyukov.");
            }
        } // end actionPerformed

        @Override
        public void stateChanged(ChangeEvent ev) {
            // change in population
            if (ev.getSource() == population) {
                populationNum = population.getValue();
            }

            int sum = oneShotVac.getValue() + twoShotsVac.getValue() + threeShotsVac.getValue() + recovered.getValue();
            // change in percentages: adjust sliders if sum >= 100%
            if (ev.getSource() == oneShotVac) {
                if (sum <= 100) {
                    oneShotPercent = oneShotVac.getValue();
                }
                else {
                    int diff = oneShotVac.getValue() - oneShotPercent;
                    oneShotPercent = oneShotVac.getValue();

                    if (twoShotsPercent >= threeShotsPercent && twoShotsPercent >= recoveredPercent) {
                        twoShotsPercent -= diff;
                        twoShotsVac.setValue(twoShotsPercent);
                    }
                    else if (threeShotsPercent >= twoShotsPercent && threeShotsPercent >= recoveredPercent) {
                        threeShotsPercent -= diff;
                        threeShotsVac.setValue(threeShotsPercent);
                    }
                    else {
                        recoveredPercent -= diff;
                        recovered.setValue(recoveredPercent);
                    }
                }
            }

            if (ev.getSource() == twoShotsVac) {
                if (sum <= 100) {
                    twoShotsPercent = twoShotsVac.getValue();
                }
                else {
                    int diff = twoShotsVac.getValue() - twoShotsPercent;
                    twoShotsPercent = twoShotsVac.getValue();

                    if (oneShotPercent >= threeShotsPercent && oneShotPercent >= recoveredPercent) {
                        oneShotPercent -= diff;
                        oneShotVac.setValue(oneShotPercent);
                    }
                    else if (threeShotsPercent >= oneShotPercent && threeShotsPercent >= recoveredPercent) {
                        threeShotsPercent -= diff;
                        threeShotsVac.setValue(threeShotsPercent);
                    }
                    else {
                        recoveredPercent -= diff;
                        recovered.setValue(recoveredPercent);
                    }
                }
            }

            if (ev.getSource() == threeShotsVac) {
                if (sum <= 100) {
                    threeShotsPercent = threeShotsVac.getValue();
                }
                else {
                    int diff = threeShotsVac.getValue() - threeShotsPercent;
                    threeShotsPercent = threeShotsVac.getValue();

                    if (oneShotPercent >= twoShotsPercent && oneShotPercent >= recoveredPercent) {
                        oneShotPercent -= diff;
                        oneShotVac.setValue(oneShotPercent);
                    }
                    else if (twoShotsPercent >= oneShotPercent && twoShotsPercent >= recoveredPercent) {
                        twoShotsPercent -= diff;
                        twoShotsVac.setValue(twoShotsPercent);
                    }
                    else {
                        recoveredPercent -= diff;
                        recovered.setValue(recoveredPercent);
                    }
                }
            }

            if (ev.getSource() == recovered) {
                if (sum <= 100) {
                    recoveredPercent = recovered.getValue();
                }
                else {
                    int diff = recovered.getValue() - recoveredPercent;
                    recoveredPercent = recovered.getValue();

                    if (oneShotPercent >= twoShotsPercent && oneShotPercent >= threeShotsPercent) {
                        oneShotPercent -= diff;
                        oneShotVac.setValue(oneShotPercent);
                    }
                    else if (twoShotsPercent >= threeShotsPercent && twoShotsPercent >= oneShotPercent) {
                        twoShotsPercent -= diff;
                        twoShotsVac.setValue(twoShotsPercent);
                    }
                    else {
                        threeShotsPercent -= diff;
                        threeShotsVac.setValue(threeShotsPercent);
                    }
                }
            }
        } // end stateChanged
    } // end Listener

} // end class
