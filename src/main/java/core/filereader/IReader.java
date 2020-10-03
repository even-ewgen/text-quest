package core.filereader;

import java.io.IOException;
import java.util.List;

public interface IReader<T> {

    List<T> read() throws IOException;
}
