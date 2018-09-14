import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;

public class IoCContextImpl implements IoCContext {

    private HashSet<Class<?>> clazzInfo = new HashSet<>();
    private HashMap<Class<?>, Boolean> status = new HashMap<>();

    @Override
    public void registerBean(Class<?> beanClazz) {
        checkForRegister(beanClazz);
        clazzInfo.add(beanClazz);
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException {
        checkForGet(resolveClazz);
        status.put(resolveClazz, true);
        return (T) getClazzInfo(resolveClazz).newInstance();
    }

    private <T> void checkForGet(Class<T> resolveClazz) {
        if (resolveClazz == null) {
            throw new IllegalArgumentException();
        }
        if (!clazzInfo.contains(resolveClazz)) {
            throw new IllegalStateException();
        }
    }

    private void checkForRegister(Class<?> beanClazz){
        if (beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }
        if (status.get(beanClazz) != null) {
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

    private Class<?> getClazzInfo(Class<?> info) {
        return clazzInfo.stream().filter(c -> c.equals(info)).findFirst().get();
    }
}
