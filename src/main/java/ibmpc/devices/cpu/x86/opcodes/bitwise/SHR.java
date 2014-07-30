package ibmpc.devices.cpu.x86.opcodes.bitwise;

import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.operands.Operand;
import ibmpc.devices.cpu.x86.opcodes.AbstractDualOperandOpCode;

/**
 * SHR (Shift Right)
 * 
 *	Usage:  SAR     dest,count
 *  Modifies flags: CF OF PF SF ZF (AF undefined)
 *
 *        +-----------------------+   +---+
 *    +-->| 7 ----------------> 0 |-->| C |
 *    |   +-----------------------+   +---+ 
 *    |     |
 *    +-----+
 *
 *	Shifts the destination right by "count" bits with the current sign
 *	bit replicated in the leftmost bit.  The Carry Flag contains the
 *	last bit shifted out.
 *
 *                           Clocks                 Size
 *	Operands         808x  286   386   486          Bytes
 *
 *	reg,1             2     2     3     3             2
 *	mem,1           15+EA   7     7     4            2-4  (W88=23+EA)
 *	reg,CL           8+4n  5+n    3     3             2
 *	mem,CL        20+EA+4n 8+n    7     4            2-4  (W88=28+EA+4n)
 *	reg,immed8        -    5+n    3     2             3
 *	mem,immed8        -    8+n    7     4            3-5
 * @author lawrence.daniels@gmail.com
 */
public class SHR extends AbstractDualOperandOpCode {
	
	/**
	 * SHR dst, src
	 * @param dest the given {@link Operand destination}
	 * @param src the given {@link Operand source}
	 */
	public SHR( final Operand dest, final Operand src ) {
		super( "SHR", dest, src );
	}

	/* (non-Javadoc)
	 * @see ibmpc.devices.cpu.OpCode#execute(ibmpc.devices.cpu.VirtualCPU)
	 */
	public void execute( final Intel80x86 cpu ) {
		// get the source and destination values
		final int value0 = dest.get();
		final int value1 = src.get();

		// shift the destination right by the source value
		dest.set( value0 >> value1 );
		
		// put the right most bit into CF
		cpu.FLAGS.setCF( ( value0 & 0x01) != 0 );
	}

}