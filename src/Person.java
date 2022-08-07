import java.awt.*;
import java.util.Random;

/**
* The {@code Person} class represents a person as a point in a two-dimensional space.
* @author Stepan Kostyukov
*/
public class Person {
    // class members
    private boolean isAlive;
    private boolean isInfected;
    private int immunityStatus;         //  0,   1,    2,    3,    4,    5. NOTE: '0' is only used to create the first infected person.
    private final double[] INFECT_CHANCE = {0, 0.8,  0.6,  0.3,  0.1,  0.4};
    private final double[] DEATH_CHANCE  = {0, 0.1, 0.07, 0.03, 0.01, 0.03};

    private Color colour;

    private int xCoord;
    private int yCoord;
    private int xInc;
    private int yInc;

    private int cycleCount;

    private static final int DIAMETER = 10;     // do we even need it implemented like this?

    
    // 0-arg constructor (Remove if not used)
    public Person() {
        this.isAlive = true;
        this.isInfected = false;
        this.immunityStatus = 0;
        this.colour = Color.BLUE;
        this.xCoord = 0;
        this.yCoord = 0;
        this.xInc = 0;
        this.yInc = 0;
        this.cycleCount = 0;
    }

    /**
     * Creates an instance of the {@code Person} class object.
     * @param immunityStatus
     * @param width width of the {@code JPanel}.
     * @param height height of the {@code JPanel}.
     */
    public Person(int immunityStatus, int width, int height) {
        this.isAlive = true;
        this.isInfected = false;

        this.immunityStatus = immunityStatus;
        switch (this.immunityStatus) {
            case 0:
                this.colour = Color.RED;
                this.isInfected = true;
                this.immunityStatus = 1;
                break;
            case 1:
                this.colour = Color.BLUE;
                break;
            case 2:
                this.colour = Color.CYAN;
                break;
            case 3:
                this.colour = Color.YELLOW;
                break;
            case 4:
                this.colour = Color.MAGENTA;
                break;
            case 5:
            this.colour = Color.GREEN;
        }

        // generate random x and y spawn coordinates
        int spawnX, spawnY;
        do {
            spawnX = (int)(Math.random() * width);
        } while (spawnX < 0 || spawnX > width - DIAMETER);
        this.xCoord = spawnX;

        do {
            spawnY = (int)(Math.random() * height);
        } while (spawnY < 0 || spawnY > height - DIAMETER);
        this.yCoord = spawnY;

        // generate random xInc and yInc values
        do {
            this.xInc = (int) (Math.random() * 10 - 5);
            this.yInc = (int) (Math.random() * 10 - 5);
        } while (this.xInc == 0 && this.yInc == 0);

        this.cycleCount = 0;
    }
    
    /**
     * Moves the points inside the {@code JPanel}.
     * @param person a {@code Person} object.
     * @param width width of the {@code JPanel}.
     * @param height height of the {@code JPanel}.
     */
    public static void move(Person person, int width, int height) {
        // at left edge, so negate xInc
		if (person.getXCoord() <= 0) { person.setXInc(-person.getXInc()); }
        
        // at right edge, so negate xInc
        if (person.getXCoord() >= width - DIAMETER) { person.setXInc(-person.getXInc()); }

        // at top edge, so negate yInc
		if (person.getYCoord() <= 0) { person.setYInc(-person.getYInc()); }
        
        // at bottom edge, so negate yInc
		if (person.getYCoord() >= height - DIAMETER) { person.setYInc(-person.getYInc()); }
        
		// adjust the person position
		person.setXCoord(person.getXCoord() + person.getXInc());
		person.setYCoord(person.getYCoord() + person.getYInc());

    } // end move()

    /**
     * Changes the direction of the points after they collide and changes the state variables of the involved objects.
     * @param person1 first involved {@code Person} object.
     * @param person2 second involved {@code Person} object.
     */
    public static void checkCollision(Person person1, Person person2) {

        int deltaX = person1.getXCoord() - person2.getXCoord();
        int deltaY = person1.getYCoord() - person2.getYCoord();

        // if collision occurs
        if (Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= DIAMETER) {
            // determine if the person is alive
            if (person1.isAlive()) {
                // if the person is not infected yet, then determine if the person will get infected
                if (!person1.isInfected() && person2.isInfected()) {
                    Random rand = new Random();
                    person1.setIsInfected(rand.nextDouble(1.0) <= person1.getINFECT_CHANCE(person1.getImmunityStatus()));

                    if (person1.isInfected()) { person1.setColour(Color.RED); }
                }
                person1.setXInc((int)(Math.random() * 10 - 5));
                person1.setYInc((int)(Math.random() * 10 - 5));
            }
            
            // same for the second person
            if (person2.isAlive()) {
                if (!person2.isInfected() && person1.isInfected()) {
                    Random rand = new Random();
                    person2.setIsInfected(rand.nextDouble(1.0) <= person2.getINFECT_CHANCE(person2.getImmunityStatus()));

                    if (person2.isInfected()) { person2.setColour(Color.RED); }
                }
                person2.setXInc((int)(Math.random() * 10 - 5));
                person2.setYInc((int)(Math.random() * 10 - 5));
            }
        }
    } // end checkCollision()
















    // getters & setters
    public boolean isAlive() {
        return this.isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isInfected() {
        return this.isInfected;
    }

    public void setIsInfected(boolean isInfected) {
        this.isInfected = isInfected;
    }

    public int getImmunityStatus() {
        return this.immunityStatus;
    }

    public void setImmunityStatus(int immunityStatus) {
        this.immunityStatus = immunityStatus;
    }

    public Color getColour() {
        return this.colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public int getXCoord() {
        return this.xCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getYCoord() {
        return this.yCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getXInc() {
        return this.xInc;
    }

    public void setXInc(int xInc) {
        this.xInc = xInc;
    }

    public int getYInc() {
        return this.yInc;
    }

    public void setYInc(int yInc) {
        this.yInc = yInc;
    }

    public int getCycleCount() {
        return this.cycleCount;
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }

    public static int getDiameter() {
        return DIAMETER;
    }

    public double getINFECT_CHANCE(int index) {
        return this.INFECT_CHANCE[index];
    }

    public double getDEATH_CHANCE(int index) {
        return this.DEATH_CHANCE[index];
    }

} // end class