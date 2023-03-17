package worms.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling invalid arguments for properties of worms
 * @author Arnout Coenegrachts
 */
public class InvalidArgumentException extends Exception{	
	
	/**
	 * Initialize this new invalid argument exception with given double.
	 * @param 	number
	 * 			the value for this new invalid argument exception
	 * @post		The value of this new invalid argument exception is equal to the given double
	 * 			| new.getDouble() == number
	 */
	public InvalidArgumentException(Double number) {
		this.numberD = number;
	}
	/**
	 * Initialize this new invalid argument exception with given integer.
	 * @param 	number
	 * 			the value for this new invalid argument exception
	 * @post		The value of this new invalid argument exception is equal to the given integer
	 * 			| new.getInteger() == number
	 */
	public InvalidArgumentException(int number) {
		this.numberI = number;
	}
	
	
	/**
	 * Initialize this new invalid argument exception with given string.
	 * @param 	text
	 * 			the value for this new invalid argument exception
	 * @post		The value of this new invalid argument exception is equal to the given string
	 * 			| new.getText() == text
	 */
	public InvalidArgumentException(String text) {
		this.text = text;
	}
	
	
	
	/**
	 * Return the double value registered for this invalid argument exception.
	 * @return
	 */
	@Basic @Immutable
	public Double getDouble() {
		return this.numberD;
	}
	
	/**
	 * Return the integer value registered for this invalid argument exception.
	 * @return
	 */
	@Basic @Immutable
	public int getInteger() {
		return this.numberI;
	}
	
	/**
	 * Return the string value registered for this invalid argument exception.
	 * @return
	 */
	@Basic @Immutable
	public String getText() {
		return this.text;
	}
	
	/**
	 * Variable registering the double involved in this invalid argument exception
	 */
	public Double numberD;
	
	/**
	 * Variable registering the integer involved in this invalid argument exception
	 */
	public int numberI;
	
	public String text;
	
	/**
	 * A generated serial version ID for this exception class.
	 */
	private static final long serialVersionUID = -3342178625685551039L;
}
