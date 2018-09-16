package clazzForTest;

import Annotions.CreatedOnTheFly;

public class DependencyNeedClose {

    @CreatedOnTheFly
    private CloseableObject closeableObject;

    public boolean isDependencyClosed() {
        return closeableObject.isClose();
    }

}
