import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Optional;

public class IoCContextImpl implements IoCContext {

    private HashSet<ClassInfo> clazzInfos = new HashSet<>();

    @Override
    public void registerBean(Class<?> beanClazz) {
        checkForRegister(beanClazz);
        clazzInfos.add(new ClassInfo(beanClazz));
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException {
        checkForGet(resolveClazz);
        ClassInfo info = getClazzInfo(resolveClazz).get();
        info.setCalled(true);
        return (T) info.getClazz().newInstance();
    }

    private <T> void checkForGet(Class<T> resolveClazz) {
        if (resolveClazz == null) {
            throw new IllegalArgumentException();
        }
        if (!clazzInfos.contains(new ClassInfo(resolveClazz))) {
            throw new IllegalStateException();
        }
    }

    private void checkForRegister(Class<?> beanClazz){
        if (beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }
        if (getClazzInfo(beanClazz).isPresent() && getClazzInfo(beanClazz).get().isCalled()) {
            throw new IllegalStateException();
        }
        if (Modifier.isAbstract(beanClazz.getModifiers())
                || Modifier.isInterface(beanClazz.getModifiers()) ) {
            throw new IllegalArgumentException(beanClazz.getName() + " is abstract");
        }
        try {
            beanClazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("beanClass full " + beanClazz.getName() + " has no default constructor");
        }

    }

    private Optional<ClassInfo> getClazzInfo(Class<?> info) {
        return clazzInfos.stream().filter(c -> c.equals(new ClassInfo(info))).findFirst();
    }
}
