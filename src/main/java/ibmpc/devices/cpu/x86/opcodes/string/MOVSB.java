package ibmpc.devices.cpu.x86.opcodes.string;

import static ibmpc.devices.memory.X86MemoryUtil.computePhysicalAddress;
import static ibmpc.devices.memory.X86MemoryUtil.reverseBlock;
import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.x86.opcodes.AbstractOpCode;
import ibmpc.devices.memory.IbmPcRandomAccessMemory;

/** 
 * <pre>
 * Copies data from addressed by DS:SI (even if operands are given) to
 * the location ES:DI destination and updates SI and DI based on the
 * size of the operand or instruction used.  SI and DI are incremented
 * when the Direction Flag is cleared and decremented when the Direction
 * Flag is Set.  Use with REP prefixes. 
 * </pre>
 * @see REPZ
 * @see REPNZ
 * @author lawrence.daniels@gmail.com
 */
public class MOVSB extends AbstractOpCode implements MassDataOpCode {
	private static MOVSB instance = new MOVSB();
	
	/**
	 * Private constructor
	 */
	private MOVSB() {
		super();
	}
	
	/**
	 * @return the singleton instance of this class
	 */
	public static MOVSB getInstance() {
		return instance;
	}

	/* 
	 * (non-Javadoc)
	 * @see ibmpc.devices.cpu.OpCode#execute(ibmpc.devices.cpu.Intel80x86)
	 */
	public void execute( final Intel80x86 cpu ) {
		// get the register collection and memory instances
		final IbmPcRandomAccessMemory memory = cpu.getRandomAccessMemory();
		
		// get the data byte from DS:[SI]
		final int data = memory.getByte( cpu.DS.get(), cpu.SI.get() );
		
		// put the data byte into ES:[DI]
		memory.setByte( cpu.ES.get(), cpu.DI.get(), (byte)data );
		
		// setup increment/decrement value
		final int delta = cpu.FLAGS.isDF() ? -1 : 1;
		
		// increment/decrement SI and DI
		cpu.SI.add( delta );
		cpu.DI.add( delta );
	}
	
	/* 
	 * (non-Javadoc)
	 * @see ibmpc.devices.cpu.x86.opcodes.string.MassDataOpCode#executeEnMass(ibmpc.devices.cpu.Intel80x86, int)
	 */
	public void executeEnMass( final Intel80x86 cpu, final int count ) {
		// get the register collection and memory instances
		final IbmPcRandomAccessMemory memory = cpu.getRandomAccessMemory();
		
		// determine direction
		final boolean backward = cpu.FLAGS.isDF();
		
		// setup increment/decrement value
		final int delta = count * ( backward ? -1 : 1 );
		
		// if the direction is backwards ...
		if( backward ) {
			// determine the source and destination addresses
			final int sourceAddress = computePhysicalAddress( cpu.DS.get(), cpu.SI.get() ) + delta;
			final int destinationAddress = computePhysicalAddress( cpu.ES.get(), cpu.DI.get() ) + delta;
			
			// get the source block from DS:[SI]
			final byte[] srcBlock = memory.getBytes( sourceAddress, count );
			
			// reverse the bytes in the memory block
			final byte[] dstBlock = reverseBlock( srcBlock );
			
			// copy the source block into ES:[DI]
			memory.setBytes( destinationAddress, dstBlock, count );
		}
		
		// must be forward
		else {
			// move "count" bytes of data from DS:[SI] to ES:[DI]
			memory.copyBytes( cpu.DS.get(), cpu.SI.get(), cpu.ES.get(), cpu.DI.get(), count );
		}
		
		// increment/decrement SI and DI
		cpu.SI.add( delta );
		cpu.DI.add( delta );
	}

}
