import clazzForTest.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IoCContextImplTestForQuestion3 {
    private IoCContext context;

    @BeforeEach
    void setUp() {
        context = new IoCContextImpl();
    }

    @Test
    void should_use_interface_to_register() throws InstantiationException, IllegalAccessException {
        context.registerBean(InterfaceForCommonClass.class, CommonClass.class);
        InterfaceForCommonClass instance = context.getBean(InterfaceForCommonClass.class);
        assertEquals(CommonClass.class, instance.getClass());
    }

    @Test
    void should_been_override() throws InstantiationException, IllegalAccessException {
        context.registerBean(InterfaceForCommonClass.class, CommonClass.class);
        context.registerBean(InterfaceForCommonClass.class, CommonClass2.class);
        InterfaceForCommonClass instance = context.getBean(InterfaceForCommonClass.class);
        assertEquals(CommonClass2.class, instance.getClass());
    }

    @Test
    void should_use_abstract_class_to_register() throws InstantiationException, IllegalAccessException {
        context.registerBean(AbstractClass.class, AbstractClassImpl.class);
        AbstractClass instance = context.getBean(AbstractClass.class);
        assertEquals(AbstractClassImpl.class, instance.getClass());
    }

    @Test
    void should_register_user_common_class_with_impl() throws InstantiationException, IllegalAccessException {
        context.registerBean(CommonClass.class, CommonClass3.class);
        CommonClass instance = context.getBean(CommonClass.class);
        assertEquals(CommonClass3.class, instance.getClass());
    }

    @Test
    void should_be_override_with_common_class() throws InstantiationException, IllegalAccessException {
        context.registerBean(CommonClass.class, CommonClass3.class);
        context.registerBean(CommonClass.class);
        assertEquals(CommonClass.class, context.getBean(CommonClass.class).getClass());
    }

    @Test
    void should_throw_if_super_class_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> {context.registerBean(null, CommonClass.class);},
                "beanClazz is mandatory");
    }

    @Test
    void should_throw_if_impl_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> {context.registerBean(InterfaceForCommonClass.class, null);},
                "beanClazz is mandatory");
    }

    @Test
    void should_throw_if_impl_if_interface() {
        Class<InterfaceClass> Interface = InterfaceClass.class;
        assertThrows(IllegalArgumentException.class,
                () -> {context.registerBean(Interface, Interface);},
                "beanClass " + Interface.getName() + "is abstract");
    }

    @Test
    void should_throw_if_impl_is_abstract_clazz() {
        Class<AbstractClass> abstractClazz = AbstractClass.class;
        assertThrows(IllegalArgumentException.class,
                () -> {context.registerBean(abstractClazz, abstractClazz);},
                "beanClass " + abstractClazz.getName() + " is abstract");
    }



}
