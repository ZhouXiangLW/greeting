package clazzForTest;

import Annotions.CreatedOnTheFly;

public class Dependency {
    @CreatedOnTheFly
    private BaseDependency baseDependency;

    public boolean isInjected() {
        return baseDependency != null;
    }
}
