import Annotions.CreatedOnTheFly;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Optional;

public class IoCContextImpl implements IoCContext {

    private HashSet<ClassInfo> clazzInfos = new HashSet<>();
    private HashSet<Class<?>> dependencys = new HashSet<>();

    @Override
    public void registerBean(Class<?> beanClazz) {
        checkForRegister(new ClassInfo(beanClazz));
        ClassInfo toBeRegistered = new ClassInfo(beanClazz);
        beforeRegistered(toBeRegistered);
        clazzInfos.add(new ClassInfo(beanClazz));
    }

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) {
        ClassInfo toBeRegistered = new ClassInfo(resolveClazz, beanClazz);
        checkForRegister(toBeRegistered);
        beforeRegistered(toBeRegistered);
        clazzInfos.add(toBeRegistered);
    }


    @Override
    public <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException {
        T result = doGetBean(resolveClazz);
        dependencys.clear();
        return result;
    }

    public <T> T doGetBean(Class<T> resolveClazz) throws InstantiationException, IllegalAccessException {
        checkForGet(resolveClazz);
        ClassInfo info = getClazzInfo(resolveClazz).orElseThrow(IllegalArgumentException::new);
        info.setCalled(true);
        Object newInstance = info.hasImplement() ? getNewInstance(info.getImpl()) :
                getNewInstance(info.getClazz());
        return (T) newInstance;
    }

    private Object getNewInstance(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        Object instance = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CreatedOnTheFly.class)) {
                Class<?> c = field.getType();
                if (dependencys.contains(c)) {
                    dependencys.clear();
                    throw new IllegalStateException();
                }
                dependencys.add(c);
                field.setAccessible(true);
                field.set(instance, doGetBean(c));
            }
        }
        return instance;
    }

    private <T> void checkForGet(Class<T> resolveClazz) {
        if (resolveClazz == null) {
            throw new IllegalArgumentException();
        }
        if (!clazzInfos.contains(new ClassInfo(resolveClazz))) {
            throw new IllegalStateException();
        }
    }

    private void checkForRegister(ClassInfo classInfo){
        if (!classInfo.hasImplement()) {
            checkForImpl(classInfo.getClazz());
        } else {
            checkSuper(classInfo.getClazz());
            checkForImpl(classInfo.getImpl());
        }
    }

    private void checkForImpl(Class<?> beanClazz) {
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

    private void checkSuper(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }
    }

    private void beforeRegistered(ClassInfo toBeRegistered) {
        clazzInfos.remove(toBeRegistered);
    }
}
