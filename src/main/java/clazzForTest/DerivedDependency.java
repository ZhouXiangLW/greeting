package clazzForTest;

import Annotions.CreatedOnTheFly;

import java.util.Date;

public class DerivedDependency extends Dependency {
    @CreatedOnTheFly
    private BaseDependency baseDependencyForDerived;

    public BaseDependency getBaseDependencyForDerived() {
        return baseDependencyForDerived;
    }

    public boolean isInjected() {
        return baseDependencyForDerived != null && super.isInjected();
    }

}
