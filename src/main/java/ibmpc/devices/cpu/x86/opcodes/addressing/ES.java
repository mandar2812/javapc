package ibmpc.devices.cpu.x86.opcodes.addressing;

import ibmpc.devices.cpu.Intel80x86;
import ibmpc.exceptions.X86AssemblyException;

/**
 * ES:
 *
 * @author lawrence.daniels@gmail.com
 */
public class ES extends SegmentOverrideOpCode {
    private static ES instance = new ES();

    /**
     * Private constructor
     */
    private ES() {
        super();
    }

    /**
     * @return the singleton instance of this class
     */
    public static ES getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final Intel80x86 cpu) throws X86AssemblyException {
        override(cpu, cpu.ES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ES:";
    }

}