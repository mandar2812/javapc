package org.ldaniels528.javapc.jbasic.gwbasic.program.commands.io.device;


import org.ldaniels528.javapc.jbasic.common.tokenizer.TokenIterator;

import org.ldaniels528.javapc.jbasic.common.exceptions.JBasicException;
import org.ldaniels528.javapc.jbasic.common.exceptions.NotYetImplementedException;
import org.ldaniels528.javapc.jbasic.common.program.JBasicCompiledCode;
import org.ldaniels528.javapc.jbasic.common.values.Value;
import org.ldaniels528.javapc.jbasic.gwbasic.program.commands.GwBasicCommand;

/**
 * UNLOCK Statement
 * <br>Purpose: To release locks that have been applied to an opened file. 
 * This is used in a multi-device environment, often referred to as a network 
 * or network environment.
 * <br>Syntax: UNLOCK [#]n [,[record number] [TO record number]]
 * @author lawrence.daniels@gmail.com
 */
public class UnlockOp extends GwBasicCommand {
	private Value[] recordNumbers;
	private int handle;	  

	  /**
	   * Creates an instance of this opCode
	   * @param it the parsed text that describes the BASIC instruction
	   * @throws JBasicException
	   */
	  public UnlockOp( TokenIterator it ) throws JBasicException {
		  parse( it );		  
	  }

	  /* 
	   * (non-Javadoc)
	   * @see org.ldaniels528.javapc.jbasic.program.opcodes.OpCode#execute(org.ldaniels528.javapc.jbasic.program.JBasicProgram, org.ldaniels528.javapc.jbasic.environment.JBasicEnvironment)
	   */
	  public void execute( JBasicCompiledCode program ) 
	  throws JBasicException {
		  throw new NotYetImplementedException();
	  }
	  	  
	  /**
	   * Converts the given textual representation into {@link org.ldaniels528.javapc.jbasic.common.values.Value values}
	   * that will be displayed at runtime
	   * @param it the given {@link TokenIterator iterator}
	   * @throws JBasicException
	   */
	  private void parse( TokenIterator it ) throws JBasicException {
		  // TODO Determine what to do here
	  }

}
