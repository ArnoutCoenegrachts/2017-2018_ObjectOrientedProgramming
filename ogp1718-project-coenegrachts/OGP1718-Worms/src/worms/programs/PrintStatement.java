package worms.programs;

public class PrintStatement extends Statement{
	public PrintStatement(Expression<?> expression, SourceLocation location) {
		super(location, null);
		setValue(expression);
	}
	public void setValue(Expression<?> newValue) {
		this.value = newValue;
	}
	public Expression<?> getValue() {
		return this.value;
	}
	private Expression<?> value;
	
	public void Execute() {
		System.out.println(getValue().toString());
		getProgram().addPrintedObject(getValue().getValue());
	}
	
}
