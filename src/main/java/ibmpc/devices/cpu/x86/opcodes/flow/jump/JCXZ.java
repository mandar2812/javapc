package ibmpc.devices.cpu.x86.opcodes.flow.jump;

import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.operands.Operand;
import ibmpc.devices.cpu.x86.opcodes.flow.AbstractFlowControlOpCode;

/**
 * <pre>
 * Usage:  JCXZ    label
 * Modifies flags: None
 *
 * Causes execution to branch to "label" if register CX is zero.  
 * Uses unsigned comparison.
 * </pre>
 * @author lawrence.daniels@gmail.com
 */
public class JCXZ extends AbstractFlowControlOpCode {
	
	/**
	 * Creates a new conditional jump instruction
	 * @param destination the given memory offset to jump to.
	 */
	public JCXZ( final Operand destination ) {
		super( destination );
	}

	/**
	 * {@inheritDoc}
	 */
	protected boolean redirectsFlow( final Intel80x86 cpu ) {
		return ( cpu.CX.get() == 0 );
	}

}