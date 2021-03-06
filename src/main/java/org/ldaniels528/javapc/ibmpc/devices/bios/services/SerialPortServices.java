package org.ldaniels528.javapc.ibmpc.devices.bios.services;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.exceptions.X86AssemblyException;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystem;

/**
 * BIOS Serial Communication Services Processor
 * @author lawrence.daniels@gmail.com
 */
public class SerialPortServices implements InterruptHandler {
	private static final SerialPortServices instance = new SerialPortServices();
	
	/**
	 * Private constructor
	 */
	private SerialPortServices() {
		super();
	}
	
	/**
	 * Returns the singleton instance of this class 
	 * @return the singleton instance of this class 
	 */
	public static SerialPortServices getInstance() {
		return instance;
	}
	
	/**
	 * Process the Disk Services Interrupt (INT 14h)
	 * @throws X86AssemblyException
	 */
	public void process(IbmPcSystem system, final I8086 cpu)
	throws X86AssemblyException {
		// determine what to do
		switch( cpu.AH.get() ) {
			default:
				throw new X86AssemblyException( "Invalid call (" + cpu.AH + ")" );
		}
	}
}
