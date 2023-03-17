package worms.programs;

import java.util.List;

import worms.model.InvalidArgumentException;
import worms.programs.IProgramFactory;
import worms.programs.SourceLocation;
import worms.programs.Program.*;
import worms.util.ModelException;

public class ProgramFactory implements IProgramFactory<Expression<?>, Statement, Procedure, Program>{

	@Override
	public Program createProgram(List<Procedure> procs, Statement main) throws ModelException {
		try {
			return new Program(procs, main);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Statement createAssignmentStatement(String variableName, Expression<?> value, SourceLocation sourceLocation)
			throws ModelException {
		return new AssignmentStatement(variableName, value, sourceLocation);
	}

	@Override
	public Statement createPrintStatement(Expression<?> value, SourceLocation sourceLocation) throws ModelException {
		return new PrintStatement(value, sourceLocation);
	}

	@Override
	public Statement createTurnStatement(Expression<?> angle, SourceLocation location) throws ModelException {
		try {
			return new ActionStatement(location, "Turn", angle);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Statement createMoveStatement(SourceLocation location) throws ModelException {
		try {
			return new ActionStatement(location, "Move", null);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Statement createJumpStatement(SourceLocation location) throws ModelException {
		try {
			return new ActionStatement(location, "Jump", null);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Statement createEatStatement(SourceLocation location) {
		try {
			return new ActionStatement(location, "Eat", null);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Statement createFireStatement(SourceLocation location) throws ModelException {
		try {
			return new ActionStatement(location, "Fire", null);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation)
			throws ModelException {
		return new BlockStatement(statements, sourceLocation);
	}

	@Override
	public Statement createIfStatement(Expression<?> condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWhileStatement(Expression<?> condition, Statement body, SourceLocation sourceLocation)
			throws ModelException {
		return null;
	}

	@Override
	public Expression<?> createReadVariableExpression(String variableName, SourceLocation sourceLocation)
			throws ModelException {
		return new OtherExpression(null, sourceLocation, variableName);
	}

	@Override
	public Expression<?> createDoubleLiteralExpression(double value, SourceLocation location) throws ModelException {
		try {
			return new DoubleExpression(null, location, value);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createBooleanLiteralExpression(boolean value, SourceLocation location) throws ModelException {
		try {
			return new SingleBooleanExpression(null, location, value);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createNullExpression(SourceLocation location) throws ModelException {
		return new OtherExpression(null, location, null);
	}

	@Override
	public Expression<?> createSelfExpression(SourceLocation location) throws ModelException {
		try {
			return new EntityExpression(null, location, null);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createAdditionExpression(Expression<?> left, Expression<?> right, SourceLocation location)
			throws ModelException {
		try {
			return new DoubleExpression(null, location, left, right, "+");
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createAndExpression(Expression<?> left, Expression<?> right, SourceLocation sourceLocation)
			throws ModelException {
		try {
			return new BinaryBooleanExpression(null, sourceLocation, left, right, "&&");
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createNotExpression(Expression<?> expression, SourceLocation sourceLocation) throws ModelException {
		try {
			return new SingleBooleanExpression(null, sourceLocation, expression, "Boolean" );
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createEqualityExpression(Expression<?> left, Expression<?> right, SourceLocation location)
			throws ModelException {
		try {
			return new BinaryBooleanExpression(null, location, left, right, "==");
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createLessThanExpression(Expression<?> left, Expression<?> right, SourceLocation location) {
		try {
			return new BinaryBooleanExpression(null, location, left, right, "<");
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createSearchObjectExpression(Expression<?> angleDelta, SourceLocation sourceLocation)
			throws ModelException {
		try {
			return new EntityExpression(null, sourceLocation, angleDelta);
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createDistanceExpression(Expression<?> entity, SourceLocation sourceLocation) throws ModelException {
		try {
			return new DoubleExpression(null, sourceLocation, entity, null, "d");
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

	@Override
	public Expression<?> createIsWormExpression(Expression<?> entity, SourceLocation sourceLocation) throws ModelException {
		try {
			return new SingleBooleanExpression(null, sourceLocation, entity, "Worm");
		} catch (InvalidArgumentException e) {
			throw new ModelException("");
		}
	}

}
