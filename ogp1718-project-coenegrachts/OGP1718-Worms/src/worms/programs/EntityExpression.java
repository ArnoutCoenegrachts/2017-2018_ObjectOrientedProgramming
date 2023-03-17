package worms.programs;

import java.util.List;

import worms.model.Food;
import worms.model.GameObject;
import worms.model.InvalidArgumentException;
import worms.model.Projectile;
import worms.model.World;
import worms.model.Worm;
import worms.programs.SourceLocation;

public class EntityExpression extends Expression<GameObject>{

	public EntityExpression(Statement statement, SourceLocation sourceLocation, Expression<?> expression) throws InvalidArgumentException {
		super(statement, sourceLocation);
		setElement(expression);
		setValue();
	}
	
	public void setElement(Expression<?> e) {
		this.element = e;
	}
	public Expression<?> getElement() {
		return this.element;
	}
	private Expression<?> element;
	
	public void setValue() throws InvalidArgumentException {
		Expression<?> e = getElement();
		if (e.getValue() == null)
			setValue(getSelf());
		else if (e.getValue() instanceof GameObject) 
			setValue((GameObject) e.getValue());
		else if (e.getValue() instanceof Double) {
			double number = (double) e.getValue();
			double angle = number + getSelf().getAngle();
			double cos = Math.cos(angle);
			double sin = Math.sin(angle);
			World world = this.getValue().getWorld();
			double x = getSelf().getX();
			double y = getSelf().getY();
			List<GameObject> objects = world.getItems();
			GameObject result = null;
			for (GameObject o : objects) {
				double xo = o.getX();
				double yo = o.getY();
				if ((x-xo)/cos == (y-yo)/sin) 
					result = o;
			}
			setValue(result);
		}
		else
			throw new InvalidArgumentException("Invalid Expression given");
			
		
	}
	public GameObject getValue() {
		return this.value;
	}
	private GameObject value;

	@Override
	public String toString() {
		GameObject object = this.getValue();
		return objectToString(object);
	}
	public String objectToString(GameObject object) {
		String Class;
		String Information;
		String Location = "("+String.valueOf(object.getX())+","+String.valueOf(object.getY())+")";
		if (object instanceof Worm) {
			Class = "Worm";
			Information = ((Worm) object).getName();
		}
		else if (object instanceof Food) {
			Class = "Food";
			if(((Food) object).isHealthy() == true)
				Information = "healty";
			else
				Information = "poisonned";
		}
		else {
			Class = "Projectile";
			if (((Projectile) object).getType() == 0)
				Information = "rifle";
				
			else
				Information = "bazooka"	;
		}
		return Class+"_"+Information+"_"+Location;
	}

	
}