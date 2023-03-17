package worms.programs;

import worms.model.InvalidArgumentException;

public class WhileStatement extends Statement{
	public WhileStatement(SingleBooleanExpression e, Statement s, SourceLocation location) {
		super(location, null);
		setE(e);
		setS(s);
	}
	
	public void setE(SingleBooleanExpression newE) {
		this.e = newE;
	}
	public SingleBooleanExpression getE() {
		return this.e;
	}
	private SingleBooleanExpression e;
	
	public void setS(Statement newS) {
		this.s = newS;
	}
	public Statement getS() {
		return this.s;
	}
	private Statement s;
	
	@Override
	public void Execute() throws InvalidArgumentException {
		while (getE().getValue() == true) {
			getS().Execute();
		}
	}
}
