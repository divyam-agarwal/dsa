package dp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxPathInTriangleTest {
    MaxPathInTriangle sol = new MaxPathInTriangle();

    @Test
    void TenByTen(){
        List<List<Integer>> A = new ArrayList<>();

        A.add(Arrays.asList(725, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        A.add(Arrays.asList(479, 359, 0, 0, 0, 0, 0, 0, 0, 0));
        A.add(Arrays.asList(963, 465, 706, 0, 0, 0, 0, 0, 0, 0));
        A.add(Arrays.asList(146, 282, 828, 962, 0, 0, 0, 0, 0, 0));
        A.add(Arrays.asList(492, 996, 943, 828, 437, 0, 0, 0, 0, 0));
        A.add(Arrays.asList(392, 605, 903, 154, 293, 383, 0, 0, 0, 0));
        A.add(Arrays.asList(422, 717, 719, 896, 448, 727, 772, 0, 0, 0));
        A.add(Arrays.asList(539, 870, 913, 668, 300, 36, 895, 704, 0, 0));
        A.add(Arrays.asList(812, 323, 334, 674, 665, 142, 712, 254, 869, 0));
        A.add(Arrays.asList(548, 645, 663, 758, 38, 860, 724, 742, 530, 779));

        assertEquals(7553, sol.solve(A));
    }

    @Test
    void FourByFour(){
        List<List<Integer>> A = new ArrayList<>();

        A.add(Arrays.asList(3, 0, 0, 0));
        A.add(Arrays.asList(7, 4, 0, 0));
        A.add(Arrays.asList(2, 4, 6, 0));
        A.add(Arrays.asList(8, 5, 9, 3));

        assertEquals(23, sol.solve(A));
    }
}
