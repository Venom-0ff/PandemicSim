import java.awt.*;

/**
* The {@code Person} class represents a person as a point in a two-dimensional space.
* @author Stepan Kostyukov
*/
public class Person {
    // class members
    private boolean isAlive;
    private boolean isInfected;
    private int immunityStatus;

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

    // TODO:
    // Write necessary constructors.
    //   Randomly assign values from -5 to 5 to xInc and yInc: (int) ((Math.random() * (max - min)) + min).
    
    /**
     * Moves the points inside the {@code JPanel}.
     * @param person a {@code Person} object.
     * @param width width of the {@code JPanel}.
     * @param height height of the {@code JPanel}.
     */
    public static void move(Person person, int width, int height) {
        if (person.getXCoord() >= width - DIAMETER)
		{
			// at right edge, so negate xInc
			person.setXInc(-person.getXInc());
		}
		if (person.getXCoord() <= 0)
		{
			// at left edge, so negate xInc
			person.setXInc(-person.getXInc());
		}
		if (person.getYCoord() >= height - DIAMETER)
		{
			// at bottom edge, so negate yInc
            person.setYInc(-person.getYInc());
		}
		if (person.getYCoord() <= 0)
		{
			// at top edge, so negate yInc
			person.setYInc(-person.getYInc());
		}

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

        if (Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= DIAMETER) {
            // TODO:
            // 1. Change the direction of the movement after the collision.
            // 2. Determine what state variables should be changed and change them.
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

} // end class