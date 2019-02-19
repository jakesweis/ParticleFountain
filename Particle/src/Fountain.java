
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: ParticleFountain
// Files: Fountain.java
// Course: CS 300, Spring, 2019
//
// Author: Jacob Sweis
// Email: jdsweis@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Galen Quinn
// Partner Email: gaquinn@wisc.edu
// Partner Lecturer's Name: Gary Dahl
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// X Write-up states that pair programming is allowed for this assignment.
// X We have both read and understand the course Pair Programming Policy.
// X We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.Scanner;
import java.util.Random;

/**
 * This class contains the methods that run the particle fountain.
 * 
 * @author Jacob Sweis
 * @author Galen Quinn
 *
 */
public class Fountain {

    /**
     * Creates a single particle at position (3,3) with velocity (-1,-2). Then checks whether
     * calling updateParticle() on this particle's index correctly results in changing its position
     * to (2,1.3).
     * 
     * @return true when no defect is found, and false otherwise
     */
    private static boolean testUpdateParticle() {

        particles = new Particle[1];
        particles[0] = new Particle();

        particles[0].setPositionX(3.0f);
        particles[0].setPositionY(3.0f);
        particles[0].setVelocityX(-1);
        particles[0].setVelocityY(-2);

        updateParticle(0);

        if (particles[0].getPositionX() != 2 && particles[0].getPositionY() != 1.3) { // checks if
                                                                                      // position
                                                                                      // was
                                                                                      // correctly
                                                                                      // changed
            return false;
        }

        return true;
    }

    /**
     * Calls removeOldParticles(6) on an array with three particles (two of which have ages over six
     * and another that does not). Then checks whether the old particles were removed and the young
     * particle was left alone.
     * 
     * @return true when no defect is found, and false otherwise
     */
    private static boolean testRemoveOldParticles() {

        particles = new Particle[3];
        particles[0] = new Particle();
        particles[1] = new Particle();
        particles[2] = new Particle();


        particles[0].setAge(7);// old particle
        particles[1].setAge(9);// old particle
        particles[2].setAge(4);// young particle

        removeOldParticles(6);

        if (particles[0] != null || particles[1] != null || particles[2] == null) { // checks if old
                                                                                    // particles
                                                                                    // were removed
                                                                                    // and the young
                                                                                    // particle was
                                                                                    // left alone
            return false;
        }

        return true;
    }

    private static Random randGen; // random generator to randomize variables
    private static Particle[] particles; // particle array containing all of the particles
    private static int positionX; // middle of the screen (left to right): 400
    private static int positionY; // middle of the screen (top to bottom): 300
    private static int startColor; // blue: Utility.color(23,141,235)
    private static int endColor; // lighter blue: Utility.color(23,200,255)

    /**
     * This method is the constructor for the Fountain class.
     */
    public static void setup() {
        if (false == testUpdateParticle()) {
            System.out.println("FAILED");
        }

        if (false == testRemoveOldParticles()) {
            System.out.println("FAILED");
        }



        randGen = new Random(); // field initialized to new Random object

        particles = new Particle[800]; // particle array initialized with 800 indices

        positionX = 400;
        positionY = 300;
        startColor = Utility.color(23, 141, 235);
        endColor = Utility.color(23, 200, 255);

    }

    /**
     * This method should do all of the moving, accelerating, and drawing of a particle.
     *
     * @param index particle index
     */
    private static void updateParticle(int index) {
        Utility.circle(particles[index].getPositionX(), particles[index].getPositionY(),
            particles[index].getSize());
        Utility.fill(Utility.color(23, 141, 235), 255);
        particles[index].setVelocityY(particles[index].getVelocityY() + 0.3f);
        particles[index]
            .setPositionX(particles[index].getPositionX() + particles[index].getVelocityX());
        particles[index]
            .setPositionY(particles[index].getPositionY() + particles[index].getVelocityY());
        particles[index].setAge(particles[index].getAge() + 1);
    }

    /**
     * This method creates an int number of particles.
     *
     * @param numParticles number of particles to be created
     */
    private static void createNewParticles(int numParticles) {

        for (int i = 0; i < particles.length; i++) { // iterates through all indexes
            if (numParticles > 0) { // checks if there are still more particles to create
                if (particles[i] == null) { // checks if the index is valid or if a particle needs
                                            // to be created
                    particles[i] = new Particle(); // change to reference newly modified particles
                    particles[i].setPositionX(
                        Fountain.positionX + randGen.nextFloat() * (0.3f + 0.3f) - 0.3f);
                    particles[i].setPositionY(
                        Fountain.positionY + randGen.nextFloat() * (0.3f + 0.3f) - 0.3f);
                    particles[i].setSize(randGen.nextFloat() * (11f - 4f) + 4f);
                    particles[i].setColor(Utility.lerpColor(Fountain.startColor, Fountain.endColor,
                        randGen.nextFloat() * 1.0f));
                    particles[i].setVelocityX(randGen.nextFloat() * (1 + 1) - 1);
                    particles[i].setVelocityY(randGen.nextFloat() * (-5 + 10) - 10);
                    particles[i].setAge(randGen.nextInt(41));
                    particles[i].setTransparency(randGen.nextInt() * (127 - 32) + 32);
                    numParticles--;
                }
            }
        }
    }

    /**
     * This method is to search through the particles array for references to particles with an age
     * that is greater than the specified max age. The references to any such particles should be
     * replaced with null references in your particles array by this method.
     *
     * @param maxAge maximum age the particles can be before being removed
     */
    private static void removeOldParticles(int maxAge) {
        for (int i = 0; i < particles.length; i++) { // iterates through all indexes
            if (particles[i] != null) { // checks if particle exists
                if (particles[i].getAge() > maxAge) { // checks if particles age is over maxAge
                    particles[i] = null;
                }
            }
        }
    }

    /**
     * This method increments the x position by the x velocity, and increments the y position by the
     * y velocity.
     */
    public static void update() {

        Utility.background(Utility.color(235, 213, 186));
        createNewParticles(10);

        for (int i = 0; i < particles.length; i++) { // iterates through all indexes
            if (particles[i] != null) { // checks if particle exists
                updateParticle(i);
            }
        }
        removeOldParticles(80);
    }

    /**
     * This method moves the Fountain.positionX and Fountain.positionY to match the position of the
     * mouse whenever the mouse button is pressed.
     *
     * @param xPosMouse x coordinate of the mouse at the time it was clicked
     * @param yPosMouse y coordinate of the mouse at the time it was clicked
     */
    public static void mousePressed(int xPosMouse, int yPosMouse) {
        Fountain.positionX = xPosMouse;
        Fountain.positionY = yPosMouse;
    }

    /**
     * This method calls Utility.save(“screenshot.png”) whenever the key pressed happened to be the
     * ‘p’ key.
     *
     * @param charPressed key pressed on keyboard
     */
    public static void keyPressed(char charPressed) {
        if (charPressed == 'p') { // checks if user pressed 'p' key
            Utility.save("screenshot.png");
        }
    }

    /**
     * This is the main method for the Fountain class.
     *
     * @param args
     */
    public static void main(String[] args) {
        Utility.runApplication();
    }

}
