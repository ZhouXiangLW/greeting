import java.util.Objects;

public class ClassInfo {
    private Class<?> clazz;
    private boolean isCalled;
    private Class<?> impl;

    public ClassInfo(Class<?> clazz, Class<?> impl) {
        this.clazz = clazz;
        this.impl = impl;
        this.isCalled = false;
    }

    public ClassInfo(Class<?> clazz) {
        this.clazz = clazz;
        this.isCalled = false;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean isCalled() {
        return isCalled;
    }

    public void setCalled(boolean called) {
        this.isCalled = called;
    }

    public Class<?> getImpl() {
        return impl;
    }

    public void setImpl(Class<?> impl) {
        this.impl = impl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassInfo)) return false;
        ClassInfo info = (ClassInfo) o;
        return Objects.equals(clazz, info.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }
}
