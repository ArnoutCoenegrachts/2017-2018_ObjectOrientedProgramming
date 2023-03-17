package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * An abstract class of GameObjects involving worlds, coordinates, radii and mass
 * 
 * @author Arnout Coenegrachts
 */
public abstract class GameObject {

	/**
	 * Return the world in which this GameObject currently exists
	 */
	public World getWorld() {
		return this.world;
	}
	/**
	 * Set the world of this GameObject to the given world
	 * @param 	world
	 * 			The world for this new GameObject
	 * @post 	This GameObject now exists within the given world
	 *			| new.getWorld() = world
	 */
	void setWorld(World world) throws InvalidArgumentException{
		this.world = world;
	}
	/**
	 * Remove the world from this GameObject
	 * @post		this GameObject doesn't exist within a world anymore
	 */
	void removeWorld() {
		this.world = null;
	}
	/**
	 * a variable registering the world in which this GameObject exists
	 */
	private World world;
	
	/**
	 * Return a double-array with the current x-coordinate of this worm in the first position and the
	 * 	current y-coordinate of this worm in the second position
	 */
	public double[] getCoordinates() {
		return new double[] {getX(), getY()};
	}
	/**
	 * Set the coordinates of this GameObject to the given x- and y-coordinate
	 * @param 	newX
	 * 			The new x-coordinate of this GameObject
	 * @param 	newY
	 * 			The new y-coordinate of this GameObject
	 * @post		The coordinates of this GameObject are equal to the given coordinates
	 * 			| new.getCoordinates() = {newX, newY}
	 */
	public void setCoordinates(double newX, double newY) throws InvalidArgumentException {
		this.x = newX;
		this.y = newY;
	}
	/**
	 * Get the x-coordinate of this GameObject
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * A variable registering the x-coordinate of this GameObject
	 */
	private double x;
	/**
	 * Get the y-coordinate of this GameObject
	 */
	public double getY() {
		return this.y;
	}
	/**
	 * A variable registering the y-coordinate of this GameObject
	 */
	private double y;
	
	/**
	 * Set the radius of this GameObject to the given value
	 * @param 	radius
	 * 			The new radius of this GameObject
	 * @post		The radius of this GameObject is equal to the given value
	 * 			| new.getRadius() = radius
	 */
	public void setRadius(double radius) throws InvalidArgumentException {
		this.radius = radius;
	}
	/**
	 * Return the current radius of this worm
	 */
	public double getRadius() {
		return this.radius;
	}
	/**
	 * Variable registering the current radius of the worm in meters
	 */
	private double radius;
	
	/**
	 * Terminate this GameObject
	 * @post		this GameObject is terminated 
	 */
	public void terminate() {
		this.isTerminated = true;
	}
	/**
	 * check if this GameObject has been terminated
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	/**
	 * A boolean variable registering whether this GameObject is terminated or not
	 */
	private boolean isTerminated;
	
	/**
	 * Set the mass of the GameObject
	 * @param 	density
	 * 			The density of this GameObject
	 * @post		| getMass() = density * 4/3 * Math.PI * Math.pow(this.getRadius(), 3)
	 */
	public void setMass(double density) {
		this.mass = density * 4/3 * Math.PI * Math.pow(this.getRadius(), 3);
	}
	/**
	 * Return the mass of this worm
	 */
	public double getMass() {
		return this.mass;
	}
	/**
	 * Variable registering the current mass of the worm in kilograms
	 */
	private double mass;
	
	
	
	
}
