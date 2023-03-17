package worms.programs;

import java.util.Arrays;

import worms.model.Food;
import worms.model.GameObject;
import worms.model.InvalidArgumentException;
import worms.model.Projectile;
import worms.model.Worm;
import worms.programs.SourceLocation;
import worms.programs.Expression.BooleanExpression;

public class SingleBooleanExpression extends BooleanExpression{

	public SingleBooleanExpression(Statement statement, SourceLocation sourceLocation, Expression<?> element, String operator) throws InvalidArgumentException {
		super(statement, sourceLocation);
		setElement(element);
		setOperator(operator);
		setValue();
	}
	public SingleBooleanExpression(Statement statement, SourceLocation sourceLocation, Boolean value) throws InvalidArgumentException {
		super(statement, sourceLocation);
		setOperator(null);
		setValue(value);
	}
	
	
	public void setElement(Expression<?> newElement) {
		this.element = newElement;
	}
	public Expression<?> getElement() {
		return this.element;
	}
	private Expression<?> element;
	
	public void setOperator(String newOperator) throws InvalidArgumentException {
		if (Arrays.asList(getValidOperators()).contains(newOperator))
			setOperator(newOperator);
		else
			throw new InvalidArgumentException("Invalid Operator");
	}
	
	public String[] getValidOperators() {
		return this.validOperators;
	}
	private final String[] validOperators = {"Worm", "Food", "Projectile", "Boolean"};

	public void setValue() throws InvalidArgumentException {
		Expression<?> e = getElement();
		String operator = getOperator();
		if (e instanceof EntityExpression) {
			GameObject o = ((EntityExpression) e).getValue();
			if (o instanceof Worm && operator == "Worm")
				setValue(true);
			else if (o instanceof Food && operator == "Food")
				setValue(true);
			else if (o instanceof Projectile && operator == "Projectile")
				setValue(true);
			else
				setValue(false); //this should be impossible, but I included it just to be sure
		}
		else if (e instanceof BooleanExpression && operator == "Boolean") {
			boolean b = ((BooleanExpression) e).getValue();
			if (b == true)
				setValue(false);
			else
				setValue(true);
		}
		else if (operator != "Boolean")
			setValue(false);
		else
			throw new InvalidArgumentException("");
	}

}
