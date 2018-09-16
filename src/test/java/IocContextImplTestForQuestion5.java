import clazzForTest.AnotherDependency;
import clazzForTest.BaseDependency;
import clazzForTest.DerivedDependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IocContextImplTestForQuestion5 {
    private IoCContext context;

    @BeforeEach
    void setUp() {
        context = new IoCContextImpl();
    }

    @Test
    void should_auto_injected_for_supper_class() throws InstantiationException, IllegalAccessException {
        context.registerBean(BaseDependency.class);
        context.registerBean(DerivedDependency.class);
        assertTrue(context.getBean(DerivedDependency.class).isInjected());
    }

    @Test
    void should_first_init_super_class_field() throws InstantiationException, IllegalAccessException {
        context.registerBean(BaseDependency.class);
        context.registerBean(DerivedDependency.class);
        DerivedDependency derivedDependency = context.getBean(DerivedDependency.class);
        assertTrue(derivedDependency.getBaseDependencyForDerived().getDate().after(
                derivedDependency.getBaseDependency2().getDate()
        ));
    }

    @Test
    void should_injected_if_derived_class_and_super_class_based_od_same_class() throws InstantiationException, IllegalAccessException {
        context.registerBean(BaseDependency.class);
        context.registerBean(AnotherDependency.class);
        assertTrue(context.getBean(AnotherDependency.class).isInjected());
    }
}
