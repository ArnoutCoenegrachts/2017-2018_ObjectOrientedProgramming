package worms.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
import worms.util.MustNotImplementException;

public class World {
	
	@Raw
	public World(double width, double height, boolean[][] passableMap) throws InvalidArgumentException{
		this.worms = new ArrayList<Worm>();
		this.foods = new ArrayList<Food>();
		setMap(passableMap);
		setWidth(width);
		setHeight(height);
		setPixelSize();
		this.game = false;
	}
	/**
	 * Get the map of the passable and impassable areas of the world
	 */
	public boolean[][] getMap() {
		return this.map;
	}
	/**
	 * save the map of this world
	 * @param 	passableMap
	 * 			The map of this world
	 * @throws 	InvalidArgumentException
	 * 			| passableMap == null || passableMap.length == 0
	 */
	public void setMap(boolean[][]passableMap) throws InvalidArgumentException {
		if (passableMap != null && passableMap.length != 0)
			this.map = passableMap;
		else
			throw new InvalidArgumentException("Invalid map");
	}
	/**
	 * variable registering the map of the passable and impassable areas of the world
	 */
	private boolean[][] map;
	
	/**
	 * Return the width of this world
	 */
	@Basic
	public double getWidth() {
		return this.width;
	}
	/**
	 * set the width of this world
	 * @param 	width
	 * 			the width
	 * @throws 	InvalidArgumentException
	 * 			| isValidSize(width) == false
	 */
	@Basic
	public void setWidth(double width) throws InvalidArgumentException{
		if (isValidSize(width)==true)
			this.width = width;
		else
			throw new InvalidArgumentException(width);
	}
	/**
	 * Return the height of this world
	 */
	@Basic
	public double getHeight() {
		return this.height;
	}
	/**
	 * set the height of this world
	 * @param 	height
	 * 			the height
	 * @throws 	InvalidArgumentException
	 * 			| isValidSize(width) == false
	 */
	@Basic
	public void setHeight(double height) throws InvalidArgumentException{
		if (isValidSize(height)==true)
			this.height = height;
		else
			throw new InvalidArgumentException(height);
	}
	/**
	 * a variable registering the width of this world
	 */
	private double width;
	/**
	 * a variable registering the height of this world
	 */
	private double height;
	/**
	 * Check wether the given size is a valid one
	 * @param 	size
	 * 			The size to be checked
	 * @return	| if (size >= 0.0 && size <= Double.MAX_VALUE)
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean isValidSize(double size) {
		if (size >= 0.0 && size <= Double.MAX_VALUE)
			return true;
		else
			return false;
	}
	
	/**
	 * Return the pixelsize of this world
	 */
	public double[] getPixelSize() {
		return this.pixelSize;
	}
	/**
	 * Set the pixelsize of this world in accordance with the map
	 * @throws 	InvalidArgumentException
	 * 			| getMap()[0].length == 0 || getMap().length == 0
	 */
	public void setPixelSize() throws InvalidArgumentException{
		double rows = getMap()[0].length;
		double coloms = getMap().length;
		if (rows != 0 && coloms != 0)
			this.pixelSize = new double[] {getWidth()/rows, getHeight()/coloms};	
		else
			throw new InvalidArgumentException("Invalid map");
	}
	/**
	 * a variable registering the pixelsize of this world
	 */
	private double[] pixelSize;
	
	/**
	 * Check if the given coordinates remain within the boundries of this world for objects with the given radius
	 * @param 	x
	 * 			The x-coordinate
	 * @param 	y
	 * 			The y-coordinate
	 * @param 	radius
	 * 			The radius of the object
	 * @return	| if (x-radius >= 0.0 && y-radius >= 0.0 && x+radius <= getWidth() && y+radius <= getHeight())
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean hasValidCoordinates(double x, double y, double radius) {
		if (x-radius >= 0.0 && y-radius >= 0.0 && x+radius <= getWidth() && y+radius <= getHeight())
			return true;
		else
			return false;
	}
	/**
	 * Get the corresponding pixels on the map for the given coordinates
	 * @param 	x
	 * 			the x-coordinate
	 * @param 	y
	 * 			the y-coordinate
	 * @return	(int[]) {Math.floor( (getHeight()-Math.floor(y)-1) / getPixelSize()[1]), Math.floor( Math.floor(x) / getPixelSize()[0])}
	 */
	public int[] mapLocation(double x, double y) {
		int c = (int) Math.floor( Math.floor(x) / getPixelSize()[0]);
		int r = (int) Math.floor( (getHeight()-Math.floor(y)-1) / getPixelSize()[1]);
		return new int[] {r,c};
	}
	
	/**
	 * Check if the given coordinates are passable
	 * @param 	x
	 * 			the x-coordinate to be checked
	 * @param 	y
	 * 			the y-coordinate to be checked
	 * @return	| if (r < totR && r>=0 && c < totC && c >= 0) 
	 *			|	then return getMap()[r][c];
	 *			| else
	 *			|	then return true
	 */
	public boolean isPassable(double x, double y) {
		int[] location = mapLocation(x,y);
		int r = location[0];
		int c = location[1];
		int totR = getMap().length;
		int totC = getMap()[0].length;
		if (r < totR && r>=0 && c < totC && c >= 0) {
			return getMap()[r][c];
		}
		else
			return true;
	}
	/**
	 * Check if the given coordinates are passable for an object with the given radius
	 * @param 	x
	 * 			the x-coordinate to be checked
	 * @param 	y
	 * 			the y-coordinate to be checked
	 * @param 	radius
	 * 			the radius of the checked object
	 * @return	true if the entire area covert by the object is passable
	 * @return 	false if a portion of the a
	 */
	public boolean isPassable(double x, double y, double radius) {
		boolean ok = true;
		int n;
		if (getPixelSize()[0]>=getPixelSize()[1]) 
			n = (int) Math.ceil(2 * radius/getPixelSize()[0]);
			
		else 
			n = (int) Math.ceil(2 * radius/getPixelSize()[1]);
		for (int i=0; i<360; i = i + 1) {
			double angle = Math.toRadians(i);
			double Xi = radius * Math.cos(angle);
			double Yi = radius * Math.sin(angle);
			for (int j=0; j <= n; j = j + 1) {
				double Xj = x + j/n * Xi;
				double Yj = y + j/n * Yi;
				if (isPassable(Xj,Yj) == false) {
					ok = false;
					j = n+1;
					i = 360;
					return false;
				}
		}}
		return ok;
	}
	/**
	 * Check if the given coordinates are adjacent to impassable terrain for an object with the given radius
	 * @param 	x
	 * 			the x-coordinate to be checked
	 * @param 	y
	 * 			the y-coordinate to be checked
	 * @param 	radius
	 * 			the radius of the object to be checked
	 * @return	| if (isPassable(x, y, radius) == true && isPassable(x,y,1.1*radius) == false)
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean isAdjacent(double x, double y, double radius) {
		if (isPassable(x, y, radius) == true && isPassable(x,y,1.1*radius) == false)
			return true;
		else
			return false;
	}
	/**
	 * Check if the given set of coordinates are adjacent to impassable terrain for an object with the given radius
	 * @param 	location
	 * 			the set of coordinates to be checked
	 * @param 	radius
	 * 			the radius of the object to be checked
	 * @return	| if (isPassable(location[0], location[1], radius) == true && isPassable(location[0],location[1],1.1*radius) == false)
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean isAdjacent(double[] location, double radius) {
		return isAdjacent(location[0], location[1], radius);
	}
	/**
	 * Check if the given coordinates are adjacent to impassable terrain, while also remaining within the bounds of this world, for an object with the given radius
	 * @param 	x
	 * 			the x-coordinate to be checked
	 * @param 	y
	 * 			the y-coordinate to be checked
	 * @param 	radius
	 * 			the radius of the object to be checked
	 * @return	| if (hasValidCoordinates(x,y, radius) == true && isPassable(x,y,radius) == true && isAdjacent(x,y,radius) == true)
	 *			|	then return true
	 *			| else
	 *			|	then return false
	 */
	public boolean isValidLocation(double x, double y, double radius) {
		if (hasValidCoordinates(x,y, radius) == true && isPassable(x,y,radius) == true && isAdjacent(x,y,radius) == true)
			return true;
		else
			return false;
	}
	
	/**
	 * Get a list with all the worms that exist in this world
	 */
	public List<Worm> getWorms() {
		return this.worms;
	}
	/**
	 * add a worm to this world
	 * @param 	worm
	 * 			the worm to be added
	 * @param 	x
	 * 			the x-coordinate of the worm
	 * @param 	y
	 * 			the y-coordinate of the worm
	 * @param 	radius
	 * 			the radius of the worm
	 * @post		There is a new worm in this world with the given attributes
	 * @throws 	InvalidArgumentException
	 * 			| isValidLocation(x,y,radius) == false || isTerminated() == true || hasActiveGame() == true || worm.isTerminated() == true || hasWorm(worm) == true
	 */
	public void addWorm(Worm worm, double x, double y, double radius) throws InvalidArgumentException{
		if (isValidLocation(x,y,radius) == true && isTerminated() == false && hasActiveGame() == false && worm.isTerminated() == false && hasWorm(worm) == false) {
			this.worms.add(worm);
			this.items.add(worm);
			worm.setWorld(this, x, y, radius);
		}
		else {
			
			throw new InvalidArgumentException("Invalid Worm!");
		}
	}
	/**
	 * Create a new worm in this universe
	 * @param 	x
	 * 			The x-coordinate of this new worm
	 * @param 	y
	 * 			The y-coordinate of this new worm
	 * @param 	angle
	 * 			The orientation of this new worm
	 * @param 	radius
	 * 			The radius of this new worm
	 * @param 	name
	 * 			The name of this new worm
	 * @post		There is a new worm in this world with the given attributes
	 * @return	| new Worm(this, x, y, angle, radius, name)
	 */
	public Worm createWorm(double x, double y, double angle, double radius, String name) throws InvalidArgumentException {
		return new Worm(this, x, y, angle, radius, name);
	}
	/**
	 * Remove the given worm from this world
	 * @param 	worm 
	 * 			the worm to be removed
	 * @post		the given worm doesn't exist within this world
	 * @post		| worm.terminate();
	 * @post		| if (getWorms().size() == 1)
	 * 			|	then finishGame();
	 */
	public void removeWorm(Worm worm) {
		getWorms().remove(worm);
		getItems().remove(worm);
		worm.removeWorld();
		if (worm.isTerminated() == false)
			worm.terminate();
		if (getWorms().size() == 1)
			finishGame();
	}
	/**
	 * Check if this worlds has the given worm
	 * @param 	worm
	 * 			The worm to be checked
	 * @return	| if (getWorms().contains(worm) || getItems().contains(worm) ) 
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean hasWorm(Worm worm) {
		if (getWorms().contains(worm) || getItems().contains(worm) ) 
			return true;
		else
			return false;
	}
	/**
	 * a variable registering all the worms that exist in this world
	 */
	private List<Worm> worms = new ArrayList<Worm>();
	/**
	 * Get the worm who's turn it is
	 */
	public Worm getActiveWorm() {
		List<Worm> worms = getWorms();
		for (Worm worm : worms) {
			if (worm.isActive() == true)
				return worm;
		}
		return worms.get(0);
	}
	/**
	 * Activate the next worm
	 */
	public void activateNextWorm() {
		List<Worm> worms = getWorms();
		int n = worms.size() - 1;
		for (int i = 0; i <= n; i = i + 1) {
			Worm worm = worms.get(i);
			if (worm.isActive() == true) {
				worm.deactivate();
				if (i != n) {
					worms.get(i+1).activate();
					i = n + 1;
				}
				else {
					worms.get(0).activate();
					i = n + 1;
				}
			}	
		}
	}
	
	/**
	 * Get a list with all the food that exist in this world
	 */
	public List<Food> getFoods() {
		return this.foods;
	}
	/**
	 * add a food to this world
	 * @param 	food
	 * 			the food to be added
	 * @param 	x
	 * 			the x-coordinate of the worm
	 * @param 	y
	 * 			the y-coordinate of the worm
	 * @param 	radius
	 * 			the radius of the food
	 * @post		There is a new food in this world with the given attributes
	 * @throws 	InvalidArgumentException
	 * 			| isValidLocation(x,y,radius) == false || isTerminated() == true || hasActiveGame() == true || food.isTerminated() == true || hasFood(food) == true
	 */
	public void addFood(Food food, double x, double y, double radius) throws InvalidArgumentException{
		if (isValidLocation(x, y, radius) == true && isTerminated() == false && hasActiveGame() == false && food.isTerminated() == false && hasFood(food) == false) {
			this.foods.add(food);
			this.items.add(food);
			food.setWorld(this, x, y, radius);
			}
		else
			throw new InvalidArgumentException("Invalid Food");
	}
	/**
	 * Create a new food in this universe
	 * @param 	x
	 * 			The x-coordinate of this new food
	 * @param 	y
	 * 			The y-coordinate of this new food
	 * @post		There is a new food in this world with the given attributes
	 * @return	| new Food(this, x, y)
	 */
	public Food createFood(double x, double y) throws InvalidArgumentException {
		return new Food(this, x, y);
	}
	/**
	 * Remove the given food from this world
	 * @param 	food 
	 * 			the food to be removed
	 * @post		the given food doesn't exist within this world
	 * @post		| food.terminate();
	 */
	public void removeFood(Food food) {
		getFoods().remove(food);
		getItems().remove(food);
		food.removeWorld();
		if (food.isTerminated() == false)
			food.terminate();

	}
	/**
	 * Check if this worlds has the given food
	 * @param 	food
	 * 			The food to be checked
	 * @return	| if (getFoods().contains(food) || getItems().contains(food) ) 
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean hasFood(Food food) {
		if (getFoods().contains(food))
			return true;
		else
			return false;
	}
	/**
	 * a variable registering all the food that exist in this world
	 */
	private List<Food> foods = new ArrayList<Food>();
	
	/**
	 * Get a list with all the projectiles that exist in this world
	 */
	public List<Projectile> getProjectiles() {
		return this.projectiles;
	}
	/**
	 * add a worm to this world
	 * @param 	projectile
	 * 			the projectile to be added
	 * @post		There is a new projectile in this world
	 * @throws 	InvalidArgumentException
	 * 			| isValidLocation(x,y,radius) == false || isTerminated() == true || hasActiveGame() == true || projectile.isTerminated() == true || hasProjectile(projectile) == true
	 */
	public void addProjectile(Projectile projectile) throws InvalidArgumentException {
		double x = projectile.getX();
		double y = projectile.getY();
		if (hasValidCoordinates(x,y, projectile.getRadius()) == true && isTerminated() == false && projectile.isTerminated() == false) {
			this.projectiles.add(projectile);
			this.items.add(projectile);
			projectile.setWorld();
		}
		else
			throw new InvalidArgumentException("Invalid Projectile");
	}
	/**
	 * Remove the given projectile from this world
	 * @param 	projectile 
	 * 			the projectile to be removed
	 * @post		the given projectile doesn't exist within this world
	 * @post		| projectile.terminate();
	 */
	public void removeProjectile(Projectile projectile) {
		getProjectiles().remove(projectile);
		removeItem(projectile);
		projectile.removeWorld();
		if (projectile.isTerminated() == false)
			projectile.terminate();
	}
	/**
	 * Check if this worlds has the given projectile
	 * @param 	projectile
	 * 			The projectile to be checked
	 * @return	| if (getProjectiles().contains(projectile) || getItems().contains(projectile) ) 
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean hadProjectile(Projectile projectile) {
		if (getProjectiles().contains(projectile))
			return true;
		else
			return false;
	}
	/**
	 * a variable registering all the projectiles that exist in this world
	 */
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	
	/**
	 * @throws 	MustNotImplementException
	 */
	public Set<Team> getAllTeams() throws MustNotImplementException{
		throw new MustNotImplementException();
	}
	
	/**
	 * Get a list with all the GameObjects that exist in this world
	 */
	public List<GameObject> getItems() {
		return this.items;
	}
	/**
	 * Remove the given GameObject from this world
	 * @param 	item
	 * 			the GameObject to be removed
	 * @post		the given GameObject doesn't exist within this world
	 * @post		| item.terminate();
	 */
	public void removeItem(GameObject item) {
		if (items.contains(item)) {
			items.remove(item);
			if (item.isTerminated() == false)
				item.terminate();
		}
	}
	/**
	 * Check if this worlds has the given item
	 * @param 	item
	 * 			The item to be checked
	 * @return	| if (getItems().containts(item) 
	 * 			|	then return true
	 * 			| else
	 * 			|	then return false
	 */
	public boolean hasItem(GameObject item) {
		if (getItems().contains(item))
			return true;
		else
			return false;
	}
	/**
	 * a variable registering all the items that exist in this world
	 */
	private List<GameObject> items = new ArrayList<GameObject>();
	/**
	 * Get all items as Objects
	 */
	public List<Object> getAllItems() {
		List<Object> allItems = new ArrayList<Object>();
		List<GameObject> items = this.getItems();
		if (items.size() > 0) {
			for (GameObject o : items) {
				allItems.add(o);
			}
		}
		return allItems;
	}
	
	/**
	 * Terminate this world
	 * @post		| this.isTerminated() = true
	 * @post		| getItems().size = 0
	 */
	public void terminate() {
		this.isTerminated = true;
		int n = getWorms().size();
		for (int i = n - 1; i >= 0 ; i = i - 1) {
			removeWorm(getWorms().get(i));
		}
		int m = getFoods().size();
		for (int i = m - 1; i >= 0; i = i - 1) {
			removeFood(getFoods().get(i));
		}
		int o = getProjectiles().size();
		for (int i = o - 1; i >= 0; i = i - 1) {
			removeProjectile(getProjectiles().get(i));
		}
		
	}
	/**
	 * Return whether this world is terminated
	 */
	public boolean isTerminated() {
		return isTerminated;
	}
	/**
	 * variable registering if this world is terminated or not
	 */
	private boolean isTerminated = false;
	
	/**
	 * return the winner of the game
	 * @post		the game is over
	 * @throws 	InvalidArgumentException
	 * 			| getWorms().size()>1
	 */
	public String getWinner() throws InvalidArgumentException {
		List<Worm> worms = getWorms();
		if (worms.size() == 1)
			return worms.get(0).getName();
		else
			throw new InvalidArgumentException("Nobody has won yet");
	}

	/**
	 * Start the game
	 * @post		the game has started
	 */
	public void startGame() {
		this.game = true;
	}
	/**
	 * end the game
	 * @post		the game is over
	 */
	public void finishGame() {
		this.game = false;
	}
	/**
	 * Check whether this world has a game in progress or not
	 */
	public boolean hasActiveGame() {
		return this.game;
	}
	/**
	 * a boolean variable registering whether there is a game in progress or not
	 */
	private boolean game = false;
	
	public void castSpell() throws InvalidArgumentException {
		List<GameObject> items = getItems();
		int n = items.size();
		if (n >= 2) {
			Random rand = new Random();
			int i1 = rand.nextInt(n);
			int i2 = rand.nextInt(n);
			while (i2 == i1) {
				i2 = rand.nextInt(n);
			}
			GameObject object1 = items.get(i1);
			GameObject object2 = items.get(i2);
			executeSpell(object1, object2);
		}
	}
	public void executeSpell(GameObject object1, GameObject object2) throws InvalidArgumentException {
		if (object1 instanceof Worm) {
			Worm worm1 = (Worm) object1;
			if (object2 instanceof Worm) {
				Worm worm2 = (Worm) object2;
				double r1 = worm1.getRadius();
				double r2 = worm2.getRadius();
				int AP1 = worm1.getActionPoints();
				int AP2 = worm2.getActionPoints();
				if (r1 >= r2) {
					if (AP1 >= 5) {
						worm1.setActionPoints(AP1 - 5);
						worm2.setActionPoints(AP2 + 5);
					}
					else {
						worm1.setActionPoints(0);
						worm2.setActionPoints(AP2 + AP1);
					}
				}
				else {
					if (AP2 >= 5) {
						worm1.setActionPoints(AP1 + 5);
						worm2.setActionPoints(AP2 - 5);
					}
					else {
						worm1.setActionPoints(AP1 + AP2);
						worm2.setActionPoints(0);
					}
				}
			}
			else if (object2 instanceof Food) {
				Food food2 = (Food) object2;
				worm1.consume(food2);
			}
			else if (object2 instanceof Projectile) {
				Projectile projectile2 = (Projectile) object2;
				worm1.lowerHitpoints(projectile2.getHitpoints());
				projectile2.setHitpoints();
			}
		}
		else if (object1 instanceof Food) {
			Food food1 = (Food) object1;
			if (object2 instanceof Worm) {
				Worm worm2 = (Worm) object2;
				worm2.consume(food1);
			}
			else if (object2 instanceof Food) {
				Food food2 = (Food) object2;
				food1.changeStatus();
				food2.changeStatus();
			}
			else if (object2 instanceof Projectile) {
				Projectile projectile2 = (Projectile) object2;
				this.removeFood(food1);
				this.removeProjectile(projectile2);
			}
		}
		else if (object1 instanceof Projectile) {
			Projectile projectile1 = (Projectile) object1;
			if (object2 instanceof Worm) {
				Worm worm2 = (Worm) object2;
				worm2.lowerHitpoints(projectile1.getHitpoints());
				projectile1.setHitpoints();
			}
			else if (object2 instanceof Food) {
				Food food2 = (Food) object2;
				this.removeFood(food2);
				this.removeProjectile(projectile1);
			}
			else if (object2 instanceof Projectile) {
				Projectile projectile2 = (Projectile) object2;
				projectile1.increaseHitpoints(2);
				projectile2.increaseHitpoints(2);
			}
		}
	}
	
}
	

