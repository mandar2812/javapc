package ibmpc.devices.cpu.x86.opcodes.math;

import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.X86ExtendedFlags;
import ibmpc.devices.cpu.operands.Operand;
import ibmpc.devices.cpu.x86.opcodes.AbstractOpCode;

/**
 * <pre>
 * TEST - Test For Bit Pattern
 * 
 *	Usage:  TEST    dest,src
 *	Modifies flags: CF OF PF SF ZF (AF undefined)
 *	
 *	Performs a logical AND of the two operands updating the flags
 *	register without saving the result.
 *	
 *	                         Clocks                 Size
 *	Operands         808x  286   386   486          Bytes
 *	
 *	reg,reg           3     2     1     1             2
 *	reg,mem          9+EA   6     5     1            2-4  (W88=13+EA)
 *	mem,reg          9+EA   6     5     2            2-4  (W88=13+EA)
 *	reg,immed         5     3     2     1            3-4
 *	mem,immed       11+EA   6     5     2            3-6
 *	accum,immed       4     3     2     1            2-3
 * </pre>
 * @author lawrence.daniels@gmail.com
 */
public class TEST extends AbstractOpCode {
	private final Operand dest;
	private final Operand src;
	
	/**
	 * TEST dest, src
	 * @param dest the given {@link Operand destination}
	 * @param src the given {@link Operand source}
	 */
	public TEST( final Operand dest, final Operand src ) {
		this.dest	= dest;
		this.src	= src;
	}

	/* 
	 * (non-Javadoc)
	 * @see ibmpc.devices.cpu.OpCode#execute(ibmpc.devices.cpu.Intel80x86)
	 */
	public void execute( final Intel80x86 cpu ) {
		// perform the logic AND operation
		// on the source and destination values
		final int andValue = dest.get() & src.get();
		final int addValue = dest.get() + src.get();
		
		// adjust the flags
		final X86ExtendedFlags flags = cpu.FLAGS;
		flags.setCF( andValue == addValue );
		flags.setOF( andValue > addValue );
		flags.setPF( andValue % 2 == 0 );
		flags.setSF( andValue >= 0x80 );
		flags.setZF( andValue == 0 );
	}
	
	/* 
	 * (non-Javadoc)
	 * @see ibmpc.devices.cpu.x86.opcodes.AbstractOpCode#toString()
	 */
	public String toString() {
		return String.format( "%s %s,%s", getClass().getSimpleName(), dest, src );
	}

}
