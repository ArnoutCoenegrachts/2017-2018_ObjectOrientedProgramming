package worms.programs;

import java.util.List;
import java.util.Stack;

import worms.model.InvalidArgumentException;

public class BlockStatement extends Statement{
	
	public BlockStatement(List<Statement> statements, SourceLocation location) {
		super(location, null);
		setStatements(statements);
		setStack(statements);
	}
	
	public void setStack(List<Statement> statements) {
		Stack<Statement> result = new Stack<Statement>();
		for (Statement e : statements) {
			result.add(e);
		}
		this.s = result;	
	}
	public Stack<Statement> getStack() {
		return this.s;
	}
	private Stack<Statement> s;
	
	public void setStatements(List<Statement> s) {
		this.statements = s;
	}
	public List<Statement> getStatements() {
		return this.statements;
	}
	private List<Statement> statements;
	
	@Override
	public void Execute() throws InvalidArgumentException {
		while (getStack().size()>0) {
			getStack().pop().Execute();
		}
		setStack(getStatements());
		}
}
