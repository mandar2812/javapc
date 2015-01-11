package jbasic.gwbasic.program.commands;

import jbasic.common.tokenizer.TokenIterator;

import jbasic.common.exceptions.JBasicException;
import jbasic.common.exceptions.NotYetImplementedException;
import jbasic.common.program.JBasicCompiledCode;

/**
 * CLEAR Command
 * <br>To set all numeric variables to zero, all string variables to null, 
 * and to close all open files. Options set the end of memory and reserve 
 * the amount of string and stack space available for use by GW-BASIC.
 * <br>Syntax: CLEAR[,[expression1][,expression2]]
 * @author lawrence.daniels@gmail.com
 */
public class ClearOp extends GwBasicCommand {

	  /**
	   * Creates an instance of this opCode
	   * @param it the parsed text that describes the BASIC instruction
	   * @throws JBasicException
	   */
	  public ClearOp( TokenIterator it ) throws JBasicException {
		  // TODO Figure out what to do here
	  }

	  /* 
	   * (non-Javadoc)
	   * @see jbasic.program.opcodes.OpCode#execute(jbasic.program.JBasicProgram, jbasic.environment.JBasicEnvironment)
	   */
	  public void execute( JBasicCompiledCode program ) 
	  throws JBasicException {
		  throw new NotYetImplementedException();
	  }
	  
}
