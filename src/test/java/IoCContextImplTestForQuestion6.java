import clazzForTest.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IoCContextImplTestForQuestion6 {
    private IoCContext context;

    @BeforeEach
    void setUp() {
        context = new IoCContextImpl();
    }

    @Test
    void should_close_closeable_object() throws Exception {
        context.registerBean(CloseableObject.class);
        CloseableObject toBeClose = context.getBean(CloseableObject.class);
        context.close();
        assertTrue(toBeClose.isClose());
    }

    @Test
    void should_close_closeable_when_not_closeable_in_context() throws Exception {
        context.registerBean(CloseableObject.class);
        context.registerBean(Dependency.class);
        context.registerBean(BaseDependency.class);
        context.getBean(Dependency.class);
        CloseableObject closeableObject = context.getBean(CloseableObject.class);
        context.close();
        assertTrue(closeableObject.isClose());
    }

    @Test
    void should_close_all_closeable_object_even_one_throw() throws Exception {
        context.registerBean(CloseableObject.class);
        context.registerBean(ThrowWhenClose.class);
        CloseableObject closeableObject = context.getBean(CloseableObject.class);
        ThrowWhenClose throwWhenClose = context.getBean(ThrowWhenClose.class);
        context.close();
        assertTrue(closeableObject.isClose() && throwWhenClose.isClose());
    }

    @Test
    void should_close_when_dependency_need_close() throws Exception {
        context.registerBean(CloseableObject.class);
        context.registerBean(DependencyNeedClose.class);
        DependencyNeedClose dependencyNeedClose = context.getBean(DependencyNeedClose.class);
        context.close();
        assertTrue(dependencyNeedClose.isDependencyClosed());
    }

    @Test
    void should_close_in_reserve_order() throws Exception {
        context.registerBean(CloseableObject.class);
        CloseableObject closeableObject1 = context.getBean(CloseableObject.class);
        CloseableObject closeableObject2 = context.getBean(CloseableObject.class);
        context.close();
        assertTrue(closeableObject1.isClose() && closeableObject2.isClose());
        assertTrue(closeableObject2.getCloseTime().before(closeableObject1.getCloseTime()));
    }
}
