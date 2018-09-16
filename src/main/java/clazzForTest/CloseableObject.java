package clazzForTest;

import java.io.Closeable;
import java.util.Date;

public class CloseableObject implements Closeable {
    private boolean isClose = false;
    private Date closeTime;

    @Override
    public void close() {
        closeTime = new Date();
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) { }
        isClose = true;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public boolean isClose() {
        return isClose;
    }
}
