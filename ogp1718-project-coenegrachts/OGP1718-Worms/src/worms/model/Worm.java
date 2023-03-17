/*
 * Arnout Coenegrachts
 * Bachelor Fysica
 * link to code repository: /Applications/Eclipse/Workspace
 */




package worms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import be.kuleuven.cs.som.annotate.*;
import worms.internal.gui.game.IActionHandler;
import worms.programs.Program;

/**
 * A class of worms with a name, a location in a 2D-plane given by a x- and y-coordinate, an
 * orientation given by an angle, a radius, a mass which is dependent on the radius, an amount of
 * action points and a maximum amount of action points which is dependent on the mass.
 * @invar	The angle of each worm must be a valid angle for any worm
 * 			| isValidAngle(getAngle()) 
 * @invar	The radius of each worm must be a valid radius for a worm in view of its minimum radius
 * 			| isValidRadius(getRadius(), getMinRadius())
 * @version 1.0
 * @author Arnout Coenegrachts
 */
public class Worm extends GameObject{
	
	/**
	 * Initialize a new worm with given x- and y-coordinates, given angle, given radius and given name
	 * @param 	world
	 * 			the world in which this new worm is located
	 * @param 	x
	 * 			the x-coordinate for this new worm
	 * @param 	y
	 * 			the y-coordinate for this new worm
	 * @param 	angle
	 * 			the angle determining the orientation of this new worm
	 * @param 	radius
	 * 			the angle for this new worm
	 * @param 	name
	 * 			the name of this new worm
	 * @throws 	InvalidArgumentException
	 * 			This worm doesn't have a valid location within its world
	 * 			| world.hasValidLocation(x, y, radius) == false
	 * @throws	InvalidArgumentException
	 * 			The given coordinates aren't valid if there is no world
	 * 			| Double.isNaN(xCoordinate) == true || Double.isNaN(yCoordinate) == true
	 * @throws	InvalidArgumentException
	 * 			The given radius isn't a valid radius
	 * 			| isValidRadius(radius, getMinRadius()) == false
	 * @throws	InvalidArgumentException
	 * 			The given name is not a valid name for any worm
	 * 			| isValidName(name) == false
	 */
	@Raw
	public Worm (World world, double x, double y, double angle, double radius, String name) throws InvalidArgumentException {
		this.minRadius = 0.25;
		setRadius(radius);
		setWorld(world, x, y, getRadius());
		setCoordinates(x,y);
		setAngle(angle);
		setActionPoints(getMaxActionPoints());
		setName(name);
		setInitialHitpoints();
		this.active = false;
		if (getWorld() != null && getWorld().getWorms().size() == 1) {
			activate();
		}
		
		
	}
	
	/**
	 * Set the coordinates of this worm to the given values
	 * @param 	xCoordinate
	 * 			The new x coordinate of this worm
	 * @param 	yCoordinate
	 * 			The new y coordinate of this worm
	 * @post		If this worm is located in a world and the given coordinates are valid ones, then the x- and y-
	 * 			coordinates of this worm are equal to the given values
	 * 			| if (getWorld() != null && getWorld().hasValidCoordinates(xCoordinate, yCoordinate, getRadius() == true)
	 * 			|	then new.getCoordinates() = {xCoordinate, yCoordinate}
	 * @post		If this worm isn't located in a world and the given coordinates are numbers, then the x- and y-
	 * 			coordinates of this worm are equal to the given values
	 * 			| if (getWorld() == null && Double.isNan(xCoordinate) == false && Double.isNaN(yCoordinate) == false)
	 * 			|	then new.getCoordinates() = {xCoordinate, yCoordinate}
	 * @throws 	InvalidArgumentException
	 * 			When this worm is located in a world but the given coordinates aren't valid ones
	 * 			| if (getWorld() != null && getWorld().hasValidCoordinates(xCoordinate, yCoordinate, getRadius() == false)
	 * @throws	InvalidArgumentException
	 * 			When this worm isn't located in a world and the given coordinates aren't numbers
	 * 			| if (getWorld() == null && Double.isNan(xCoordinate) == true && Double.isNaN(yCoordinate) == true)
	 */
	@Raw @Override
	public void setCoordinates(double xCoordinate, double yCoordinate) throws InvalidArgumentException{
		if (getWorld() != null) {
			if (getWorld().hasValidCoordinates(xCoordinate, yCoordinate, getRadius()) == true) {
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
	 * Return the current angle of the worm
	 */
	@Basic
	public double getAngle() {
		return this.angle;
	}
	/**
	 * Set the angle of the worm to the given angle
	 * @param	angle
	 * 			the new angle for this worm
	 * @pre		The given angle must be a valid angle for any worm
	 * 			| isValidAngle(angle)
	 * @post 	The angle of the worm is equal to the given angle
	 * 			| new.getAngle() == angle
	 */
	public void setAngle(double angle) {
		assert isValidAngle(angle);
		this.angle = angle;
	}
	/**
	 * Return an angle equivalent to the given angle which lies between 0 and 2pi (the later 
	 * not included)
	 * @param 	angle
	 * 			The angle whose equivalent should be searched
	 */
	public static double equivalentAngle(double angle) {
		while (angle >= 2*Math.PI)
			angle = angle - 2*Math.PI;
		while (angle < 0)
			angle = angle + 2*Math.PI;
		return angle;
	}
	/**
	 * Check whether the given angle is a valid angle for any worm
	 * @param 	angle
	 * 			the angle to check
	 * @return	True if and only if the angle is between 0 and 2pi (the later not included)
	 * 			| (angle >=0 && angle < 2*Math.PI)
	 */
	public static boolean isValidAngle(double angle) {
		return (angle >= 0 && angle < 2*Math.PI);
	}
	/**
	 * Variable registering the current angle of the worm
	 */
	private double angle;
	
	/**
	 * Set the radius of this worm to the given value
	 * @param 	radius
	 * 			The new radius of this worm
	 * @post		If the given radius is a valid radius, then the radius of this worm has the given value
	 * 			| if (isValidRadius(radius, getMinRadius()) == true)
	 * 			|	then new.getRadius() = radius
	 * @post		The mass of the worm has been adjusted to its new radius
	 * @throws	InvalidArgumentException
	 * 			The given radius isn't a valid radius
	 * 			| isValidRadius(radius, getMinRadius()) == false
	 */
	@Raw @Override
	public void setRadius(double radius) throws InvalidArgumentException{
			if (isValidRadius(radius, getMinRadius()) == true) {
				super.setRadius(radius);
				setMass();
			}
			else
				throw new InvalidArgumentException(radius);	
	}
	/**
	 * Check whether the given radius is a valid radius for any worm with the given minimal radius
	 * @param 	radius
	 * 			The radius to check
	 * @param 	minRadius
	 * 			The minimal radius to check the radius against
	 * @return	True if and only if the radius is equal to or greater than the minimal radius
	 * 			| result == radius >= minRadius
	 */
	public static boolean isValidRadius(double radius, double minRadius) {
		if (radius >= minRadius)
			return true;
		else
			return false;
	}

	/**
	 * Return the minimal radius of this worm
	 */
	@Basic
	public double getMinRadius() {
		return this.minRadius;
	}
	/**
	 * Variable registering the minimal radius of this worm
	 */
	private double minRadius;
	
	/**
	 * Return the current amount of actions of this worm
	 */
	@Basic
	public int getActionPoints() {
		return this.actions;
	}
	/**
	 * Return the maximal amount of actions for this worm
	 */
	public int getMaxActionPoints() {
		return (int) Math.round(getMass());
	}
	/**
	 * Set the amount of actions of the worm to the given amount
	 * @param 	actions
	 * 			The new amount of actions of this worm
	 * @post		If the given amount of actions in the range between 0 and the maximal amount of actions
	 * 			for this worm is, then the new amount of actions is equal to the given amount
	 * 			| if (actions >= 0 && actions <= getMaxActionPoints())
	 * 			|	then new.getActionPoints() == actions
	 * @post		If the given amount of actions is smaller than 0, then the new amount of actions is
	 * 			equal to 0. If this worm is located in a world, the next Worm shall be activated
	 * 			| if (actions < 0) 
	 * 			|	then new.getActionPoints() == 0
	 * 			|	if (getWorld() != null)
	 * 			|		then getWorld().activateNextWorm()
	 * @post		If the given amount of actions exceeds the maximal amount of actions of this worm, than
	 * 			the new amount of actions is equal to the maximal amount of actions
	 * 			| if (actions > getMaxActionPoints())
	 * 			|	then new.getActionPoints() == getMaxActionPoints()
	 */
	public void setActionPoints(int actions) {
		if (actions > 0 && actions <= getMaxActionPoints())
			this.actions = actions;
		else if (actions <= 0) {
			this.actions = 0;
			if (getWorld() != null)
				getWorld().activateNextWorm();
		}
		else if (actions > getMaxActionPoints())
			this.actions = getMaxActionPoints();
	}
	/**
	 * Check if the action which costs the given amount of action points is possible to do
	 * @param 	actions
	 * 			The amount of action points this action costs
	 * @return	True if and only if the given amount of action points is smaller or equal to the
	 * 			current amount of actions
	 * 			| getActionPoints() >= actions
	 */
	public boolean isValidAction (int actions) {
		if (getActionPoints() >= actions && actions >= 0)
			return true;
		else 
			return false;
	}
	/**
	 * Variable registering the current amount of actions the worm
	 */
	private int actions;
	
	/**
	 * Return the current name of this worm
	 */
	@Basic
	public String getName() {
		return this.name;
	}
	/**
	 * Set the name of this worm to the given name
	 * @param 	name
	 * 			The new name of this worm
	 * @post		If the given name is a valid name, then the name of this worm is equal
	 * 			to the given name
	 * 			| if (isValidName(name) == true)
	 * 			|	then this.name = name
	 * @throws	InvalidArgumentException
	 * 			The given name is not a valid name for any worm
	 * 			| isValidName(name) == false
	 */
	public void setName(String name) throws InvalidArgumentException{
		if (isValidName(name) == true) //
					this.name = name;
		else
			throw new InvalidArgumentException(name);}
	/**
	 * Check whether the given name is a valid name for a worm
	 * @param 	name
	 * 			The name to check
	 * @return	True if and only if the given name is at least 2 characters long , starts
	 * 			with an uppercase letter and only consists of upper- and lowercase letters,
	 * 			numbers, whitespaces and quotes.
	 * 			| name.length() >= 2 && hasValidCharacters(name)
	 */
	public static boolean isValidName(String name) {
		if (name.length() >= 2 && hasValidCharacters(name))
			return true;
		else
			return false;
	}
	/**
	 * Check wheter the given name consists of valid characters.
	 * @param 	name
	 * 			The name to check
	 * @return	True if and only if the first character is an uppercase letter and if the
	 * 			other characters are upper- and lowercase letters, numbers, whitespaces and quotes.
	 * 			| isValidFirstCharacter(nameArray[0]) && isValidCharacter(nameArray[i])
	 * 			|			with (1 <= i < name.length())
	 */
	public static boolean hasValidCharacters(String name) {
		String[] nameArray = name.split("");
		for (int i = 0; i < nameArray.length; i++) {
			if (i == 0) {
				if (isValidFirstCharacter(nameArray[0]) == false)
					return false;
			}
			else
				if (isValidCharacter(nameArray[i]) == false) {
					return false;
				}
		}
		return true;
	}
	/**
	 * Check whether the given character is a valid character
	 * @param 	character
	 * 			The character to check
	 * @return	True if and only if the given character is an upper- or lowercase letter,
	 * 			number, whitespace or quote
	 * 			| character.matches(ValidCharacters)
	 */
	public static boolean isValidCharacter(String character) {
		if (character.matches(ValidCharacters))
			return true;
		else
			return false;
	}
	/**
	 * Check whether the given character is a valid first character
	 * @param 	character
	 * 			The character to check
	 * @return	True if and only if the given character is an uppercase letter
	 * 			| character.matches(upperLetters)
	 */
	public static boolean isValidFirstCharacter(String character) {
		if (character.matches(upperLetters))
			return true;
		else
			return false;
	}
	/**
	 * Constant registering all valid characters for a name of a worm
	 */
	private static String ValidCharacters = "[A-Za-z0-9,\\s\"\'_]";
	/**
	 * Constant registering all uppercase letters
	 */
	private static String upperLetters = "[A-Z]";
	/**
	 * Variable registering the current name of the worm
	 */
	private String name;
	
	/**
	 * Constant registering the massdensity of any worm in kilograms per cubicmeter
	 */
	public final double p = 1062;
	
	/**
	 * Set the mass of this worm
	 */
	public void setMass() {
		super.setMass(this.p);
	}
	
	/**
	 * Return the distance between the centre of this worm and the given coordinates
	 * @param 	x
	 * 			The x coordinate of the point of which the distance will be calculated
	 * @param 	y
	 * 			The y coordinate of the point of which the distance will be caluclated
	 * @return	The distance between the centre of this worm and the given coordinates
	 * 			| d = Math.sqrt( Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2) )
	 */
	public double distanceTo(double x, double y) {
		double d = Math.sqrt( Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2) );
		return d;
	}
	
	/**
	 * Return the distance between the centre of this worm and the given coordinates
	 * @param 	coordinates
	 * 			The set of coordinates of the points of which the distance will be calculated
	 * @return	The distance between the centre of this worm and the given coordinates
	 * 			| d = Math.sqrt( Math.pow(coordinates[0] - getX(), 2) + Math.pow(coordinates[1] - getY(), 2) )
	 */
	public double distanceTo(double[] coordinates) {
		return distanceTo(coordinates[0], coordinates[1]);
	}
	
	/**
	 * Move the worm in its current direction
	 * @throws 	InvalidArgumentException
	 * 			When the the worm cannot move due because it doesn't have any actionpoints left or because
	 * 			it has nowhere to go
	 * 			| if (isValidMove(possibleNewLocation) == false || isValidAction(cost) == false-
	 * 			|	then throw InvalidArgumentException
	 * @post		The worm will have moved for a distances equal to or smaller than its own radius and larger
	 * 			than or equal to 0.1.
	 */
	public void move()  throws InvalidArgumentException {
		double angle = getAngle();
		double r = getRadius();
		double[] possibleNewLocation = getFurthestLocationInDirection(angle, r);
		int cost = move_ActionCost(possibleNewLocation[0], possibleNewLocation[1], angle);
		if (isValidMove(possibleNewLocation) == true && isValidAction(cost) == true) {
			setCoordinates(possibleNewLocation[0], possibleNewLocation[1]);
			MoveCombat();
			setActionPoints(getActionPoints() - cost);
		}
		else {
			double[] position = this.divergence();
			double[] possibleLocation = new double[] {position[0], position[1]};
			double newOrientation = position[2];
			cost = move_ActionCost(possibleLocation[0], possibleLocation[1], newOrientation);
			if (isValidMove(possibleLocation) == true && isValidAction(cost) == true) {
				setCoordinates(possibleLocation[0], possibleLocation[1]);
				MoveCombat();
				setActionPoints(getActionPoints() - cost);
				}
			else
				throw new InvalidArgumentException("Invalid Move");
		}
	}
	/**
	 * Get the furthest location where this worm can stand in the given direction when moving
	 * @param 	angle
	 * 			The angle that indicates the direction in which the furthest location is requested 
	 * @param 	maxDistance
	 * 			The maximal value of the the distance between the worms current position and the furthest 
	 * 			location
	 * @return	A set of coordinates representing the furthest location in the given direction
	 * 			| return CoordinatesFurtestLocation
	 * @return 	The current location of the worm when the worm cannot move in the given direction
	 * 			| if (ListPossibleLocations.size() == 0)
	 * 			|	return this.getCoordinates()
	 */
	public double[] getFurthestLocationInDirection(double angle, double maxDistance) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		double r = getRadius();
		World world = getWorld();
		List<double[]> Coordinates = new ArrayList<double[]>();
		for (double d = 0.1; d <= maxDistance; d = d + 0.05) {
			double oplX = getX() + d*cos;
			double oplY = getY() + d*sin;
			Coordinates.add(new double[] {oplX, oplY});
		}
		for (int i = Coordinates.size()-1; i>=0; i = i - 1) {
			double[] C = Coordinates.get(i);
			if (world.isAdjacent(C[0], C[1], r) == true) break;
			else Coordinates.remove(i);
		}
		int l = Coordinates.size();
		if (l > 0) {
			double[] opl = Coordinates.get(l - 1);
			return opl;
		}
		else
			return getCoordinates();
	}
	/**
	 * Get the location for which the distance of the furthest possible area divided by the difference in 
	 * angle, called the divergence,  is maximal
	 * @return	The coordinates of this location
	 * 			| ratio = distance/divergence
	 * 			|	return max(ratio).position
	 */
	public double[] divergence()   {
		double angle = this.getAngle();
		List<Double> ratios = new ArrayList<Double>();
		List<double[]> solution = new ArrayList<double[]>();
		for (double s = angle-0.7875 ; s <= angle+0.7875; s = s + 0.0175) {
			if (s != angle) {
				double[] testLocation = getFurthestLocationInDirection(s, getRadius());
				double distance = distanceTo(testLocation);
				double divergence = Math.abs(s-angle);
				double ratio = distance/divergence;
				double[] position = {testLocation[0], testLocation[1], s};
				ratios.add(ratio);
				solution.add(position);
			}
		}
		int index = ratios.indexOf(Collections.max(ratios));
		return solution.get(index);
	}
	/**
	 * Get the amount of action points moving the worm to this location in this direction costs
	 * @param 	x
	 * 			The x-coordinate of the location towards which the worm might move
	 * @param 	y
	 * 			The y-coordinate of the location towards which the worm might move
	 * @param 	angle
	 * 			The direction in which the worm moves
	 * @return	The amount of action points this move costs
	 * 			| return (int) distance*(Math.abs(Math.cos(angle)) + Math.abs(4*Math.sin(angle)))
	 */
	public int move_ActionCost(double x, double y, double angle) {
		double distance = distanceTo(x,y);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double cost = distance*(Math.round( Math.abs(cos) + Math.abs(4*sin) ) );
		return((int) Math.ceil(cost));
	}
	/**
	 * Check whether moving towards the given destination is allowed
	 * @param 	destination
	 * 			The coordinates of the destination towards the worm might move
	 * @return	True if the destination is adjacent to impassible terrain and the distance to the destination is
	 * 			equal to or larger than 0.1
	 * 			| if (world.isAdjacent(destination) == true && distanceTo(destination)- >= 0.1)
	 * 			|	then return true
	 * @return	False if the destination isn't adjacent to impassible terrain or the distance to the destination
	 * 			is smaller than 0.1
	 * 			| if (world.isAdjacent(destination) == false || distanceTo(destination)- < 0.1)
	 * 			|	then return false
	 */
	public boolean isValidMove(double[] destination) {
		if (getWorld().isAdjacent(destination[0], destination[1], getRadius()) == true && distanceTo(destination) >= 0.1)
			return true;
		else
			return false;
	}
	/**
	 * Search worms to combat with after moving
	 */
	public void MoveCombat() {
		List<Worm> Worms = getWorld().getWorms();
		double r = getRadius();
		for (Worm worm : Worms) {
			if (worm != this) {
				double Ri = worm.getRadius();
				if (this.distanceTo(worm.getX(), worm.getY()) < r + Ri) {
					if (Ri < r) {
						MoveDamage(this, worm);
					}
					else {
						MoveDamage(worm, this);
					}
				}
			}
		}
	}
	/**
	 * Fight between two worms when they overlap after one of the moves. Both worms will take damage and lose
	 * hitpoints
	 * @param 	large
	 * 			The worm with the largest radius
	 * @param 	small
	 * 			The worm with the smallest radius
	 * @post		The small worm will have lost an amount of hitpoints equal to a random integer N between
	 * 			1 and 10, multiplied by the sum of both radii divided by the large radius
	 * 			| small.lowerHitpoints(N*(R_large + R_small)/R_large))
	 * @post		The large worm will have lost an amount of hitpoints equal the the same integer N minus the
	 * 			amount of hitpoints the small worm lost
	 * 			| large.lowerHitpoints(N - N*(R_large + R_small)/R_large))
	 */
	public void MoveDamage(Worm large, Worm small) {
		Random rand = new Random();
		double Rl = large.getRadius();
		double Rs = small.getRadius();
		int N = rand.nextInt(10) + 1;
		int smallDamage = (int) Math.ceil(N*(Rl+Rs)/Rl);
		small.lowerHitpoints(smallDamage);
		large.lowerHitpoints(N - smallDamage);
	}
	
	/**
	 * Turn the worm with the given angle
	 * @param 	addAngle
	 * 			The angle that will be added to the current angle of the worm
	 * @pre		The sum of the current angle of the worm and the given angle must be a valid angle
	 * 			| isValidAngle(getAngle() + addAngle)
	 * @pre		The amount of action points this is going to cost cannot be more than the current
	 * 			amount of action points
	 * 			| isValidAction(ActionCost)
	 * @post		The worm's orientation is the sum of the previous angle and the given angle
	 * 			| new.getAngle() = this.angle + addAngle
	 * @post		The worm's amount of action points is equal to the previous amount of action points 
	 * 			minus the amount of action points the given angle costs
	 * 			| new.getActionPoints() = this.actions - ActionCost
	 */
	public void turn(double addAngle) {
		double newAngle = getAngle() + addAngle;
		newAngle = equivalentAngle(newAngle);
		int ActionCost = (int)(Math.round( Math.abs(addAngle) / ( 2 * Math.PI ) * 60) );
		if (isValidAction(ActionCost) && isValidAngle(newAngle) == true) {
			setAngle(newAngle);
			setActionPoints(getActionPoints() - ActionCost );
		}
	}
	
	/**
	 * Let this worm jump in small steps in the current direction of the worm.
	 * @param 	timeStep
	 * 			The amount of time between each step of the jump
	 * @post		The worm will have a new location adjacent to impassable terrain or beyond the bounds of the
	 * 			world. 
	 * @post		If the worm overlaps with another worm afther the jump, they will fight 
	 * @throws 	InvalidArgumentException
	 * 			When this worm cannot jump
	 * 			| if (isValidJump() == false)
	 * 			|	then throw InvalidArgumentException
	 */
	public void jump(double timeStep) throws InvalidArgumentException {
		if (isValidJump() == true) {
			List<double[]> path = getJumpPath();//jumpPath(timeStep);
			setActionPoints(0);
			int n = path.size() - 1;
			double xNew = path.get(n)[0];
			double yNew = path.get(n)[1];
			if (getWorld().hasValidCoordinates(xNew, yNew, getRadius()) == true) {
				setCoordinates(xNew, yNew);
				jumpCombat();
			}
			else
				getWorld().removeWorm(this);
		}
		else
			throw new InvalidArgumentException("Invalid Jump");
	}
	/**
	 * Calculate the path of a possible jump of the worm in small steps
	 * @param 	deltaT
	 * 			The amount of time between each step of the jump
	 * @return	A list of coordinates representing the path the worm will take when it jumps. Every set of
	 * 			coordinates representes a passable area of the world and the final set of coordinates is either
	 * 			adjacent to impassable terrain or is beyond the boundaries of the world
	 * 			| return List<double[]> path
	 * @throws 	InvalidArgumentException
	 * 			When the terrain of a step isn't passable and the previous step wasn't adjacent to impassable
	 * 			terrain
	 * 			| if(world.isPassable(xAtTime, yAtTime) == false && world.isAdjacent(path.get(path.size()-1)) == false
	 * 			|	then throw InvalidArgumentException
	 */
	public List<double[]> jumpPath(double deltaT) throws InvalidArgumentException{
		World world = getWorld();
		double r = getRadius();
		double initVx = getInitialVelocity() * Math.cos(getAngle());
		double initVy = getInitialVelocity() * Math.sin(getAngle());
		List<double[]> path = new ArrayList<double[]>();
		for (double time = 0; time >= 0; time = time + deltaT) {
			double xAtTime = getX() + (initVx * time);
			double yAtTime = getY() + (initVy * time - 0.5 * g * (time * time));
			if (world.isPassable(xAtTime, yAtTime, r) == true) {
				path.add(new double[] {xAtTime, yAtTime});
				if (world.hasValidCoordinates(xAtTime, yAtTime, getRadius()) == false) {
					break;
				}
				else if (world.isAdjacent(xAtTime, yAtTime, r) == true && distanceTo(xAtTime, yAtTime) > r) {
					break;
				}
			}
			else throw new InvalidArgumentException("Invalid Jump");
		} 
		return path;
	}
	/**
	 * Get the amount of time the jump will take
	 * @param 	deltaT
	 * 			The amount of time between each step
	 * @return	The amount of time the jump will take
	 * 			| (path.size() - 1) * deltaT
	 * @throws 	InvalidArgumentException
	 * 			When the jumpPath of this jump throws an InvalidArgumentException
	 */
	public double jumpTime(double deltaT) throws InvalidArgumentException {
		List<double[]> path = jumpPath(deltaT);
		this.jumpPath = path;
		int n = path.size() - 1;
		return n * deltaT;
	}
	/**
	 * Get the current jump path of this worm
	 * @return	the jump path of this worm
	 */
	public List<double[]> getJumpPath() {
		return this.jumpPath;
	}
	/**
	 * The jump path of this worm
	 */
	private List<double[]> jumpPath;
 	/**
 	 * Get the position of this worm during the jump at the given time
 	 * @param 	time
 	 * 			The amount of time that has passed since the jump started for which the position has to be
 	 * 			caluclated
 	 * @return	The coordinates of the worm during the jump at the given time
 	 * @throws 	InvalidArgumentException
 	 * 			When the amount of time is negative, if the jump isn't allowed or if the position at the 
 	 * 			given time isn't a valid one
 	 * 			| if (time < 0 || isValidJump() == false || world.isPassable(xAtTime, yAtTime, radius) == false)
 	 * 			|	then throw InvalidArgumentException
 	 */
	public double[] jumpStep(double time) throws InvalidArgumentException {
		if (time >= 0 && isValidJump() == true) {
			World world = getWorld();
			double r = getRadius();
			double initVx = getInitialVelocity() * Math.cos(getAngle());
			double initVy = getInitialVelocity() * Math.sin(getAngle());
			double xAtTime = getX() + (initVx * time);
			double yAtTime = getY() + (initVy * time - 0.5 * g * (time * time));
			if (world.isPassable(xAtTime, yAtTime, r) == true) {
				return new double[] {xAtTime, yAtTime};
			}
			else
				throw new InvalidArgumentException("Non passable terrain");
		}
		else {
			throw new InvalidArgumentException("Invalid Time");	
		}
	}
	/**
	 * Search worms to combat after jumping
	 */
	public void jumpCombat() {
		List<Worm> Worms = getWorld().getWorms();
		double r = getRadius();
		Random rand = new Random();
		for (Worm worm : Worms) {
			if (worm != this) {
				double Ri = worm.getRadius();
				if (this.distanceTo(worm.getX(), worm.getY()) < r + Ri) {
					int coin = rand.nextInt(2);
					if (coin == 0)
						jumpDamage(this, worm);
					if (coin == 1)
						jumpDamage(worm, this);
				}
			}
		}
	}
	/**
	 * Fight between two worms when they overlap after one of them jumps. One of the worms will deal damage 
	 * while the other takes it. Which worm is which has been decided randomly in jumpCombat()
	 * @param 	attacker
	 * 			The worm that will damage the other worm
	 * @param 	defender
	 * 			The worm that will be taking damage from the other worm
	 * @post		The defender will have lost an amount of hitpoints equal to a random integer between 1 and
	 * 			10 times the ratio of the attackers radius and the defenders radius
	 * 			| defender.lowerHitpoints(rand.nextInt(10 + R_attacker / R_defender) + 1)
	 */
	public void jumpDamage(Worm attacker, Worm defender) {
		double Ra = attacker.getRadius();
		double Rd = defender.getRadius();
		Random rand = new Random();
		int N = (int) Math.ceil(10 * Ra / Rd);
		int damage = rand.nextInt(N) + 1;
		defender.lowerHitpoints(damage);
	}	
	/**
	 * Check whether the worm is able to jump or not
	 * @return	True if and only if the angle of the worm is between zero and pi radians and the amount
	 * 			of action points the worm currently has is larger than zero
	 * 			| angle >= 0 && angle <= Math.PI && getActionPoints() > 0
	 */
	public boolean isValidJump() {
		double angle = this.getAngle();
		if (angle >= 0 && angle <= Math.PI && this.getActionPoints() > 0)
			return true;
		else
			return false;
	}
	/**
	 * Return the force this worm will exert on itself when jumping with the current amount of action
	 * points and its current mass
	 */
	public double getForce() {
		return( (5 * getActionPoints()) + (getMass() * g) );
	}
	/**
	 * Return the initial velocity of this worm if it would jump with the current amount of action points
	 * and current mass
	 */
	public double getInitialVelocity() {
		return( getForce() / getMass() * 0.5 );
	}
	/**
	 * Constant registering the standard acceleration in meters per seconds squared
	 */
	private final double g = 5;
	
	/**
	 * Return the current amount of hitpoints of this worm
	 */
	@Basic
	public int getHitpoints() {
		return(this.hitpoints);
	}
	/**
	 * Set the amount of hitpoints of this worm to the given amount of hitpoints
	 * @param 	hitpoints
	 * 			The new amount of hitpoints of this worm
	 * @post		If the given amount of hitpoints is larger than 0, than the amount of hitpoints of this worm
	 * 			is equal to the given amount
	 * 			| if (hitpoints > 0)
	 * 			|	then new.getHitpoints() = hitpoints
	 * @post		If the given amount of hitpoints is equal to or smaller than 0, the worm is terminated()
	 * 			| if (hitpoints <= 0)
	 * 			|	this.terminate();
	 */
	public void setHitpoints(int hitpoints) {
		if (hitpoints > 0)
			this.hitpoints = hitpoints;
		else if (hitpoints <= 0)
			terminate();
	}
	/**
	 * Lower the amount of hitpoints of this worm with the given amount of damage
	 * @param 	damage
	 * 			The amount of hitpoints this worm loses
	 * @post		The amount of hitpoints of this worm will be equal to its current amount minus the given
	 * 			amount of damage
	 * 			| new.getHitpoints() = getHitpoints() - damage
	 */
	public void lowerHitpoints(int damage) {
		setHitpoints(getHitpoints() - damage);
	}
	/**
	 * Increase the amount of hitpoints of this worm with the given amount
	 * @param 	healing
	 * 			The amount of hitpoints the worm will earn
	 * @post		The amount of hitpoints of this worm will be equal to the original amount plus the given 
	 * 			amount of healing
	 * 			| new.getHitpoints() = getHitpoints() + healing
	 */
	public void increaseHitpoints(int healing) {
		setHitpoints(getHitpoints() + healing);
	}
	/**
	 * Set the initial hitpoints of this worm
	 * @post		the amount of hitpoints of this worm will be equal to a random integer between 1000 and 2000
	 */
	public void setInitialHitpoints() {
		Random rand = new Random();
		int HP = rand.nextInt(1001) + 1000;
		if (HP >= 1000 && HP <= 2000)
			this.hitpoints = HP;
		if (HP < 1000)
			this.hitpoints = 1000;
		if (HP > 2000)
			this.hitpoints = 2000;
	}
	/**
	 * Variable registereing the current amount of hitpoints of the worm
	 */
	private int hitpoints;
	
	/**
	 * terminate this worm
	 * @post		this worm is terminated
	 * @post		this worm doesn't exist within a world anymore
	 */
	@Override
	public void terminate() {
		super.terminate();
		World world = getWorld();
		if (world != null) {
			world.removeWorm(this);
			world.removeItem(this);
		}
	}
	
	/**
	 * Set the world for this worm
	 * @param 	world
	 * 			The new world for this worm
	 * @param 	x
	 * 			The x-coordinate of this worm
	 * @param 	y
	 * 			The y-coordinate of this worm
	 * @param 	radius
	 * 			The radius of this worm
	 * @post		If this worm didn't have a world before, then it exists within the given world. 
	 * 			| if (getWorld() == null)
	 * 			| 	then new getWorld() = world
	 * @throws 	InvalidArgumentException
	 * 			When this worm doesn't have an acceptable loction within this world, or when the world or this
	 * 			worm is terminated, or if this worm already exists within the world
	 * 			| if (isValidLocation(x,y,radius) == true || isTerminated() == false ||
	 * 			|	hasActiveGame() == false || worm.isTerminated() == false || hasWorm(worm) == false)
	 */
	void setWorld(World world, double x, double y, double radius) throws InvalidArgumentException{
		if (getWorld() == null) {
			super.setWorld(world);
			if (world != null && world.hasWorm(this) == false) {
				world.addWorm(this, x, y, radius);
			}
		}
	}
	/**
	 * Remove this worm from its world
	 * @post		This worm won't be in a world anymore
	 * 			| this.getWorld() = null
	 */
	@Override
	void removeWorld() {
		World world = getWorld();
		if (world.hasWorm(this) == true)
			world.removeWorm(this);
		super.removeWorld();
	}
	
	
	public void eat() throws InvalidArgumentException{
		if (canEat() == true) {
			World world = getWorld();
			Food food = getEatableFood();
			consume(food);
			world.removeFood(food);
		}
		else
			throw new InvalidArgumentException("Worm cannot eat");
	}
	public void consume(Food food) throws InvalidArgumentException {
		double r =getRadius();
		World world = getWorld();
		if (food.isHealthy() == true) {
			double[] standLocation = getStandLocation(1.1);
			double xNew = standLocation[0];
			double yNew = standLocation[1];
			double newRadius = 1.1*r;
			if (world.isValidLocation(xNew, yNew, newRadius) == true) {
				setRadius(newRadius);
				setActionPoints(getActionPoints() - 8);
				setCoordinates(xNew, yNew);
			}
			else {
				world.removeWorm(this);
			}
		}
		else {
			double[] standLocation = getStandLocation(0.9);
			double xNew = standLocation[0];
			double yNew = standLocation[1];
			double newRadius = 0.9*r;
			if (newRadius < getMinRadius()) {
				newRadius = getMinRadius();
			}
			if (world.isValidLocation(xNew, yNew, newRadius) == true) {
				setRadius(newRadius);
				setActionPoints(getActionPoints() - 8);
				setCoordinates(xNew, yNew);
			}
			else {
				world.removeWorm(this);
			}
		}
	}
	private Food getEatableFood() throws InvalidArgumentException {
		double radius = this.getRadius();
		World world = this.getWorld();
		List<Food> foods = world.getFoods();
		List<Food> solution = new ArrayList<Food>();
		int n = foods.size();
		for (int i = 0; i < n; i = i + 1) {
			Food food = foods.get(i);
			double xFood = food.getX();
			double yFood = food.getY();
			if (radius + food.getRadius() >= this.distanceTo(xFood, yFood)) {
				solution.add(food);
			}
		}
		int l = solution.size();
		Random rand = new Random();
		if (l > 0) {
			int index = rand.nextInt(l);
			return(solution.get(index));
		}
		else
			throw new InvalidArgumentException("No eatable foods");
	}
	public double[] getStandLocation(double size) throws InvalidArgumentException {
		World world = getWorld();
		double radius = getRadius();
		double x = getX();
		double y = getY();
		double Xopl = Double.NaN;
		double Yopl = Double.NaN;
		for (int i=1; i <= 20; i = i + 1) {
			double factor = 0.1 * i * 0.05 * radius;
			for (int j=0; j <= 360; j = j + 1) {
				double angle = Math.toRadians(j);
				double Xj = x + factor * Math.cos(angle);
				double Yj = y + factor * Math.sin(angle);
				if (world.isAdjacent(Xj, Yj, size*radius) == true) {
					Xopl = Xj;
					Yopl = Yj;
				}
			}
		}
		if (Double.isNaN(Xopl) == false && Double.isNaN(Yopl) == false) {
			return new double[] {Xopl, Yopl};
		}
		else {
			throw new InvalidArgumentException("Error");
		}
	}
	public boolean canEat() {
		World world = getWorld();
		List<Food> foods = world.getFoods();
		for (int i = 0; i < foods.size(); i = i + 1) {
			Food food = foods.get(i);
			double xFood = food.getX();
			double yFood = food.getY();
			if (this.getRadius() + food.getRadius() > this.distanceTo(xFood, yFood) && isValidAction(8) == true) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if this worm is active
	 * @return True if it is this worm's turn
	 * @return False if it isn't this worm's turn
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * Activate this worm
	 * @post		it is this worm's turn
	 * @post		This worm's amount of action points has been replenished to the maximum amount
	 */
	public void activate() {
		setActionPoints(getMaxActionPoints());
		this.active = true;
	}
	/**
	 * Deactivate this worm
	 * @post		this worm is deactivated and its turn is over
	 */
	public void deactivate() {
		this.active = false;
	}
	/**
	 * A boolean variable registering whether or not it's the turn of this worm
	 */
	private boolean active;
	
	public Projectile fire() throws InvalidArgumentException {
		World world = this.getWorld();
		if (world != null && getActionPoints() >=30) {
			if (this.getsHit() == false) {
				Random rand = new Random();
				int type = rand.nextInt(2);
				if (type == 0)
					setActionPoints(getActionPoints() - 10);
				else
					setActionPoints(getActionPoints() - 25);
				return new Projectile(this,type);
			}
			else {
				List<Projectile> projectiles = world.getProjectiles();
				for (int i = 0; i < projectiles.size(); i = i + 1) {
					Projectile projectile = projectiles.get(i);
					if (projectile.getsHitBy(this) == true) {
						projectile.damage(this);
						break;
					}
				}
				return null;
			}
		}
		else
			throw new InvalidArgumentException("Cannot fire");
	}
	public boolean getsHit() {
		World world = getWorld();
		List<Projectile> projectiles = world.getProjectiles();
		boolean getsHit = false;
		if (projectiles.size() > 0 ) {
			for (int i = 0; i < projectiles.size(); i = i + 1) {
				Projectile projectile = projectiles.get(i);
				if (projectile.getsHit(this, projectile.getX(), projectile.getY()) == true) {
					getsHit = true;
				}
			}
		}
		return getsHit;
	}
	
	/**
	 * Load a new program into this worm
	 * @param 	newProgram
	 * 			The new program for this worm
	 * @param 	newActionHandler
	 * 			The actionHandler for this program
	 * @post		This worm is now computercontrolled instead of playercontrolled and will execute the actions
	 * 			discribed in the loaded program
	 */
	public void loadProgram(Program newProgram, IActionHandler newActionHandler) {
		this.program = newProgram;
		newProgram.setWorm(this);
		newProgram.setActionHandler(newActionHandler);
	}
	/**
	 * get the program that's currently loaded in this worm
	 * @return
	 */
	public Program getProgram() {
		return this.program;
	}
	/**
	 * A variable registering the currently loaded program of this worm
	 */
	private Program program;
}
