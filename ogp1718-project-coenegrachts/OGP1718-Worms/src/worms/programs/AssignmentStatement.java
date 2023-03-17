package worms.programs;

import java.util.HashMap;
import java.util.Map;

public class AssignmentStatement extends Statement{
	public AssignmentStatement(String name, Expression<?> expression, SourceLocation location) {
		super(location, null);
		setName(name);
		setValue(expression);
	}
	
	public void setValue(Expression<?> newValue) {
		this.value = newValue;
	}
	public Expression<?> getValue() {
		return this.value;
	}
	private Expression<?> value;
	
	public void setName(String newName) {
		this.name = newName;
	}
	public String getName() {
		return this.name;
	}
	private String name;
	
	public void Execute() {
		Map<String, Expression<?>> map = new HashMap<String, Expression<?>>();
		map.put(getName(),getValue());
	}
}

