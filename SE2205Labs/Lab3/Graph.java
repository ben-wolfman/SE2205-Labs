import java.util.*;

public class Graph {

    private HashMap<Node, LinkedList<Node>> adjacencyMap;
    public Graph() {
        adjacencyMap = new HashMap<>();
    }

    public void addEdgeHelper(Node a, Node b) {
        LinkedList<Node> tmp = adjacencyMap.get(a);

        if (tmp != null) {
            tmp.remove(b);
        }
        else tmp = new LinkedList<>();
        tmp.add(b);
        adjacencyMap.put(a,tmp);
    }

    public void addEdge(Node source, Node destination) {

        if (!adjacencyMap.keySet().contains(source))
            adjacencyMap.put(source, null);

        if (!adjacencyMap.keySet().contains(destination))
            adjacencyMap.put(destination, null);

        addEdgeHelper(source, destination);
        addEdgeHelper(destination, source);

    }
    public boolean hasEdge(Node source, Node destination) {
        return adjacencyMap.containsKey(source) && adjacencyMap.get(source).contains(destination);
    }

    public void resetNodesVisited()
    {
    	 for (Node n : adjacencyMap.keySet()) {
             n.unvisit();
         }

    }

    void BFS(Node node) {
      LinkedList<Node> queue = new LinkedList<>();
      //Enqueue input node as initial node
      queue.add(node);
      //Mark the node as visited
      node.visit();
      //Print out the node's name
      System.out.print(node.name + " ");
      //While the queue is not empty, continue to loop
      while (!queue.isEmpty()) {
        //Remove the first node
        Node v = queue.removeFirst();
        //Find all neighbors of the popped node
        LinkedList<Node> allNeighbors = adjacencyMap.get(v);
        //Loop through all neighbors, mark as visited, and print out name
        for (Node neighbor : allNeighbors) {
          //Only add to queue if not already visited
          if (!neighbor.isVisited()) {
            queue.add(neighbor);
            System.out.print(neighbor.name + " ");
            neighbor.visit();
          }
        }
      }
      System.out.println();
    }


   public void DFS(Node node) {
     LinkedList<Node> temp = new LinkedList<>();
     node.visit();
     System.out.print(node.name + " ");
     temp = adjacencyMap.get(node);
     for (Node neighbor : temp) {
       //If neighbor is not visited, recursively call DFS
       if (!neighbor.isVisited()) {
         DFS(neighbor);
       }
     }
   }

   public void printEdges() {
     //Loops through all nodes and prints which nodes are connected
     for (Node node : adjacencyMap.keySet()) {
       System.out.print("The node " + node.name + " is connected to: ");
       //Prints each node connected to input node
       for (Node neighbor : adjacencyMap.get(node)) {
        System.out.print(neighbor.name + " ");
       }
      System.out.println();
    }
   }
}
