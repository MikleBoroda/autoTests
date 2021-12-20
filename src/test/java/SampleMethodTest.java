import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleMethodTest {


    @Test
    public void positiveTest() {

    }

    @Test
    public void assertTest() {
        Assert.assertEquals(3, 5);
    }

    @Test
    public void throwExceptionTest() {
        throw new IllegalStateException("Text exception");
    }
}
