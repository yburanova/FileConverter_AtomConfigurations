package kirklandfile.FileConverter_AtomConfigurations.writers;

import java.io.File;
import java.io.IOException;

/**
 * Created by buranova on 07.12.2016.
 */
public interface WriterCommand
{
    void write(File file, double[][] atomicPositions) throws IOException;
}
