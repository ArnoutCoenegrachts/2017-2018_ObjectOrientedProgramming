package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of food located in a world
 * 
 * @invar the radius of every worm is 0.2 m
 * @invar the density of every worm is 150 kg/m^3
 * @author Arnout Coenegrachts
 */
public class Food extends GameObject{
	/**
	 * Initialize a new worm with given x- and y-coordinates in the given world
	 * @param 	world
	 * 			The world in which this new food is located
	 * @param 	x
	 * 			The x-coordinate of this new food
	 * @param 	y
	 * 			The y-coordinate of this new food
	 * @throws 	InvalidArgumentException
	 * 			This food doesn't have a valid location within its world
	 * 			| world.hasValidLocation(x, y, radius) == false
	 * @throws	InvalidArgumentException
	 * 			The given coordinates aren't valid if there is no world
	 * 			| Double.isNaN(xCoordinate) == true || Double.isNaN(yCoordinate) == true
	 * 
	 */
	public Food(World world, double x, double y) throws InvalidArgumentException{
		setMass(density);
		setWorld(world, x, y, getRadius());
		setCoordinates(x,y);
		this.healthy = true;
	}
	
	/**
	 * Set the coordinates of this food to the given values
	 * @param 	xCoordinate
	 * 			The new x coordinate of this food
	 * @param 	yCoordinate
	 * 			The new y coordinate of this food
	 * @post		If this food is located in a world and the given coordinates are valid ones, then the x- and y-
	 * 			coordinates of this food are equal to the given values
	 * 			| if (getWorld() != null && getWorld().hasValidCoordinates(xCoordinate, yCoordinate, getRadius() == true)
	 * 			|	then new.getCoordinates() = {xCoordinate, yCoordinate}
	 * @post		If this food isn't located in a world and the given coordinates are numbers, then the x- and y-
	 * 			coordinates of this food are equal to the given values
	 * 			| if (getWorld() == null && Double.isNan(xCoordinate) == false && Double.isNaN(yCoordinate) == false)
	 * 			|	then new.getCoordinates() = {xCoordinate, yCoordinate}
	 * @throws 	InvalidArgumentException
	 * 			When this food is located in a world but the given coordinates aren't valid ones
	 * 			| if (getWorld() != null && getWorld().hasValidCoordinates(xCoordinate, yCoordinate, getRadius() == false)
	 * @throws	InvalidArgumentException
	 * 			When this food isn't located in a world and the given coordinates aren't numbers
	 * 			| if (getWorld() == null && Double.isNan(xCoordinate) == true && Double.isNaN(yCoordinate) == true)
	 */
	@Raw @Override
	public void setCoordinates(double xCoordinate, double yCoordinate) throws InvalidArgumentException{
		if (getWorld() != null) {
			if (getWorld().isValidLocation(xCoordinate, yCoordinate, getRadius()) == true) {
				super.setCoordinates(xCoordinate, yCoordinate);
			}
			else
				throw new InvalidArgumentException("Invalid Coordinates!");
		}
		else {
			if (Double.isNaN(xCoordinate) == false && Double.isNaN(yCoordinate) == false) {
				super.setCoordinates(xCoordinate, yCoordinate);
			}
			else
				throw new InvalidArgumentException("Invalid Coordinates!");
			}
	}
	
	/**
	 * return the radius of any food
	 */
	@Override @Basic
	public double getRadius() {
		return this.radius;
	}
	/**
	 * constant registering the radius of any food
	 */
	private final double radius = 0.2;
	
	/**
	 * terminate this food
	 * @post		This food is terminated
	 * @post		This food doesn't exist within a world anymore
	 */
	@Override
	public void terminate() {
		super.terminate();
		World world = getWorld();
		if (world != null) {
			world.removeFood(this);
			world.removeItem(this);
		}
	}
	
	
	/**
	 * Set the world for this food
	 * @param	world
	 * 			The new world for this food
	 * @param 	x
	 * 			The x-coordinate of this food
	 * @param 	y
	 * 			The y-coordinate of this food
	 * @param 	radius
	 * 			The radius of this food
	 * @post		If this food didn't have a world before, then it exists within the given world. 
	 * 			| if (getWorld() == null)
	 * 			| 	then new getWorld() = world
	 * @throws 	InvalidArgumentException
	 * 			When this food doesn't have an acceptable loction within this world, or when the world or this
	 * 			food is terminated, or if this food already exists within the world
	 * 			| if (isValidLocation(x,y,radius) == true || isTerminated() == false ||
	 * 			|	hasActiveGame() == false || food.isTerminated() == false || hasFood(food) == false)
	 */
	void setWorld(World world, double x, double y, double radius) throws InvalidArgumentException{
		if (getWorld() == null) {
			super.setWorld(world);
			if (world != null && world.hasFood(this) == false)
				world.addFood(this, x, y, radius);
		}
	}
	/**
	 * Remove this food from its world
	 * @post		This food won't be in a world anymore
	 * 			| this.getWorld() = null
	 */
	@Override
	void removeWorld() {
		World world = getWorld();
		if (world.hasFood(this) == true)
			world.removeFood(this);
		super.removeWorld();
	}
	
	/**
	 * Return whether this food is healthy or poisoned
	 */
	public boolean isHealthy() {
		return this.healthy;
	}
	/**
	 * Change the food's status from healthy to poisoned or from poisoned to healthy
	 * @post		if the food used to be healthy, then it is poisoned now
	 * @post		if the food used to be poisoned, then it is healthy now
	 */
	public void changeStatus() {
		if (isHealthy() == true)
			this.healthy = false;
		else 
			this.healthy = true;
	}
	/**
	 * A boolean variable registering whether this food is poisoned or not
	 */
	private boolean healthy;
	/**
	 * A constant registering the density of any food
	 */
	private final double density = 150;
}
