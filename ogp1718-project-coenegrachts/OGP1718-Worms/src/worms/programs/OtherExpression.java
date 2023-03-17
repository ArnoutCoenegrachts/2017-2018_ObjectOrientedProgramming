package worms.programs;

import worms.programs.SourceLocation;

public class OtherExpression extends Expression<Object>{

	public OtherExpression(Statement statement, SourceLocation sourceLocation, Object value) {
		super(statement, sourceLocation);
		setValue(value);
	}

	@Override
	public String toString() {
		return null;
	}
}