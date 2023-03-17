package worms.programs;

import be.kuleuven.cs.som.annotate.Model;
import worms.model.InvalidArgumentException;
import worms.model.Worm;
import worms.programs.SourceLocation;


public abstract class Expression<S> {
	
	public Expression(Statement statement, SourceLocation sourceLocation) {
		setStatement(statement);
		setSourceLocation(sourceLocation);
	}
	
	public Expression(Statement statement, SourceLocation sourceLocation, String name) {
		setStatement(statement);
		setSourceLocation(sourceLocation);
	}
	
	
	public abstract String toString();
	
	
	
	
	
	
	public void setStatement(Statement newStatement) {
		this.statement = newStatement;
	}
	public Statement getStatement() {
		return this.statement;
	}
	private Statement statement;
	
	public void setValue(S newValue) {
		this.value = newValue;
	}
	public S getValue() {
		return this.value;
	}
	private S value;
	
	public void setSourceLocation(SourceLocation newSourceLocation) {
		this.sourceLocation = newSourceLocation;
	}
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	private SourceLocation sourceLocation;
	
	public Worm getSelf() {
		return this.getStatement().getProgram().getWorm();
	}
	
	public static abstract class BooleanExpression extends Expression<Boolean> {
		
		
		public BooleanExpression(Statement statement, SourceLocation sourceLocation) {
			super(statement, sourceLocation);
		}
		
		public void setValue(boolean newValue) {
			this.value = newValue;
		}
		public Boolean getValue() {
			return this.value;
		}
		private boolean value;
		
		public void setOperator(String newOperator) throws InvalidArgumentException {
			this.operator = newOperator;
		}
		public String getOperator() {
			return this.operator;
		}
		private String operator;
		
		@Override
		public String toString() {
			return String.valueOf(getValue());
		}
	}
	
	
	
	
}