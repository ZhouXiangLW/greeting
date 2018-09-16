package clazzForTest;

import Annotions.CreatedOnTheFly;

public class LoopDependencyB {
    @CreatedOnTheFly
    private LoopDependencyA loopDependencyA;
}
