package backtracking;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ValidIpAddressesTest {

    ValidIpAddresses sol = new ValidIpAddresses();

    @Test
    void example1() {
        List<String> result = sol.restoreIpAddresses("25525511135");
        assertTrue(result.containsAll(Arrays.asList("255.255.11.135", "255.255.111.35")));
        assertEquals(2, result.size());
    }

    @Test
    void allZeros() {
        List<String> result = sol.restoreIpAddresses("0000");
        assertEquals(List.of("0.0.0.0"), result);
    }

    @Test
    void example3() {
        List<String> result = sol.restoreIpAddresses("101023");
        List<String> expected = Arrays.asList("1.0.10.23", "1.0.102.3", "10.1.0.23", "10.10.2.3", "101.0.2.3");
        assertTrue(result.containsAll(expected));
        assertEquals(expected.size(), result.size());
    }
}
