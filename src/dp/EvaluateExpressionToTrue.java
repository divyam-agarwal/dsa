package dp;

public class EvaluateExpressionToTrue {

    // ─── Solution ─────────────────────────────────────────────────────────────
    // Given a boolean expression of T/F symbols and &/|/^ operators, count the
    // number of ways to parenthesize it so that it evaluates to true.
    // Return the answer mod 1003.
    //
    // TODO

    public void combine2Exp(String A, int[][] T, int[][] F, int l1, int r1, int l2, int r2, char operand){
        int total = (T[l1][r1] + F[l1][r1]) * (T[l2][r2] + F[l2][r2]);
        if(operand=='|'){
            T[l1][r2] += T[l1][r1]*(T[l2][r2] + F[l2][r2]) + F[l1][r1]*T[l2][r2];
            F[l1][r2] += F[l1][r1]*F[l2][r2];
        }
        if(operand=='&'){
            T[l1][r2] += T[l1][r1]*T[l2][r2];
            F[l1][r2] += total - T[l1][r1]*T[l2][r2] ;
        }if(operand=='^'){
            T[l1][r2] += T[l1][r1]*F[l2][r2] + F[l1][r1]*T[l2][r2];
            F[l1][r2] += total - (T[l1][r1]*F[l2][r2] + F[l1][r1]*T[l2][r2]);
        }
        T[l1][r2]%= 1003;
        F[l1][r2]%=1003;
    }

    public int cnttrue(String A) {
        int operands = A.length()/2 + 1;
        int[][] T = new int[operands][operands];
        int[][] F = new int[operands][operands];

        //base case
        for(int i = 0;i<operands;i++){
            if(A.charAt(2*i)=='T'){
                T[i][i] = 1;
            }
            if(A.charAt(2*i)=='F'){
                F[i][i] = 1;
            }
        }

        for(int k = 1;k<operands;k++){
            int i =0, j = k;
            while(i<operands && j<operands){
                //split exp i,j
                for(int m = i;m<j;m++){
                    //i->m, m+1->j
                    char operand = A.charAt(2*m+1);
                    combine2Exp(A, T, F, i,m,m+1,j,operand);
                }
                i++;
                j++;
            }
        }
        return T[0][operands-1];
    }
}
