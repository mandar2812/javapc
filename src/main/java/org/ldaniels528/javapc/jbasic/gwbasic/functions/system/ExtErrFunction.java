package org.ldaniels528.javapc.jbasic.gwbasic.functions.system;

import org.ldaniels528.javapc.ibmpc.devices.memory.MemoryObject;

import org.ldaniels528.javapc.jbasic.common.tokenizer.TokenIterator;

import org.ldaniels528.javapc.jbasic.common.JBasicCompiledCodeReference;
import org.ldaniels528.javapc.jbasic.common.exceptions.JBasicException;
import org.ldaniels528.javapc.jbasic.common.functions.JBasicFunction;
import org.ldaniels528.javapc.jbasic.common.values.types.impl.JBasicTempString;

/**
 * EXTERR Function
 * <br>Purpose: To return extended error information.
 * <br>Syntax: EXTERR(<i>function</i>)
 * <pre>
 * Function		Return Value
 * ----------	------------
 *    0          Extended error code
 *    1          Extended error class
 *    2          Extended error suggested action
 *    3          Extended error locus
 * </pre>
 * @author lawrence.daniels@gmail.com
 */
public class ExtErrFunction extends JBasicFunction {

    /**
     * Creates an instance of this function
     * @param it the given {@link TokenIterator iterator}
     * @throws JBasicException
     */
    public ExtErrFunction( String name, TokenIterator it ) 
    throws JBasicException {
    		super( name, it, TYPE_NUMERIC );
    }

    /* 
     * (non-Javadoc)
     * @see org.ldaniels528.javapc.jbasic.base.values.Value#getValue(org.ldaniels528.javapc.jbasic.base.environment.JBasicEnvironment)
     */
	public MemoryObject getValue(JBasicCompiledCodeReference program) {
		// get the parameter values
		final MemoryObject[] objects = getParameterValues( program );
		
		// get the environment string
		final int function = objects[0].toInteger();
		
		// return error string based on the function
		final String error;
		switch( function ) {
			case 0: error = "Extended error code"; break;
			case 1: error = "Extended error class"; break;
			case 2: error = "Extended error suggested action"; break;
			case 3: error = "Extended error locus"; break;
			default: error = "Extended error code";
		}
		
		// return a string containing the error
		JBasicTempString string = new JBasicTempString();
		string.setString( error );
		return string;
	}

}
