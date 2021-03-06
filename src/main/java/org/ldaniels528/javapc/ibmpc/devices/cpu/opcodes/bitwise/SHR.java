package org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.bitwise;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.devices.cpu.operands.Operand;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.AbstractDualOperandOpCode;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.FlagsAffected;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystem;

/**
 * SHR (Shift Right)
 * <p/>
 * Usage:  SAR     dest,count
 * Modifies flags: CF OF PF SF ZF (AF undefined)
 * <p/>
 * +-----------------------+   +---+
 * +-->| 7 ----------------> 0 |-->| C |
 * |   +-----------------------+   +---+
 * |     |
 * +-----+
 * <p/>
 * Shifts the destination right by "count" bits with the current sign
 * bit replicated in the leftmost bit.  The Carry Flag contains the
 * last bit shifted out.
 * <p/>
 * Clocks                 Size
 * Operands         808x  286   386   486          Bytes
 * <p/>
 * reg,1             2     2     3     3             2
 * mem,1           15+EA   7     7     4            2-4  (W88=23+EA)
 * reg,CL           8+4n  5+n    3     3             2
 * mem,CL        20+EA+4n 8+n    7     4            2-4  (W88=28+EA+4n)
 * reg,immed8        -    5+n    3     2             3
 * mem,immed8        -    8+n    7     4            3-5
 *
 * @author lawrence.daniels@gmail.com
 */
@FlagsAffected({"AF", "CF", "OF", "PF", "SF", "ZF"})
public class SHR extends AbstractDualOperandOpCode {

    /**
     * SHR dst, src
     *
     * @param dest the given {@link Operand destination}
     * @param src  the given {@link Operand source}
     */
    public SHR(final Operand dest, final Operand src) {
        super("SHR", dest, src);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(IbmPcSystem system, final I8086 cpu) {
        // get the source and destination values
        final int value0 = dest.get();
        final int value1 = src.get();

        // shift the destination right by the source value
        dest.set(value0 >> value1);

        // put the right most bit into CF
        cpu.FLAGS.setCF((value0 & 0x01) != 0);
    }

}