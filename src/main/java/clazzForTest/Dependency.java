package clazzForTest;

import Annotions.CreatedOnTheFly;

public class Dependency {
    @CreatedOnTheFly
    private BaseDependency baseDependency;

    public BaseDependency getBaseDependency2() {
        return baseDependency;
    }

    public boolean isInjected() {
        return baseDependency != null;
    }
}
