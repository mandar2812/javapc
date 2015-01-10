package ibmpc.devices.cpu.x86.opcodes.string;

import ibmpc.devices.cpu.Intel80x86;
import ibmpc.devices.cpu.x86.opcodes.AbstractOpCode;
import ibmpc.devices.memory.IbmPcRandomAccessMemory;

/**
 * Scan String Byte
 * <pre>
 * Usage:  SCAS    string
 *         SCASB
 *         SCASW
 *         SCASD   (386+)
 * Modifies flags: AF CF OF PF SF ZF
 *
 * Compares value at ES:DI (even if operand is specified) from the
 * accumulator and sets the flags similar to a subtraction.  DI is
 * incremented/decremented based on the instruction format (or
 * operand size) and the state of the Direction Flag.  Use with REP
 * prefixes.
 * </pre>
 */
public class SCASB extends AbstractOpCode {
    private static SCASB instance = new SCASB();

    /**
     * Private constructor
     */
    private SCASB() {
        super();
    }

    /**
     * @return the singleton instance of this class
     */
    public static SCASB getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final Intel80x86 cpu) {
        // get the memory instance
        final IbmPcRandomAccessMemory memory = cpu.getRandomAccessMemory();

        // get the byte from register AL
        final int data0 = cpu.AL.get();

        // put the data word into ES:[DI]
        final int data1 = memory.getByte(cpu.ES.get(), cpu.DI.get());

        // compare the data
        cpu.FLAGS.compare(data0, data1);

        // setup increment/decrement value
        final int delta = cpu.FLAGS.isDF() ? -1 : 1;

        // increment/decrement pointer
        cpu.DI.add(delta);
    }

}