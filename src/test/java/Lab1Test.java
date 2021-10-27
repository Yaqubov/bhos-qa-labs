import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Lab1Test {
    @Test
    void squareRootTest() {
        Lab1 sqrt = new Lab1();
        Assertions.assertEquals(-1e8, sqrt.squareRoot(-5));
    }
}
