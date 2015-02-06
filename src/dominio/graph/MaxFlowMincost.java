package dominio.graph;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/** @author Cristofol-Lluis Rivas I Thwaite */

public class MaxFlowMincost extends FordFulkerson {

	private static double INFINITY = Double.MAX_VALUE;

	private class Vertex implements Comparable<Vertex> {
		double weight;
		int id;

		@Override
		public int compareTo(Vertex other) {
			return Double.compare(weight, other.weight);
		}

	}

	public MaxFlowMincost(Graph g) {
		super(g);
	}

	private boolean isNotSaturated(int u, int v) {
		return (g.getEdgeMaxFlow(u, v) > g.getEdgeFlow(u, v));
	}

	private ArrayList<Integer> processPath(int src, int snk, int[] parent) {
		Stack<Integer> s = new Stack<Integer>();
		ArrayList<Integer> p = new ArrayList<Integer>();
		s.push(snk);
		int x = snk;
		while (x != src) {
			if (parent[x] == -1)
				return p;
			x = parent[x];
			s.push(x);
		}

		while (!s.empty())
			p.add(s.pop());
		return p;
	}

	@Override
	protected ArrayList<Integer> getAugmentingPath() {
		int source = g.getSource();
		int sink = g.getSink();
		int n = g.getNodeCount();
		boolean[] v = new boolean[n];
		double[] d = new double[n];
		int[] parent = new int[n];
		PriorityQueue<Vertex> Q = new PriorityQueue<Vertex>();
		for (int i = 0; i < n; ++i) {
			d[i] = INFINITY;
			v[i] = false;
			parent[i] = -1;
		}

		d[source] = 0.0;
		Vertex aux = new Vertex();
		aux.id = source;
		aux.weight = 0.0;
		Q.add(aux);

		// DIJKSTRA
		while (!Q.isEmpty()) {
			int u = Q.poll().id;
			v[u] = true;
			ArrayList<Integer> Neighbours = new ArrayList<Integer>();
			Neighbours = g.getNeighbours(u);
			for (int i = 0; i < Neighbours.size(); ++i) {
				int w = Neighbours.get(i);
				if (!v[w] && isNotSaturated(u, w)) {
					double c = g.getEdgeCost(u, w);
					if (d[w] > d[u] + c) {
						d[w] = d[u] + c;
						parent[w] = u;
						Vertex aux2 = new Vertex();
						aux2.id = w;
						aux2.weight = d[w];
						Q.add(aux2);
					}// if_weight

				}// if_not_visited_not_saturated

			}// for

		}// while

		return processPath(source, sink, parent);
	}// augmenting_path()

}
