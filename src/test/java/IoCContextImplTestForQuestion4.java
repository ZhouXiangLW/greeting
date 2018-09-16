import clazzForTest.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IoCContextImplTestForQuestion4 {
    private IoCContext context;

    @BeforeEach
    void setUp() {
        context = new IoCContextImpl();
    }

    @Test
    void should_auto_inject_dependency_of_dependency() throws InstantiationException, IllegalAccessException {
        context.registerBean(BaseDependency.class);
        context.registerBean(Dependency.class);
        context.registerBean(ClassWithDependency.class);
        assertTrue(context.getBean(ClassWithDependency.class).isInjected());
    }

    @Test
    void should_throw_if_dependency_not_registered() {
        context.registerBean(ClassWithDependency.class);
        context.registerBean(BaseDependency.class);
        assertThrows(IllegalStateException.class,
                () -> context.getBean(ClassWithDependency.class));
    }

    @Test
    void should_throws_if_dependency_of_dependency_not_registered() {
        context.registerBean(ClassWithDependency.class);
        context.registerBean(Dependency.class);
        assertThrows(IllegalStateException.class,
                () -> context.getBean(ClassWithDependency.class));
    }

    @Test
    void should_throw_if_has_loop_dependency() {
        context.registerBean(LoopDependencyB.class);
        context.registerBean(LoopDependencyA.class);
        assertThrows(IllegalStateException.class,
                () -> context.getBean(LoopDependencyA.class));
    }

    @Test
    void should_not_influence_on_other_beans() throws InstantiationException, IllegalAccessException {
        context.registerBean(LoopDependencyB.class);
        context.registerBean(LoopDependencyA.class);

        context.registerBean(BaseDependency.class);
        context.registerBean(Dependency.class);
        context.registerBean(ClassWithDependency.class);

        assertThrows(IllegalStateException.class,
                () -> context.getBean(LoopDependencyA.class));
        assertTrue(context.getBean(ClassWithDependency.class).isInjected());

    }
}
