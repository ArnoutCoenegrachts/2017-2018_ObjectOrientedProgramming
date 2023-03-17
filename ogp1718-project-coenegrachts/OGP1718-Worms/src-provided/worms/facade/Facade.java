package worms.facade;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import worms.internal.gui.game.IActionHandler;
import worms.model.Food;
import worms.model.InvalidArgumentException;
import worms.model.Projectile;
import worms.model.Team;
import worms.model.World;
import worms.model.Worm;
import worms.programs.IProgramFactory;
import worms.programs.Program;
import worms.programs.ProgramFactory;
import worms.util.ModelException;

public class Facade implements IFacade{
	
	@Override
	public void move(Worm worm) throws ModelException {
		try {
			worm.move();
			}
		catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public void turn(Worm worm, double angle) throws ModelException {
		worm.turn(angle);
	}

	@Override
	public double[] getJumpStep(Worm worm, double t) throws ModelException {
		try {
		return(worm.jumpStep(t));
		}
		catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public double getOrientation(Worm worm) throws ModelException {
		return (worm.getAngle());
	}

	@Override
	public double getRadius(Worm worm) throws ModelException {
		return (worm.getRadius());
	}

	@Override
	public void setRadius(Worm worm, double newRadius) throws ModelException {
		try {
			worm.setRadius(newRadius);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public long getNbActionPoints(Worm worm) throws ModelException {
		return (worm.getActionPoints());
	}

	@Override
	public void decreaseNbActionPoints(Worm worm, long delta) throws ModelException {
		int newAmountActions = (int) (worm.getActionPoints() - delta);
		worm.setActionPoints(newAmountActions);
		
	}

	@Override
	public long getMaxNbActionPoints(Worm worm) throws ModelException {
		return (worm.getMaxActionPoints());
	}

	@Override
	public String getName(Worm worm) throws ModelException {
		return (worm.getName());
	}

	@Override
	public void rename(Worm worm, String newName) throws ModelException {
		try {
			worm.setName(newName);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
		
	}

	@Override
	public double getMass(Worm worm) throws ModelException {
		return worm.getMass();
	}
	
	@Override
	public World createWorld(double width, double height, boolean[][] passableMap) throws ModelException {
		try {
			return new World(width, height, passableMap);
		}
		catch(InvalidArgumentException e) {
			throw new ModelException("");
		}

	}
	
	@Override
	public void terminate(World world) throws ModelException {
		world.terminate();
		
	}
	
	@Override
	public boolean isTerminated(World world) throws ModelException {
		return world.isTerminated();
	}
	
	@Override
	public double getWorldWidth(World world) throws ModelException {
		return world.getWidth();
	}
	
	@Override
	public double getWorldHeight(World world) throws ModelException {
		return world.getHeight();
	}
	
	@Override
	public boolean isPassable(World world, double[] location) throws ModelException {
		return world.isPassable(location[0], location[1]);
	}
	
	@Override
	public boolean isPassable(World world, double[] center, double radius) {
		return world.isPassable(center[0], center[1], radius);
	}
	
	@Override
	public boolean isAdjacent(World world, double[] center, double radius) {
		return world.isAdjacent(center[0], center[1], radius);
	}
	
	@Override
	public boolean hasAsWorm(World world, Worm worm) throws ModelException {
		return world.hasWorm(worm);
	}
	
	@Override
	public void addWorm(World world, Worm worm) throws ModelException {
		try {
			if (worm != null && worm.getWorld() == null)
				world.addWorm(worm, worm.getX(), worm.getY(), worm.getRadius());
			else
				throw new ModelException("");
		}
		catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
		
	}
	
	@Override
	public void removeWorm(World world, Worm worm) throws ModelException {
		if (worm != null && world.hasWorm(worm) == true) {
			world.removeWorm(worm);
		}
		else
			throw new ModelException("");
	}
	
	@Override
	public List<Worm> getAllWorms(World world) throws ModelException {
		return world.getWorms();
	}
	
	@Override
	public boolean hasAsFood(World world, Food food) throws ModelException {
		return world.hasFood(food);
	}
	
	@Override
	public void addFood(World world, Food food) throws ModelException {
		try {
			if (food != null && food.getWorld() == null)
				world.addFood(food, food.getX(), food.getY(), food.getRadius());
			else
				throw new ModelException("");
		}
		catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
		
	}
	
	@Override
	public void removeFood(World world, Food food) throws ModelException {
		if (food != null && world.hasFood(food) == true)
			world.removeFood(food);
		else
			throw new ModelException("");
		
	}
	
	@Override
	public Collection<Object> getAllItems(World world) throws ModelException {
		if (world != null)
			return world.getAllItems();
		else
			return new ArrayList<Object>();
	}
	
	@Override
	public boolean hasActiveGame(World world) throws ModelException {
		return world.hasActiveGame();
	}
	
	@Override
	public Worm getActiveWorm(World world) throws ModelException {
		return world.getActiveWorm();
	}
	
	@Override
	public void startGame(World world) throws ModelException {
		world.startGame();
		
	}
	
	@Override
	public void finishGame(World world) throws ModelException {
		world.finishGame();
		
	}
	
	@Override
	public void activateNextWorm(World world) throws ModelException {
		world.activateNextWorm();
		
	}
	
	@Override
	public String getWinner(World world) throws ModelException{
		try {
			return world.getWinner();
		}
		catch (InvalidArgumentException e) {
			throw new ModelException("Nobody has won yet");
		}
	}
	
	@Override
	public void terminate(Worm worm) throws ModelException {
		worm.terminate();
		
	}
	
	@Override
	public boolean isTerminated(Worm worm) throws ModelException {
		return worm.isTerminated();
	}
	
	@Override
	public double[] getLocation(Worm worm) throws ModelException {
		return worm.getCoordinates();
	}
	
	@Override
	public BigInteger getNbHitPoints(Worm worm) throws ModelException {
		return BigInteger.valueOf(worm.getHitpoints());
	}
	
	@Override
	public void incrementNbHitPoints(Worm worm, long delta) throws ModelException {
		worm.increaseHitpoints((int)delta);
		
	}
	
	@Override
	public World getWorld(Worm worm) throws ModelException {
		return worm.getWorld();
	}
	
	@Override
	public double[] getFurthestLocationInDirection(Worm worm, double direction, double maxDistance)
			throws ModelException {
			return worm.getFurthestLocationInDirection(direction, maxDistance);
	}
	
	@Override
	public double getJumpTime(Worm worm, double deltaT) throws ModelException {
		try {
		return worm.jumpTime(deltaT);
		}
		catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}
	
	@Override
	public void jump(Worm worm, double timeStep) throws ModelException {
		try {
			worm.jump(timeStep);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
		
	}
	
	@Override
	public Food createFood(World world, double[] location) throws ModelException {
		try {
			if (world != null)
				return world.createFood(location[0], location[1]);
			else
				return new Food(null, location[0], location[1]);
		}
		catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}
	
	@Override
	public void terminate(Food food) throws ModelException {
		food.terminate();
		
	}
	
	@Override
	public boolean isTerminated(Food food) throws ModelException {
		return food.isTerminated();
	}
	
	@Override
	public double[] getLocation(Food food) throws ModelException {
		return food.getCoordinates();
	}
	
	@Override
	public double getRadius(Food food) throws ModelException {
		return food.getRadius();
	}
	
	@Override
	public double getMass(Food food) throws ModelException {
		return food.getMass();
	}
	
	@Override
	public World getWorld(Food food) throws ModelException {
		return food.getWorld();
	}
	
	@Override
	public void eat(Worm worm) throws ModelException{
		try {
		worm.eat();
		}
		catch (InvalidArgumentException e) {
			throw new ModelException("I'm not very hungry.");
		}
	}

	@Override
	public Set<Team> getAllTeams(World world) throws ModelException {
		return world.getAllTeams();
	}

	@Override
	public Worm createWorm(World world, double[] location, double direction, double radius, String name, Team team)
			throws ModelException {
		try {
			if (world != null)
				return world.createWorm(location[0], location[1], direction, radius, name);
			else
				return new Worm(null, location[0], location[1], direction, radius, name);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Projectile fire(Worm worm) throws ModelException {
		try {
			return worm.fire();
		}
		catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public boolean isPoisonous(Food food) throws ModelException {
		if (food.isHealthy() == true)
			return false;
		else
			return true;
	}

	@Override
	public void poison(Food food) throws ModelException {
		food.changeStatus();
	}

	@Override
	public boolean isTerminated(Projectile projectile) throws ModelException {
		return projectile.isTerminated();
	}

	@Override
	public double getOrientation(Projectile projectile) throws ModelException {
		return projectile.getAngle();
	}

	@Override
	public double[] getLocation(Projectile projectile) throws ModelException {
		return projectile.getCoordinates();
	}

	@Override
	public double getRadius(Projectile projectile) {
		return projectile.getRadius();
	}

	@Override
	public int getNbHitPoints(Projectile projectile) throws ModelException {
		return projectile.getHitpoints();
	}

	@Override
	public double[] getJumpStep(Projectile projectile, double elapsedTime) {
		try {
			return projectile.jumpStep(elapsedTime);
		}
		catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public double getJumpTime(Projectile projectile, double jumpTimeStep) throws ModelException {
		return projectile.jumpTime(jumpTimeStep);
	}

	@Override
	public void jump(Projectile projectile, double jumpTimeStep) throws ModelException {
		try {
			projectile.jump(jumpTimeStep);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Program getWormProgram(Worm worm) throws ModelException {
		return worm.getProgram();
	}

	@Override
	public void loadProgramOnWorm(Worm worm, Program program, IActionHandler actionHandler) throws ModelException {
		worm.loadProgram(program, actionHandler);
	}

	@Override
	public List<Object> executeProgram(Worm worm) throws ModelException {
		try {
			worm.getProgram().execute();
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
		return null;
	}

	@Override
	public IProgramFactory<?, ?, ?, ? extends Program> createProgramFactory() throws ModelException {
		return new ProgramFactory();
	}

	@Override
	public void castSpell(World world) throws ModelException {
		try {
			world.castSpell();
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
		
	}
	
	
}
