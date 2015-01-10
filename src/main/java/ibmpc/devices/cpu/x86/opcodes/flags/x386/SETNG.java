package ibmpc.devices.cpu.x86.opcodes.flags.x386;

import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.operands.Operand;
import ibmpc.devices.cpu.x86.opcodes.AbstractOpCode;

/**
 * SETNG
 * Set byte if not greater (ZF=1 or SF<>OF)
 *
 * @author ldaniels
 */
public class SETNG extends AbstractOpCode {
    private final Operand dest;

    /**
     * Private constructor
     */
    public SETNG(final Operand dest) {
        this.dest = dest;
    }

    /*
     * (non-Javadoc)
     * @see ibmpc.devices.cpu.OpCode#execute(ibmpc.devices.cpu.Intel80x86)
     */
    public void execute(final Intel80x86 cpu) {
        dest.set(cpu.FLAGS.isZF() || cpu.FLAGS.isOF() != cpu.FLAGS.isSF() ? 1 : 0);
    }

    /*
     * (non-Javadoc)
     * @see ibmpc.devices.cpu.x86.opcodes.AbstractOpCode#toString()
     */
    public String toString() {
        return String.format("SETNG %s", dest);
    }

}