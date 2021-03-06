package org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.data;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.AbstractOpCode;
import org.ldaniels528.javapc.ibmpc.exceptions.X86AssemblyException;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystem;

/**
 * Define Word (DW) MACRO Instruction
 *
 * @author ldaniels
 */
public class DW extends AbstractOpCode {
    private int value;

    /**
     * Creates a new define word instruction
     *
     * @param wordValue the given word value
     */
    public DW(final int wordValue) {
        this.value = wordValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(IbmPcSystem system, final I8086 cpu)
            throws X86AssemblyException {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("DW %04X", value);
    }

}
