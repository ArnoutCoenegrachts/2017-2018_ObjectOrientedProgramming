package worms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Projectile extends GameObject{
/**
 * 	Initialize a projectile from a worm and a weapon type
 * @param 	worm
 * 			The worm that fires this projectile
 * @param 	type
 * 			The type of weapon the worm uses: if type = 0, the worm fires a rifle and if
 * 			type == 1, it fires a bazooka
 */
public Projectile(Worm worm, int type) throws InvalidArgumentException {
		setWorm(worm);
		setType(type);
		setWorld();
		setAngle();
		setMass();
		setRadius();
		setMaxHitpoints();
		setHitpoints();
		setInitialCoordinates();
	}
	/**
	 * Get the worm that fired this projectile
	 */
	public Worm getWorm() {
		return this.worm;
	}
	/**
	 * 
	 * @param 	worm
	 * 			The worm of this 
	 * @post		| new.getWorm() = worm
	 */
	private void setWorm(Worm worm) {
		this.worm = worm;
	}
	private Worm worm;
	
	/**
	 * Get the type of this projectile
	 */
	public int getType() {
		return this.type;
	}
	/**
	 * Set the type of projectile
	 * @param 	type
	 * 			The type of this projectile
	 * @post		| new.getType() = type
	 * @throws 	InvalidArgumentException
	 * 			| type != 0 11 type != 1 
	 */
	private void setType(int type) throws InvalidArgumentException {
		if (type == 0 || type == 1)
			this.type = type;
		else
			throw new InvalidArgumentException("");
	}
	/**
	 * variable registering the type of this projectile
	 */
	private int type;
	/**
	 * Set the world of this projectile
	 */
	void setWorld() throws InvalidArgumentException  {
		super.setWorld(getWorm().getWorld());
	}
	
	/**
	 * get the orientation of this projectile
	 */
	public double getAngle() {
		return this.angle;
	}
	/**
	 * Set the orientation of this projectile to the orientation of its worm
	 * @post	| new.getAngle() = getWorm().getAngle()
	 */
	private void setAngle() {
		this.angle = getWorm().getAngle();
	}
	/**
	 * variabele registering the orientation of this projectile
	 */
	private double angle;
	
	/**
	 * get the mass of this projectile
	 */
	@Override
	public double getMass() {
		return this.mass;
	}
	/**
	 * set the mass of this projectile according to its type
	 * @post		| if (getType() == 0)
	 * 			|	then new.getMass() = 10
	 * @post		| if (getType() == 1)
	 * 			|	then new.getMass() = 300
	 */
	private void setMass() {
		int type = this.getType();
		if (type == 0) {
			this.mass = 10;
		}
		else {
			this.mass = 300;
		}
	}
	/**
	 * variable registering the mass of this projecile
	 */
	private double mass;
	
	/**
	 * set the radius of this projectile according to its mass
	 * @post		| new.getRadius() = (3*m/(4*density))^(1/3)
	 */
	private void setRadius() throws InvalidArgumentException {
		double m = getMass();
		double t = 3*m;
		double n = 4*p;
		super.setRadius(Math.pow(t/n, 1/3));
	}
	/**
	 * a constant registering the density of any projectile
	 */
	public final double p = 7800;
	
	/**
	 * sets the hitpoints in accordance to its type
	 * @post		| if (getType() == 0)
	 * 			|	then new.getHitpoints() = random.nextInt(6)*2
	 * @post		| if (getType() == 1)
	 * 			|	then new.getHitpoints() = random.nextInt(4)*2+1
	 */
	public void setHitpoints() {
		int type = this.getType();
		Random rand = new Random();
		if (type == 0) {
			this.hitpoints = rand.nextInt(6)*2;
		}
		else {
			this.hitpoints = rand.nextInt(4)*2+1;
		}
	}
	/**
	 * Increase the hitpoints of this projectile with the given extraHP
	 * @param 	extraHP
	 * 			The amount of hitpoints to be added to this projectile
	 * @post		| if(getHitpoints() + extraHP <= getMaxHitpoints())
	 * 			|	then new.getHitpoints() = getHitpoints() + extraHP
	 * @post		| if(getHitpoints() + extraHP > getMaxHitpoints())
	 * 			|	then new.getHitpoints() = getMaxHitpoints()
	 */
	public void increaseHitpoints(int extraHP) {
		int totHP = getHitpoints() + extraHP;
		int maxHP = getMaxHitpoints();
		if(totHP <= maxHP) {
			this.hitpoints = totHP;
		}
		else {
			this.hitpoints = maxHP;
		}
	}
	/**
	 * Get the current amount of hitpoints of this projectile
	 */
	public int getHitpoints() {
		return this.hitpoints;
	}
	/**
	 * A variable registering the amount of hitpoints of this projectile
	 */
	private int hitpoints;
	
	/**
	 * get the maximum amount of hitpoints this projectile can have
	 */
	public int getMaxHitpoints() {
		return this.maxHitpoints;
	}
	/**
	 * set the maximum amount of hitpoints this projectile can have in accordence to its type
	 * @post		| if (getType() == 0}
	 * 			|	then new.getMaxHitpoints() = 10
	 * @post		| if (getType() == 1}
	 * 			|	then new.getMaxHitpoints() = 7
	 */
	private void setMaxHitpoints() {
		int type = this.getType();
		if (type == 0) {
			maxHitpoints = 10;
		}
		else
			maxHitpoints = 7;
	}
	/**
	 * a variable registering the maximum amount of hitpoints this projectile can have
	 */
	private int maxHitpoints;
	
	private void setInitialCoordinates() throws InvalidArgumentException {
		Worm worm = this.getWorm();
		double xW = worm.getX();
		double yW = worm.getY();
		double rW = worm.getRadius();
		double r = this.getRadius();
		double angle = this.getAngle();
		double x = xW + (rW+r)*Math.cos(angle);
		double y = yW + (rW+r)*Math.sin(angle);
		setCoordinates(x,y);
	}
	/**
	 * set the coordinates of this worm to the given values
	 * @param	x
	 * 			the new x-coordinate of this projectile
	 * @param	y
	 * 			the new y-coordinate of this projectile
	 * @post		| if (getWorld().hasValidCoordinates(x, y, getRadius()) == true)
	 * 			|	then new.getCoordinates() = {x, y}
	 * @post		| if (getWorld().hasValidCoordinates(x, y, getRadius()) == false)
	 * 			|	then getWorld().removeProjectile(this)
	 */
	@Override
	public void setCoordinates(double x, double y) throws InvalidArgumentException {
		World world = getWorld();
		if (world.hasValidCoordinates(x, y, getRadius()) == true) {
			super.setCoordinates(x,y);
		}
		else
			getWorld().removeProjectile(this);
	}
	
	/**
	 * terminate this projectile
	 * @post		this projectile is terminated
	 * @post		this projectile doesn't exist within a world anymore
	 */
	@Override
	public void terminate() {
		super.terminate();
		World world = getWorld();
		if (world != null) {
			world.removeProjectile(this);
			world.removeItem(this);
		}
	}
	
	/**
	 * Let this projectile "jump" in the current direction of the projectile
	 * @param 	timeStep
	 * 			the amount of time between each step of the jump
	 * @post		this projectile will have a new location adjacent to impassable terrain or
	 * 			if it hits one or more worms, it will damage them
	 * @throws 	InvalidArgumentException
	 * 			| isValidJump() == false
	 */
	public void jump(double timeStep) throws InvalidArgumentException {
		if (isValidJump() == true) {
			List<double[]> path = getJumpPath();
			int n = path.size() - 1;
			double xNew = path.get(n)[0];
			double yNew = path.get(n)[1];
			if (getWorld().hasValidCoordinates(xNew, yNew, getRadius()) == true) {
				setCoordinates(xNew, yNew);
				if (getsHitAt(xNew, yNew) == true) {
					damage();
				}
			}
			else
				getWorld().removeProjectile(this);
		}
		else
			throw new InvalidArgumentException("Invalid Jump");
	}
	/**
	 * Calculate the path of a possible jump of the projectile in small steps
	 * @param 	deltaT
	 * 			The amount of time between each step of the jump
	 * @return	A list of coordinates representing the path the projectile will take when it jumps. Every set of
	 * 			coordinates representes a passable area of the world and the final set of coordinates is either
	 * 			adjacent to impassable terrain or hits atleast one worm
	 * 			| return List<double[]> path
	 */
	public List<double[]> jumpPath(double deltaT) {
		World world = getWorld();
		double r = getRadius();
		double initVx = getInitialVelocity() * Math.cos(getAngle());
		double initVy = getInitialVelocity() * Math.sin(getAngle());
		List<double[]> path = new ArrayList<double[]>();
		for (double time = 0; time >= 0; time = time + deltaT) {
			double xAtTime = getX() + (initVx * time);
			double yAtTime = getY() + (initVy * time - 0.5 * g * (time * time));
			path.add(new double[] {xAtTime, yAtTime});
			if (world.hasValidCoordinates(xAtTime, yAtTime, getRadius()) == false || getsHitAt(xAtTime, yAtTime) == true 
					|| world.isPassable(xAtTime, yAtTime,r) == false || world.isAdjacent(xAtTime, yAtTime, r) == true) {
				break;
			}
		} 
		return path;
	}
	/**
	 * Get the amount of time the jump will take
	 * @param 	deltaT
	 * 			The amount of time between each step
	 * @return	The amount of time the jump will take
	 * 			| (path.size() - 1) * deltaT
	 */
	public double jumpTime(double deltaT) {
		List<double[]> path = jumpPath(deltaT);
		this.jumpPath = path;
		int n = path.size() - 1;
		return n * deltaT;
	}
	/**
	 * Get the current jump path of this projectile
	 * @return	the jump path of this projectile
	 */
	public List<double[]> getJumpPath() {
		return this.jumpPath;
	}
	/**
	 * The jump path of this projectile
	 */
	private List<double[]> jumpPath;
	/**
 	 * Get the position of this projectile during the jump at the given time
 	 * @param 	time
 	 * 			The amount of time that has passed since the jump started for which the position has to be
 	 * 			caluclated
 	 * @return	The coordinates of the projectile during the jump at the given time
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
			else if (time == 0 && world.isPassable(xAtTime, yAtTime) == false) {
				return new double[] {xAtTime, yAtTime};
			}
			else
				throw new InvalidArgumentException("");
		}
		else {
			throw new InvalidArgumentException("Invalid Time");	
		}
	}
	/**
	 * Check whether the projectile is able to jump or not
	 * @return	True if and only if the angle of the worm is between zero and pi radians and the amount
	 * 			of action points the worm currently has is larger than zero
	 * 			| angle >= 0 && angle <= Math.PI && getActionPoints() > 0
	 */
	public boolean isValidJump() {
		double angle = this.getAngle();
		if (angle >= 0 && angle <= Math.PI )
			return true;
		else
			return false;
	}
	/**
	 * Return the force this projectile will output in accordence to its type
	 * @return	| if (getType() == 0)
	 * 			|	then return 1.5
	 * @return	| if (getType() == 1)
	 * 			|	then return 2.5 + getWorm().getActionPoints()%8
	 */
	public double getForce() {
		if (getType() == 0)
			return 1.5;
		else
			return (2.5 + getWorm().getActionPoints()%8);
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
	private double g = 5;
	
	/**
	 * Check if a worm gets hit by this projectile
	 * @param 	worm
	 * 			the worm to check this for
	 * @param 	x
	 * 			The x-coordinate of this projectile when it could possibly hit something
	 * @param 	y
	 * 			The y-coordinate of this projectile when it could possibly hit something
	 * @return	| if (getWorm().getRadius() + this.getRadius() < worm.distanceTo(x,y)) 
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean getsHit(Worm worm, double x, double y) {
		if (worm.getRadius() + this.getRadius() < worm.distanceTo(x,y)) 
			return true;
		else
			return false;
	}
	/**
	 * Check if this projectile gets hit by a worm at the given location
	 * @param 	x
	 * 			The x-coordinate of the location to check
	 * @param 	y
	 * 			The y-coordinate of the location to check
	 * @return	| if (getsHit(Worm worm, double x, double y)
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean getsHitAt(double x, double y) {
		World world = getWorld();
		List<Worm> worms = world.getWorms();
		boolean getsHit = false;
		for (int i = 0; i < worms.size(); i = i + 1)
			getsHit = getsHit(worms.get(i), x, y);
		return getsHit;
	}
	/**
	 * Check if this projectile gets hit by the given worm
	 * @param 	worm
	 * 			the worm to check against
	 */
	public boolean getsHitBy(Worm worm) {
		return getsHit(worm, getX(), getY());
	}
	/**
	 * Damage all worms that overlap with this projectile
	 * @post		| this.terminate()
	 */
	public void damage() {
		List<Worm> worms = this.getWorld().getWorms();
		for (int i = 0; i < worms.size(); i = i + 1) {
			Worm worm = worms.get(i);
			if (getsHitBy(worm) == true) {
				damage(worm);
			}
		}
		this.terminate();
	}
	/**
	 * Damage a single, given worm
	 * @param 	worm
	 * 			The worm to damage
	 * @post		| if (getType() == 0)
	 * 			|	then worm.lowerHitpoints(this.getHitpoints())
	 * @post		| if (getType() == 1)
	 * 			|	then worm.lowerHitpoints(this.getHitpoints()*getForce())
	 */
	public void damage(Worm worm) {
		int damage = getHitpoints();
		if (getType() == 1) {
			damage = (int) (this.getHitpoints()*getForce());
		}	
		worm.lowerHitpoints(damage);
	}
	
}
