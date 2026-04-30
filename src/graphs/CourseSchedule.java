package graphs;

// InterviewBit: Possibility of Finishing All Courses Given Prerequisites
// https://www.interviewbit.com/problems/possibility-of-finishing-all-courses-given-prerequisites/
//
// Given A courses (labelled 1..A) and a list of prerequisite pairs B,
// where B[i] = [a, b] means course a requires course b to be taken first,
// determine whether it is possible to finish all courses.
//
// It is possible if and only if the prerequisite graph contains no cycle
// (i.e. the graph is a DAG).
//
// Approach: DFS with 3-colour marking (WHITE/GRAY/BLACK) or Kahn's BFS
// topological sort — if all nodes are processed, no cycle exists.
//
// Input:
//   A — number of courses
//   B — prerequisite pairs, B[i] = [a, b] means a depends on b (edge b → a)
// Output: 1 if all courses can be finished, 0 if there is a cycle

import java.util.ArrayList;
import java.util.List;

public class CourseSchedule {

    public int solve(int A, ArrayList<Integer> B, ArrayList<Integer> C) {
        //check cycle using back edge
        List<List<Integer>> graph = new ArrayList<>();
        for(int i = 0;i<=A;i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0;i<B.size();i++){
            int u = B.get(i);
            int v = C.get(i);
            graph.get(u).add(v);
            //graph.get(v).add(u);
        }
        //if cycle, dfs returns false
        int[] visited = new int[A+1];
        for(int i = 1;i<=A;i++){
            boolean b = dfs(i, visited, graph);
            if(!b)
                return 0;
        }

        return 1;
    }

    public boolean dfs(int u, int[] visited, List<List<Integer>> graph){
        visited[u] = 1;
        boolean nocycle = true;
        for(int v: graph.get(u)){
            if(visited[v]==0){
                nocycle = dfs(v, visited, graph);
                if(!nocycle)
                    return false;
            }
            else if(visited[v]==1){
                //cycle detected
                return false;
            }
            else{
                // do nothing
            }
        }

        visited[u] = 2;
        return nocycle;
    }
}
