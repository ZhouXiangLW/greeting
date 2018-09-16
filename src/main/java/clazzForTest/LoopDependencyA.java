package clazzForTest;

import Annotions.CreatedOnTheFly;

public class LoopDependencyA {
    @CreatedOnTheFly
    private LoopDependencyB loopDependencyB;
}
