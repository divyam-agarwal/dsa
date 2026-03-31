import backtracking.ValidIpAddresses;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ValidIpAddresses sol = new ValidIpAddresses();

        System.out.println(sol.restoreIpAddresses("25525511135"));
        // Expected: [255.255.11.135, 255.255.111.35]

        System.out.println(sol.restoreIpAddresses("0000"));
        // Expected: [0.0.0.0]

        System.out.println(sol.restoreIpAddresses("101023"));
        // Expected: [1.0.10.23, 1.0.102.3, 10.1.0.23, 10.10.2.3, 101.0.2.3]
    }
}