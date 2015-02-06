package dominio.graph;

import java.util.ArrayList;

public class FordFulkerson {
	// Grafo sobre el cual ejecutamos el algoritmo.
	// protected para que las subclases lo puedan usar.
	protected Graph g;

	// Crea una instancia del algoritmo Ford-Fulkerson.
	// Pre: g != null, g tiene source y sink.
	public FordFulkerson(Graph g) {
		this.g = g;
	}

	private ArrayList<Boolean> vist;
	private ArrayList<Integer> nod;

	private boolean dfs(int x, int y, int n) {
		if (vist.get(x))
			return false;
		vist.set(x, true);
		nod.add(x);
		if (x == y) {
			return true;
		}
		for (int i = 0; i < n; ++i) {
			if (!g.isEdge(x, i)
					|| g.getEdgeMaxFlow(x, i) - g.getEdgeFlow(x, i) <= 0)
				continue;
			if (dfs(i, y, n))
				return true;
		}
		nod.remove(nod.size() - 1);
		return false;
	}

	// Calcula un augmenting path. Las subclases deben implementar
	// este metodo (con Dijkstra, BFS...)
	// Pre: g tiene source y sink, y las aristas del grafo con sus costes y
	// capacidades.
	// Post: Si no hay augmenting path devuelve null
	// Si la hay, devuelve una lista de todos los nodos en la augmenting path.
	// el primero sera el source, el ultimo sera el sink, y el resto
	// forman un path valido.
	protected ArrayList<Integer> getAugmentingPath() {
		int x = g.getSource();
		int y = g.getSink();
		int n = g.getNodeCount();
		vist = new ArrayList<Boolean>();
		nod = new ArrayList<Integer>();
		for (int i = 0; i < n; ++i) {
			vist.add(false);
		}
		boolean bb = dfs(x, y, n);
		if (bb)
			return nod;
		else
			return nod;
	}

	private class pair {
		public int a;
		public int b;

		public pair(int x, int y) {
			a = x;
			b = y;
		}
	}

	// Ejecuta el algoritmo.
	// Pre: g tiene source y sink, y las aristas del grafo con sus costes y
	// capacidades.
	// Post: devuelve el maximum flow entre source y sink
	// en las aristas de g esta la informacion de cuanto flow pasa por cada
	// arista.
	public int run() {
		int superflow = 0;
		ArrayList<Integer> nodos = getAugmentingPath();
		ArrayList<pair> edges = new ArrayList<pair>();
		while (!nodos.isEmpty()) {
			int flowmax = 1000000000;
			int x = nodos.get(0);
			for (int i = 1; i < nodos.size(); ++i) {
				int y = nodos.get(i);
				int z = g.getEdgeMaxFlow(x, y) - g.getEdgeFlow(x, y);
				if (z < flowmax) {
					flowmax = z;
				}
				x = y;
			}
			x = nodos.get(0);
			superflow += flowmax;
			for (int i = 1; i < nodos.size(); ++i) {
				int y = nodos.get(i);

				if (!g.isEdge(y, x)) {
					g.addEdge(0, 0, g.getEdgeCost(x, y), y, x);
					pair aux = new pair(y, x);
					edges.add(aux);
				}

				g.setEdgeFlow(y, x, g.getEdgeFlow(y, x) - flowmax);
				g.setEdgeFlow(x, y, flowmax + g.getEdgeFlow(x, y));
				x = y;
			}
			nodos = getAugmentingPath();
		}
		for (int i = 0; i < edges.size(); ++i) {
			pair aux = edges.get(i);
			g.removeEdge(aux.a, aux.b);
		}
		return superflow;
	}

	public final Graph getResidualGraph() {
		Graph gg = g.clone();
		int n = gg.getNodeCount();
		for (int i = 0; i < n; ++i) {
			for (int j = i; j < n; ++j) {
				if (!gg.isEdge(i, j) && gg.isEdge(j, i)
						&& gg.getEdgeFlow(j, i) > 0) {
					gg.addEdge(-g.getEdgeFlow(j, i), 0, g.getEdgeCost(j, i), i,
							j);
				}
				if (!gg.isEdge(j, i) && gg.isEdge(i, j)
						&& gg.getEdgeFlow(i, j) > 0) {
					gg.addEdge(-g.getEdgeFlow(i, j), 0, g.getEdgeCost(i, j), j,
							i);
				}
				if (gg.isEdge(i, j)) {
					gg.setEdgeMaxFlow(i, j,
							gg.getEdgeMaxFlow(i, j) - gg.getEdgeFlow(i, j));
					gg.setEdgeFlow(i, j, 0);
				}
				if (gg.isEdge(j, i)) {
					gg.setEdgeMaxFlow(j, i,
							gg.getEdgeMaxFlow(j, i) - gg.getEdgeFlow(j, i));
					gg.setEdgeFlow(j, i, 0);
				}
			}
		}
		return gg;
	}
}