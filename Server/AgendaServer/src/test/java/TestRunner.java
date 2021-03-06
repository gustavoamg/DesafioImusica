import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by gustavoamg on 31/05/17.
 */
public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AgendaServiceTests.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
