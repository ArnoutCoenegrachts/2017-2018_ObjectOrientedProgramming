package worms.programs;


import java.util.Arrays;

import worms.model.GameObject;
import worms.model.InvalidArgumentException;
import worms.model.Projectile;
import worms.model.Worm;
import worms.programs.SourceLocation;


public class DoubleExpression extends Expression<Double>{

	public DoubleExpression(Statement statement, SourceLocation sourceLocation, Expression<?> n1, Expression<?> n2, String operator) throws InvalidArgumentException {
		super(statement, sourceLocation);
		setLeft(n1);
		setLeft(n2);
		setValue();
		
	}
	public DoubleExpression(Statement statement, SourceLocation sourceLocation, double value) throws InvalidArgumentException {
		super(statement, sourceLocation);
		setOperator(null);
		setValue(value);
	}
	
	public void setLeft(Expression<?> newLeft) {
		this.left = newLeft;
	}
	public Expression<?> getLeft() {
		return this.left;
	}
	private Expression<?> left;
	
	public void setRight(Expression<?> newRight) {
		this.right = newRight;
	}
	public Expression<?> getRight() {
		return this.right;
	}
	private Expression<?> right;

	public void setOperator(String newOperator) throws InvalidArgumentException {
		if (Arrays.asList(getValidOperators()).contains(newOperator)) {
			this.operator = newOperator;
		}
		else
			throw new InvalidArgumentException("Invalid Operator");
	}
	public String getOperator() {
		return this.operator;
	}
	private String operator;
	
	public String[] getValidOperators() {
		return this.validOperators;
	}
	private final String[] validOperators = {"+", "-", "*", "/", "sqrt", "sin", "cos", null,
			"x", "y", "radius", "dir", "AP", "MaxAp", "HP", "d"};
	
	public void setValue() throws InvalidArgumentException {
		Expression<?> l = getLeft();
		Expression<?> r = getRight();
		String operator = getOperator();
		if (l.getValue() instanceof Double && r.getValue() instanceof Double) {
			double left = (double) l.getValue();
			double right = (double) r.getValue();
			if (operator == null) {
				this.value = left;
			}
			else if (operator == "sqrt") {
				this.value = Math.sqrt(left);
			}
			else if (operator == "sin") {
				this.value = Math.sin(left);
			}
			else if (operator == "cos") {
				this.value = Math.cos(left);
			}
			else if (operator == "+") {
				this.value = left + right;
			}
			else if (operator == "-") {
				this.value = left - right;
			}
			else if (operator == "*") {
				this.value = left*right;
			}
			else if (operator == "/") {
				if (right != 0) {
					this.value = left / right;
				}
				else
					throw new InvalidArgumentException("Division by 0");
			}
			else
				throw new InvalidArgumentException("");
		}
		else if (l.getValue() instanceof GameObject && r.getValue() == null) {
			GameObject o = (GameObject) l.getValue();
			if (operator == "x")
				setValue(o.getX());
			else if (operator == "y")
				setValue(o.getY());
			else if (operator == "radius")
				setValue(o.getRadius());
			else if (operator == "dir") {
				if (o instanceof Worm)
					setValue( ((Worm) o).getAngle() );
				else if (o instanceof Projectile)
					setValue( ((Projectile) o).getAngle() );
				else
					setValue(null);
			}
			else if (operator == "AP") {
				if (o instanceof Worm)
					setValue( (double) ((Worm) o).getActionPoints() );
				else
					setValue(null);
			}
			else if (operator == "MaxAP") {
				if (o instanceof Worm)
					setValue( (double) ((Worm) o).getMaxActionPoints() );
				else
					setValue(null);
			}
			else if (operator == "HP") {
				if (o instanceof Worm)
					setValue( (double) ((Worm) o).getHitpoints() );
				else if (o instanceof Projectile)
					setValue( (double) ((Projectile) o).getHitpoints() );
				else
					setValue(null);
			}
			else if (operator == "d") {
				double d = Math.pow(getSelf().getX()-o.getX(), 2)+Math.pow(getSelf().getY()-o.getY(), 2);
				this.value = Math.sqrt(d);
			}
			else
				throw new InvalidArgumentException("");	
		}
		else
			throw new InvalidArgumentException("");
	}
	public Double getValue() {
		return this.value;
	}
	private double value;

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
	
	
	
	
	
}
