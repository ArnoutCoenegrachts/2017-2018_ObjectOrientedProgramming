package worms.programs;

import worms.model.InvalidArgumentException;
import worms.model.Worm;
import worms.programs.SourceLocation;

public abstract class Statement {
	public Statement(SourceLocation location, Program program) {
		setProgram(program);
		this.location = location;
	}
	
	public SourceLocation getLocation() {
		return this.location;
	}
	private final SourceLocation location;
	
	public void setProgram(Program newProgram) {
		this.program = newProgram;
	}
	public Program getProgram() {
		return this.program;
	}
	private Program program;
	
	public Worm getSelf() {
		return this.getProgram().getWorm();
	}
	
	public abstract void Execute() throws InvalidArgumentException;
	
	
	
	
	

}
