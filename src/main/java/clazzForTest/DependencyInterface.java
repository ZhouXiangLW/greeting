package clazzForTest;

import Annotions.CreatedOnTheFly;

public class DependencyInterface {
    @CreatedOnTheFly
    private InterfaceForCommonClass interfaceForCommonClass;

    public boolean isInjected() {
        return interfaceForCommonClass != null;
    }
}
