package clazzForTest;

import Annotions.CreatedOnTheFly;

public class ClassWithDependency {
    @CreatedOnTheFly
    private Dependency dependency;

    public boolean isInjected() {
        return dependency != null && dependency.isInjected();
    }
}