package worms.programs;

import java.util.Arrays;

import worms.internal.gui.game.IActionHandler;
import worms.model.InvalidArgumentException;
import worms.model.Worm;

public class ActionStatement extends Statement{
	public ActionStatement(SourceLocation location, String operator, Expression<?> e) throws InvalidArgumentException {
		super(location, null);
		setOperator(operator);
		setE(e, operator);
	}
	
	public void setE(Expression<?> newE, String operator) throws InvalidArgumentException {
		if (operator == "Turn") {
			if (newE instanceof DoubleExpression) {
				this.e = (DoubleExpression) newE;
			
			}
			else
				throw new InvalidArgumentException("");
		}
	}
	public DoubleExpression getE() {
		return this.e;
	}
	private DoubleExpression e;
	
	
	public void setOperator(String newOperator) throws InvalidArgumentException {
		if (Arrays.asList(getValidOperators()).contains(newOperator))
			this.operator = newOperator;
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
	private final String[] validOperators = {"Turn", "Move", "Jump", "Eat", "Fire"};
	
	
	public IActionHandler getActionHandler() {
		return getProgram().getActionHandler();
	}
	
	
	@Override
	public void Execute() throws InvalidArgumentException {
		Worm self = getSelf();
		String operator = getOperator();
		IActionHandler ah = getActionHandler();
		if (operator == "Turn")
			ah.turn(self, getE().getValue());
		else if (operator == "Move")
			ah.move(self);
		else if (operator == "Jump")
			ah.jump(self);
		else if (operator == "Eat")
			ah.eat(self);
		else if (operator == "Fire")
			ah.fire(self);
		else
			throw new InvalidArgumentException("This should be impossible");
	}
}
