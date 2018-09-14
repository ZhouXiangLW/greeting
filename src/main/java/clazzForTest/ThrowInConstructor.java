package clazzForTest;

public class ThrowInConstructor {
    public ThrowInConstructor() throws MyException {
        throw new MyException();
    }
}
