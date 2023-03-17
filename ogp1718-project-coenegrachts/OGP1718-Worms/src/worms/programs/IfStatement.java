package worms.programs;

import worms.model.InvalidArgumentException;

public class IfStatement extends Statement{
	public IfStatement(SingleBooleanExpression e, Statement s1, Statement s2, SourceLocation location) {
		super(location, null);
		setE(e);
		setSIf(s1);
		setSElse(s2);
	}
	
	public void setE(SingleBooleanExpression newE) {
		this.e = newE;
	}
	public SingleBooleanExpression getE() {
		return this.e;
	}
	private SingleBooleanExpression e;
	
	public void setSIf(Statement newSIf) {
		this.sIf = newSIf;
	}
	public Statement getSIf() {
		return this.sIf;
	}
	private Statement sIf;
	
	public void setSElse(Statement newSElse) {
		this.sElse = newSElse;
	}
	public Statement getSElse() {
		return this.sElse;
	}
	private Statement sElse;
	
	@Override
	public void Execute() throws InvalidArgumentException {
		if (getE().getValue() == true) 
			getSIf().Execute();
		else
			getSElse().Execute();
	}
}
