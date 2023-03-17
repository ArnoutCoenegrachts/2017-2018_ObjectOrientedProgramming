package worms.programs;

import java.util.Arrays;

import worms.model.InvalidArgumentException;
import worms.programs.SourceLocation;
import worms.programs.Expression.BooleanExpression;

public class BinaryBooleanExpression extends BooleanExpression{

	public BinaryBooleanExpression(Statement statement, SourceLocation sourceLocation, Expression<?> left, Expression<?> right, String operator) throws InvalidArgumentException {
		super(statement, sourceLocation);
		setLeft(left);
		setRight(right);
		setOperator(operator);
		setValue();
	}
	
	public void setLeft(Expression<?> newLeft) {
		this.left = newLeft;
	}
	public Expression<?> getLeft(){
		return this.left;
	}
	private Expression<?> left;
	
	public void setRight(Expression<?> newRight) {
		this.right = newRight;
	}
	public Expression<?> getRight(){
		return this.right;
	}
	private Expression<?> right;
	
	public void setOperator(String newOperator) throws InvalidArgumentException {
		if (Arrays.asList(this.getValidOperators()).contains(newOperator))
			setOperator(newOperator);
		else
			throw new InvalidArgumentException("Invalid Operator");
	}
	
	public String[] getValidOperators() {
		return this.validOperators;
	}
	private final String[] validOperators = {"<", "<=", ">", ">=", "==", "!=", "&&", "||"};
	
	
	public boolean areDoubleExpressions(Expression<?> e1, Expression<?> e2) {
		if (e1 instanceof DoubleExpression && e2 instanceof DoubleExpression)
			return true;
		else
			return false;
	}
	public boolean areBooleanExpressions(Expression<?> e1, Expression<?> e2) {
		if (e1 instanceof BooleanExpression && e2 instanceof BooleanExpression)
			return true;
		else
			return false;
	}
	
	public void setValue() throws InvalidArgumentException {
		Expression<?> left = getLeft();
		Expression<?> right = getRight();
		String operator = getOperator();
		if (areDoubleExpressions(left, right) == true) {
			double l = ((DoubleExpression) left).getValue();
			double r = ((DoubleExpression) right).getValue();
			if (operator == "<") {
				if (l < r) 
					setValue(true);
				else
					setValue(false);
			}
			else if (operator == "<=") {
				if(l <= r) 
					setValue(true);
				else
					setValue(false);
			}
			else if (operator == ">") {
				if (l > r)
					setValue(true);
				else
					setValue(false);
			}
			else if (operator == ">=") {
				if (l >= r)
					setValue(true);
				else
					setValue(false);
			}
			else if (operator == "==") {
				if (l == r)
					setValue(true);
				else
					setValue(false);
			}
			else if (operator == "!=") {
				if (l != r)
					setValue(true);
				else
					setValue(false);
			}
			else 
				throw new InvalidArgumentException("Invalid operator/values/combination");
		}
		else if (areBooleanExpressions(left, right) == true) {
			boolean l = ((BooleanExpression) left).getValue();
			boolean r = ((BooleanExpression) right).getValue();
			if (operator == "==") {
				if (l == r) 
					setValue(true);
				else
					setValue(false);
			}
			else if (operator == "!=") {
				if (l != r)
					setValue(true);
				else
					setValue(false);
			}
			else if (operator == "&&") {
				if (l == true && r == true)
					setValue(true);
				else
					setValue(false);
			}
			else if (operator == "||") {
				if (l == true || r == true)
					setValue(true);
				else
					setValue(false);
			}
			else
				throw new InvalidArgumentException("Invalid operator/values/combination");
		}
		else
			throw new InvalidArgumentException("Invalid Expressions");
		
		
		
		
		
		
	}
	
}
