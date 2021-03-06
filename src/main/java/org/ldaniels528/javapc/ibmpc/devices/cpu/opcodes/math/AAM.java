package org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.math;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.AbstractOpCode;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.FlagsAffected;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.RegistersAffected;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystem;

/**
 * <pre>
 * Ascii Adjust for Multiplication
 *
 * Usage:  AAM
 * Modifies flags: PF SF ZF (AF,CF,OF undefined)
 *
 * AH := AL / 10
 * AL := AL mod 10
 *
 * Used after multiplication of two unpacked decimal numbers, this
 * instruction adjusts an unpacked decimal number.  The high order
 * nibble of each byte must be zeroed before using this instruction.
 * This instruction is also known to have an undocumented behavior.
 *
 * See <a href="http://www.emu8086.com/assembly_language_tutorial_assembler_reference/8086_instruction_set.html">AAM</a>
 * </pre>
 *
 * @author lawrence.daniels@gmail.com
 */
@FlagsAffected({"AF", "CF", "OF", "PF", "SF", "ZF"})
@RegistersAffected({"AL", "AH"})
public class AAM extends AbstractOpCode {
    private static AAM instance = new AAM();

    /**
     * Private constructor
     */
    private AAM() {
        super();
    }

    /**
     * @return the singleton instance of this class
     */
    public static AAM getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(IbmPcSystem system, final I8086 cpu) {
        final int AL = cpu.AL.get();
        cpu.AH.set(AL / 10);
        cpu.AL.set(AL % 10);
    }

}
