package ibmpc.devices.cpu.x86.decoder;

import static ibmpc.devices.cpu.x86.decoder.DecoderUtil.*;
import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.OpCode;
import ibmpc.devices.cpu.operands.Operand;
import ibmpc.devices.cpu.x86.opcodes.addressing.CS;
import ibmpc.devices.cpu.x86.opcodes.addressing.ES;
import ibmpc.devices.cpu.x86.opcodes.bitwise.AND;
import ibmpc.devices.cpu.x86.opcodes.math.DAA;
import ibmpc.devices.cpu.x86.opcodes.math.DAS;
import ibmpc.devices.cpu.x86.opcodes.math.SUB;
import ibmpc.devices.memory.X86MemoryProxy;

/**
 * <pre>
 * Decodes instruction codes between 20h and 2Fh
 *	---------------------------------------------------------------------------
 *	type	bits	description 				comments
 * 	---------------------------------------------------------------------------
 *	t		2		register/reference type		see X86AddressReferenceTypes
 *	r		3		register info				see X86RegisterReferences
 *	m		3		memory reference info		see X86AddressReferenceTypes
 *	i		6	 	instruction type  			
 *	s		1		source/signed				register=0,reference=1 / 1='+',0='-'
 *	c		1	 	memory class  				8-bit=0, 16-bit=1
 * 	d		16/32	offset						(optional)
 *
 *  Instruction code layout
 *  ------------------------------
 *  fedc ba98 7654 3210 
 *  		  iiii iisc ( 8 bits)
 *  ttrr rmmm iiii iisc (16 bits)
 *
 * ------------------------------------------------------------------
 * instruction				code 		tt rrr mmm	iiiii x s c dddd
 * ------------------------------------------------------------------
 * and	[bx],al				0720		00 000 111 	00100 0 0 0
 * and	[bx],ax				0721		00 000 111 	00100 0 0 1
 * and	al,[bx]				0722		00 000 111 	00100 0 1 0
 * and	ax,[bx]				0723		00 000 111 	00100 0 1 1
 * and	al,nn				  24					00100 1 0 0 nn
 * and	ax,nnnn				  25					00100 1 0 1 nnnn 
 * es:						  26					00100 1 1 0
 * daa						  27					00100 1 1 1
 * sub	[bx],al				0728		00 000 111 	00101 0 0 0
 * sub	[bx],ax				0729		00 000 111 	00101 0 0 1
 * sub	al,[bx]				072A		00 000 111 	00101 0 1 0
 * sub	ax,[bx]				072B		00 000 111 	00101 0 1 1
 * sub	al,nn				  2C					00101 1 0 0 nn
 * sub	ax,nnnn				  2D					00101 1 0 1 nnnnn
 * cs:						  2E					00101 1 1 0
 * das						  2F					00101 1 1 1
 * </pre>
 * @author lawrence.daniels@gmail.com
 */
public class Decoder20 implements Decoder {
	// define the complex instruction code constants
	private static final int AND_CODE 	= 0x04;	// 00100
	private static final int SUB_CODE 	= 0x05;	// 00101

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpCode decode( final Intel80x86 cpu, X86MemoryProxy proxy ) {
		// get the 8-bit instruction
		final int code8 = proxy.nextByte();
		
		// decode the instruction
		switch( code8 ) {
			// AND AL,nn
			case 0x24:	return new AND( cpu.AL, nextValue8( proxy ) );
			// AND AX,nnnn
			case 0x25:	return new AND( cpu.AX, nextValue16( proxy ) );
			// ES:
			case 0x26:	return ES.getInstance();
			// DAA
			case 0x27:	return DAA.getInstance();
			// SUB AL,nn
			case 0x2C:	return new SUB( cpu.AL, nextValue8( proxy ) );
			// SUB AX,nnnn
			case 0x2D:	return new SUB( cpu.AX, nextValue16( proxy ) );
			// CS:
			case 0x2E:	return CS.getInstance();
			// DAS
			case 0x2F:	return DAS.getInstance();
			// for all others ...
			default:	return decodeComplexCode( cpu, proxy, code8 );
		}
	}
	
	/**
	 * Decodes complex instruction codes between 00h and 0Fh
	 * @param cpu the given {@link Intel80x86 CPU} instance 
	 * @param proxy the given {@link X86MemoryProxy memory proxy}
	 * @param code8 the given 8-bit instruction code
	 * @return the resultant {@link OpCode opCode}, 
	 * or <tt>null</tt> if not found
	 */
	private OpCode decodeComplexCode( final Intel80x86 cpu, final X86MemoryProxy proxy, final int code8 ) {
		// instruction code layout
		// -----------------------------
		// fedc ba98 7654 3210 (16 bits)
		// mmrr rmmm iiii ivsc 
		
		// get the 16-bit instruction code
		final int code16 = proxy.nextWord( code8 );
		
		// get data elements
		final Operand[] operands = lookupOperands( cpu, proxy, code16, true, false );
		
		// identify the destination and source operands
		final Operand dest = operands[0];
		final Operand src = operands[1];
		
		// get the instruction code
		// code: .... .... iiii i... (mask = 0000 0000 1111 1000) 
		final int insCode = ( code16 & 0x00F8 ) >> 3;	
		
		// evaluate the instruction
		switch( insCode ) {
			case AND_CODE:	return new AND( dest, src ); 
			case SUB_CODE:	return new SUB( dest, src ); 
			default:		throw new UnhandledByteCodeException( code16 );
		}
	}

}
