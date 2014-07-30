package ibmpc.devices.cpu.x86.decoder;

import static ibmpc.devices.cpu.x86.decoder.DecoderUtil.nextAddressShort;
import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.OpCode;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JA;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JBE;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JC;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JG;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JGE;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JL;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JLE;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JNC;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JNO;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JNS;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JNZ;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JO;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JPE;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JPO;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JS;
import ibmpc.devices.cpu.x86.opcodes.flow.jump.JZ;
import ibmpc.devices.memory.X86MemoryProxy;

/**
 * <pre>
 * Decodes instruction codes between 70h and 7Fh
  *	---------------------------------------------------------------------------
 *	type	size		description 			comments
 *	---------------------------------------------------------------------------
 *	i		4-bit 		instruction type  		inc=01000,dec=01001
 *	z		4-bit		unknown					ax=000b, cx=001b
 * 
 *  Instruction code layout
 *  -----------------------
 *  7654 3210 (8 bits)
 *  iiii irrr 
 *  
 * ---------------------------------------------------------------------------
 * instruction			code 	iiii zzzz 
 * ---------------------------------------------------------------------------
 * jo  nn				70		0111 0000
 * jno nn				71		0111 0001
 * jc  nn				72		0111 0010
 * jnc nn				73		0111 0011
 * jz  nn				74		0111 0100
 * jnz nn				75		0111 0101
 * jbe nn				76		0111 0110
 * ja  nn				77		0111 0111
 * js  nn				78		0111 1000
 * jns nn				79		0111 1001
 * jpe nn				7A		0111 1010
 * jpo nn				7B		0111 1011
 * jl  nn				7C		0111 1100
 * jge nn				7D		0111 1101
 * jle nn				7E		0111 1110
 * jg  nn				7F		0111 1111
 * </pre>
 * @author lawrence.daniels@gmail.com
 */
public class Decoder70 implements Decoder {

	/* (non-Javadoc)
	 * @see ibmpc.devices.cpu.decoders.I8086Decoder#decode(ibmpc.devices.cpu.VirtualCPU)
	 */
	public OpCode decode( final Intel80x86 cpu, final X86MemoryProxy proxy ) {
		// get the 8-bit instruction code
		final int code8 = proxy.nextByte();
		
		// evaluate the instruction
		switch( code8 ) {
			case 0x70: 	return new JO( nextAddressShort( proxy ) ); 
			case 0x71: 	return new JNO( nextAddressShort( proxy ) ); 
			case 0x72: 	return new JC( nextAddressShort( proxy ) ); 
			case 0x73: 	return new JNC( nextAddressShort( proxy ) ); 
			case 0x74: 	return new JZ( nextAddressShort( proxy ) ); 
			case 0x75: 	return new JNZ( nextAddressShort( proxy ) ); 
			case 0x76: 	return new JBE( nextAddressShort( proxy ) ); 
			case 0x77: 	return new JA( nextAddressShort( proxy ) ); 
			case 0x78: 	return new JS( nextAddressShort( proxy ) ); 
			case 0x79: 	return new JNS( nextAddressShort( proxy ) ); 
			case 0x7A: 	return new JPE( nextAddressShort( proxy ) ); 
			case 0x7B: 	return new JPO( nextAddressShort( proxy ) ); 
			case 0x7C: 	return new JL( nextAddressShort( proxy ) ); 
			case 0x7D: 	return new JGE( nextAddressShort( proxy ) ); 
			case 0x7E:	return new JLE( nextAddressShort( proxy ) ); 
			case 0x7F:	return new JG( nextAddressShort( proxy ) ); 
			default:	throw new UnhandledByteCodeException( code8 );
		}
	}

}
