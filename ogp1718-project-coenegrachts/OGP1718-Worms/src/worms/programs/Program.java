package worms.programs;


import java.util.List;

import worms.internal.gui.game.IActionHandler;
import worms.model.InvalidArgumentException;
import worms.model.Worm;

public class Program {
	public Program(List<Procedure> procs, Statement main) throws InvalidArgumentException {
		setWorm(null);
		setActionHandler(null);
		this.main = main;
		if (worm.isActive() == true) {
			this.execute();
		}
	}
	
	public void setWorm(Worm newWorm) {
		this.worm = newWorm;
	}
	public Worm getWorm() {
		return this.worm;
	}
	private Worm worm;
	
	public Statement getMain() {
		return this.main;
	}
	private final Statement main;
	
	
	public void setActionHandler(IActionHandler newActionHandler) {
		this.actionHandler = newActionHandler;
	}
	public IActionHandler getActionHandler() {
		return this.actionHandler;
	}
	private IActionHandler actionHandler;
	
	public void execute() throws InvalidArgumentException {
		getMain().Execute();
	}
	
	
	public void addPrintedObject(Object o) {
		getPrintedObjects().add(o);
	}
	public List<Object> getPrintedObjects() {
		return this.printedObjects;
	}
	private List<Object> printedObjects;
	
	public class Procedure {
		// Must not implement?
	}
}

