package dominio.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author eric.alvarez.chinchilla
 *
 */

public class MaxFlow extends FordFulkerson {

	public MaxFlow(Graph g) {
		super(g);
	}

	// PRE: El grafo que invoca getAugmentingPath ha de tener un sourceNode y un
	// sinkNode
	// POST: Devuelve un ArrayList con el augmenting path del grafo que invoca
	// al método
	// utilizando el algoritmo BFS - Breadth First Search. Si no existe el
	// camino posible
	// devuelve un ArrayList vacío.
	@Override
	protected ArrayList<Integer> getAugmentingPath() {
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		ArrayList<Integer> path = new ArrayList<>();
		int src = g.getSource();
		int dst = g.getSink();

		// si el source es el sink
		if (src == dst) {
			path.add(src);
			return path;
		}

		int numNodes = g.getNodeCount();
		boolean[] visited = new boolean[numNodes];
		Integer[] parent = new Integer[numNodes];

		visited[src] = true;
		q.add(src);

		while (!q.isEmpty()) {
			int currentNode = q.remove();
			ArrayList<Integer> reachableList = g.getNeighbours(currentNode);
			for (int i = 0; i < reachableList.size(); i++) {
				int neighbour = reachableList.get(i);
				if (!visited[neighbour] && isNotSatured(currentNode, neighbour)) {
					parent[neighbour] = currentNode;
					visited[neighbour] = true;
					if (neighbour == dst) {
						return processPath(src, dst, parent);
					}
					q.add(neighbour);
				}
			}
		}
		return path;
	}

	// PRE: src y dst son un nodos del objeto que invoca a este método
	// POST evalua si la arista de src a dst está saturada o no
	private boolean isNotSatured(int src, int dst) {
		return (g.getEdgeFlow(src, dst) < g.getEdgeMaxFlow(src, dst));
	}

	// PRE: src es el Source node y dst es el Sink node
	// POST: devuelve el camino más corto des del source node al sink node
	private ArrayList<Integer> processPath(int src, int dst, Integer[] parent) {
		Stack<Integer> st = new Stack<Integer>();
		ArrayList<Integer> pp = new ArrayList<>();
		st.push(dst);
		int x = dst;
		while (x != src) {
			x = parent[x];
			st.push(x);
		}
		while (!st.empty()) {
			pp.add(st.pop());
		}
		return pp;
	}
}
