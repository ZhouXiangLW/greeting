package clazzForTest;

import Annotions.CreatedOnTheFly;

public class ClassWithTwoDependencies {

    @CreatedOnTheFly
    private BaseDependency baseDependency1;

    @CreatedOnTheFly
    private BaseDependency baseDependency2;

    public boolean isInjected() {
        return baseDependency1 != null && baseDependency2 != null;
    }
}
