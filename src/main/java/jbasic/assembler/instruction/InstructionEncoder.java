package jbasic.assembler.instruction;

import ibmpc.exceptions.X86AssemblyException;
import jbasic.assembler.util.X86CodeBuffer;

/**
 * Represents an Instruction Encoder
 * @author lawrence.daniels@gmail.com
 */
public interface InstructionEncoder {
	
	/**
	 * Assemble the given instruction into machine code stored within
	 * the given code buffer
	 * @param codebuf the given {@link X86CodeBuffer 80x86 code buffer} instance
	 * @param instruction the given {@link X86Instruction instruction}
	 * @return true, if the instruction was successfully encoded
	 * @throws X86AssemblyException
	 */
	boolean assemble( X86CodeBuffer codebuf, X86Instruction instruction ) 
	throws X86AssemblyException;
	
}
