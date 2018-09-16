package clazzForTest;

import Annotions.CreatedOnTheFly;

public class NotLoopDependency {
    @CreatedOnTheFly
    private LoopDependencyA loopDependencyA;

    public boolean isInjected() {
        return loopDependencyA != null;
    }

}
