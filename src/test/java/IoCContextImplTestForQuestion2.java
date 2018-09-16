import clazzForTest.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IoCContextImplTestForQuestion2 {

    private IoCContext context;

    @BeforeEach
    void setUp() {
        context = new IoCContextImpl();
    }

    @Test
    void should_get_instance_from_registered_class() throws InstantiationException, IllegalAccessException {
        Class<CommonClass> toBeRegistered = CommonClass.class;
        context.registerBean(toBeRegistered);
        CommonClass commonClass = context.getBean((Class<CommonClass>)toBeRegistered);
        assertNotNull(commonClass);
        assertEquals(toBeRegistered, commonClass.getClass());
    }

    @Test
    void should_throw_when_bean_clazz_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> {context.registerBean(null);},
                "beanClazz is mandatory");
    }

    @Test
    void should_throw_when_bean_clazz_is_interface() {
        Class<InterfaceClass> Interface = InterfaceClass.class;
        assertThrows(IllegalArgumentException.class,
                () -> {context.registerBean(Interface);},
                "beanClass " + Interface.getName() + "is abstract");
    }

    @Test
    void should_throw_if_bean_clazz_is_abstract_clazz() {
        Class<AbstractClass> abstractClazz = AbstractClass.class;
        assertThrows(IllegalArgumentException.class,
                () -> {context.registerBean(abstractClazz);},
                "beanClass " + abstractClazz.getName() + " is abstract");
    }

    @Test
    void should_throw_if_bean_class_has_no_default_constructor() {
        Class<ClassWithNoDefaultConstructor> clazz = ClassWithNoDefaultConstructor.class;
        assertThrows(IllegalArgumentException.class,
                () -> {context.registerBean(clazz);},
                clazz.getName() + " has no default constructor");
    }

    @Test
    void should_throw_if_resolve_clazz_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> context.getBean(null));
    }

    @Test
    void should_throw_if_resolve_clazz_has_not_register() {
        assertThrows(IllegalStateException.class,
                () -> context.getBean(NotRegisteredClass.class));
    }

    @Test
    void should_throw_if_constructor_throw() {
        Class<ThrowInConstructor> clazz = ThrowInConstructor.class;
        context.registerBean(clazz);
        assertThrows(MyException.class,
                () -> context.getBean(clazz));
    }

    @Test
    void should_throw_if_get_bean_been_called_twice() throws InstantiationException, IllegalAccessException {
        Class<CommonClass> clazz = CommonClass.class;
        context.registerBean(clazz);
        context.getBean(clazz);
        assertThrows(IllegalStateException.class,
                () -> context.registerBean(clazz));
    }

    @Test
    void should_get_different_class() throws InstantiationException, IllegalAccessException {
        context.registerBean(CommonClass.class);
        context.registerBean(CommonClass2.class);
        CommonClass common = context.getBean(CommonClass.class);
        CommonClass2 common2 = context.getBean(CommonClass2.class);
        assertNotNull(common);
        assertNotNull(common2);
        assertEquals(CommonClass.class, common.getClass());
        assertEquals(CommonClass2.class, common2.getClass());
    }

    @Test
    void should_get_different_instance_when_get_twice_for_same_clazz() throws InstantiationException, IllegalAccessException {
        context.registerBean(CommonClass.class);
        assertNotSame(context.getBean(CommonClass.class), context.getBean(CommonClass.class));
    }
}
