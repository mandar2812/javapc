/**
 * 
 */
package ibmpc.devices.cpu.x87.opcodes;


import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.operands.Operand;
import ibmpc.devices.cpu.x86.opcodes.AbstractOpCode;
import ibmpc.exceptions.X86AssemblyException;

/**
 * FIDIV dest
 * @author lawrence.daniels@gmail.com
 */
public class FIDIV extends AbstractOpCode {
	private final Operand dest;
	
	/**
	 * FIDIV dest
	 * @param dest the given {@link Operand destination}
	 */
	public FIDIV( final Operand dest ) {
		this.dest = dest;
	}

	/* (non-Javadoc)
	 * @see ibmpc.devices.cpu.OpCode#execute(ibmpc.devices.cpu.VirtualCPU)
	 */
	public void execute( final Intel80x86 cpu ) 
	throws X86AssemblyException {
		throw new IllegalStateException( "Not yet implemented" );
	}
	
	/* 
	 * (non-Javadoc)
	 * @see ibmpc.devices.cpu.x86.opcodes.AbstractOpCode#toString()
	 */
	public String toString() {
		return String.format( "FIDIV %s", dest );
	}

}