package clazzForTest;

import java.io.Closeable;
import java.io.IOException;

public class ThrowWhenClose implements Closeable {

    private boolean isClose = false;

    @Override
    public void close() throws IOException {
        isClose = true;
        throw new IOException();
    }

    public boolean isClose() {
        return isClose;
    }
}
