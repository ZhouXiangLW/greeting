package clazzForTest;

import Annotions.CreatedOnTheFly;

public class AnotherDependency extends Dependency {
    @CreatedOnTheFly
    private BaseDependency baseDependency2;

    public BaseDependency getBaseDependency2() {
        return baseDependency2;
    }

    public boolean isInjected() {
        return baseDependency2 != null && super.isInjected();
    }

}
