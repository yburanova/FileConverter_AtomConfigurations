package kirklandfile.FileConverter_AtomConfigurations.readers;

import java.io.File;
import java.io.IOException;

/**
 * Created by buranova on 07.12.2016.
 */
public interface ReaderCommand
{
    /**
     * reads files with the atomic configurations
     * @param file a path to the file
     * @return a matrix with the atomic types and positions
     * @throws IOException
     */
    double[][] read(File file) throws IOException;

}
