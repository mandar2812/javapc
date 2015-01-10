/**
 *
 */
package ibmpc.devices.cpu.x86.opcodes.math;

import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.operands.Operand;
import ibmpc.devices.cpu.x86.opcodes.AbstractOpCode;
import ibmpc.devices.cpu.x86.opcodes.system.INT;
import ibmpc.exceptions.X86AssemblyException;

import static ibmpc.devices.cpu.operands.Operand.SIZE_16BIT;
import static ibmpc.devices.cpu.operands.Operand.SIZE_8BIT;

/**
 * <pre>
 * Unsigned divide.
 *
 * Algorithm:
 *
 * 	when operand is a byte:
 * 		AL = AX / operand
 * 		AH = remainder (modulus)
 *
 * 	when operand is a word:
 * 		AX = (DX AX) / operand
 * 		DX = remainder (modulus)
 *
 * Example:
 * 	MOV AX, 203   ; AX = 00CBh
 * 	MOV BL, 4
 * 	DIV BL        ; AL = 50 (32h), AH = 3
 * 	RET
 * </pre>
 *
 * @author lawrence.daniels@gmail.com
 */
public class DIV extends AbstractOpCode {
    private final Operand operand;

    /**
     * DIV <i>operand</i>
     *
     * @param operand the given {@link Operand operand}
     */
    public DIV(final Operand operand) {
        this.operand = operand;
    }

    /*
     * (non-Javadoc)
     * @see ibmpc.devices.cpu.OpCode#execute(ibmpc.devices.cpu.Intel80x86)
     */
    public void execute(final Intel80x86 cpu)
            throws X86AssemblyException {
        final int value0;
        final int value1;

        switch (operand.size()) {
            // is it operand 8-bit?
            case SIZE_8BIT:
                // get the divisor and operand
                value0 = cpu.AX.get();
                value1 = operand.get();

                // put the values in AL and AH
                if (value1 != 0) {
                    cpu.AL.set(value0 / value1);
                    cpu.AH.set(value0 % value1);
                } else {
                    // call interrupt 0
                    cpu.execute(INT.DIVBYZR);
                }
                break;

            // is it operand 16-bit?
            case SIZE_16BIT:
                // get the divisor and operand
                value0 = (cpu.DX.get() << 16) | cpu.AX.get();
                value1 = operand.get();

                // put the values in AL and AH
                if (value1 != 0) {
                    cpu.AL.set(value0 / value1);
                    cpu.AH.set(value0 % value1);
                } else {
                    // call interrupt 0
                    cpu.execute(INT.DIVBYZR);
                }
                break;

            // unhandled
            default:
                throw new X86AssemblyException("Unhandled operand size " + operand.size());
        }
    }

}