package dominio.graph;

import java.util.ArrayList;

/*@author: Cristofol-Lluis Rivas I Thwaite*/

public class Graph {

	/*
	 * Hemos aadido nuevas operaciones: getEdgeMaxFlow, getEdgeFlow,
	 * getEdgeCost, getNeighbours, getNeighboursReverse, getNodeCount ya que a
	 * la hora de hacer la especificacin nos olvidamos de ellas y al empezar a
	 * implementar nos dimos cuenta que eran necessarias.
	 * 
	 * Por ltimo hemos quitado el argumento final de la matriz y del ArrayList y
	 * privatizado las clases Nodo y Edge porqu no tienen que ser utilizadas por
	 * el usuario directamente sin a travs de la classe Graph.
	 */

	/*
	 * Matriz de adjecencia para implementar y trabajar con el grafo dirijido.
	 * Si no hubiese adyacencia, habria un NULL en la posicin G[i][j]. La
	 * direccin de las aristas son de i -> j.
	 */
	private Edge G[][];

	/*
	 * Vector que contiene los nodos del grafo.
	 */
	private ArrayList<Node> nodes;

	/*
	 * Variables globales para poder trabajar con la clase Graph.
	 */
	private int source_node;
	private int sink_node;

	/*
	 * Clase Node con los atributos id(para saber en que i se encuentra en la
	 * matriz de adjecencia) y nombre(para saber el nombre del nodo).
	 */
	private class Node {
		public String name;
	}

	/*
	 * Clase Edge o arista con sus atributos flow(para saber el flujo que tienen
	 * en el momento), maxflow(para saber el flujo mximo que soporta la arista)
	 * y cost(el coste de la arista).
	 */
	private class Edge {
		public int flow;
		public int maxflow;
		public double cost;
	}

	/* OPERACIONES CONSTRUCTORAS */

	/*
	 * PRE: No hay precondicin. POST:Crea una matriz de adjecencia G y un
	 * ArrayList nodes, vacios. las variables a -1 que es el valor que se
	 * identifica con no existencia.
	 */
	public Graph() {
		G = new Edge[0][0];
		nodes = new ArrayList<Node>();
		sink_node = source_node = -1;
	}

	/* OPERACIONES CLONADORAS */

	/*
	 * PRE: No hay precondicin. POST: Copia del grafo G a la entrada implicita.
	 */
	@Override
	public Graph clone() {
		Graph C = new Graph();
		for (int i = 0; i < getNodeCount(); ++i) {
			String auxName = getNodeName(i);
			C.addNode(auxName);
		}

		for (int i = 0; i < getNodeCount(); ++i) {
			for (int j = 0; j < getNodeCount(); ++j) {
				if (isEdge(i, j)) {
					double auxCost = getEdgeCost(i, j);
					int auxFlow = getEdgeFlow(i, j);
					int auxMaxFlow = getEdgeMaxFlow(i, j);
					C.addEdge(auxFlow, auxMaxFlow, auxCost, i, j);
				}

			}

		}

		if (hasSource())
			C.setSourceNode(getSource());
		if (hasSink())
			C.setSinkNode(getSink());
		return C;
	}

	/* OPERACIONES CONSULTORAS */

	/*
	 * PRE: No hay precondicin. POST: Mtodo que retorna si existe la arista
	 * G[i][j].
	 */
	public boolean isEdge(int i, int j) {
		return (G[i][j] != null);
	}

	/*
	 * PRE: No hay precondicin. POST: Mtodo que retorna el tamao del ArrayList
	 * nodes.
	 */
	public int getNodeCount() {
		return nodes.size();
	}

	/*
	 * PRE: No hay precondicin. POST: Mtodo que nos retorna la existencia del
	 * nodo G[i][].
	 */
	public boolean isNode(int i) {
		return (i < nodes.size() && i >= 0);
	}

	/*
	 * PRE: No hay precondicin. POST: Mtodo que nos retorna la existencia del
	 * nodo con el atributo name igual a s.
	 */
	public boolean isNode(String s) {
		return (isNode(getNodeId(s)));
	}

	/*
	 * PRE: Debe existir el nodo en la posicion i en la ArrayList nodes y debe
	 * existir un nodo origen. POST: Mtodo que nos retorna si el nodo G[i][] es
	 * el nodo origen del grafo.
	 */
	public boolean isSource(int i) {
		return (i == source_node);
	}

	/*
	 * PRE: Debe existir el nodo en la posicin i en la ArrayList nodes y debe
	 * existir un nodo destino. POST: Mtodo que nos retorna si el nodo G[i][] es
	 * el nodo destino del grafo.
	 */
	public boolean isSink(int i) {
		return (i == sink_node);
	}

	/*
	 * PRE: Debe existir el nodo con name = name en la matriz G o en la
	 * ArrayList nodes y debe existir un nodo origen. POST: Mtodo que nos
	 * retorna si el nodo con atributo name = name es el nodo origen del grafo.
	 */
	public boolean isSource(String name) {
		return (isSource(getNodeId(name)));
	}

	/*
	 * PRE: Debe existir el nodo con name = name en la matriz G o en la
	 * ArrayList nodes y debe existir un nodo destino. POST: Mtodo que nos
	 * retorna si el nodo con atributo name = name es el nodo destino del grafo.
	 */
	public boolean isSink(String name) {
		return (isSink(getNodeId(name)));
	}

	/*
	 * PRE: Debe existir el Edge G[i][j]. POST: Retorna el atributo maxflow de
	 * la arista G[i][j].
	 */
	public int getEdgeMaxFlow(int i, int j) {
		return G[i][j].maxflow;
	}

	/*
	 * PRE: Debe existir el Edge G[i][j]. POST: Retorna el atributo flow de la
	 * arista G[i][j].
	 */
	public int getEdgeFlow(int i, int j) {
		return G[i][j].flow;
	}

	/*
	 * PRE: Debe existir el Edge G[i][j]. POST: Mtodo que retorna el atributo
	 * cost de la arista G[i][j].
	 */
	public double getEdgeCost(int i, int j) {
		return G[i][j].cost;
	}

	/*
	 * PRE: Debe existir el nodo en la posicin i en la ArrayList nodes. POST:
	 * Mtodo que retorna un ArrayList con la posicin i de los nodos destino del
	 * nodo en la posicin i.
	 */
	public ArrayList<Integer> getNeighbours(int i) {
		ArrayList<Integer> dest = new ArrayList<Integer>();
		for (int j = 0; j < nodes.size(); ++j) {
			if (G[i][j] != null) {
				int aux = j;
				dest.add(aux);
			}
		}

		return dest;
	}

	/*
	 * PRE: Debe existir el nodo en la posicin i en la ArrayList nodes. POST:
	 * Mtodo que retorna un ArrayList con la posicin i de los nodos origen del
	 * nodo en la posicin i.
	 */
	public ArrayList<Integer> getReverseNeighbours(int i) {
		ArrayList<Integer> source = new ArrayList<Integer>();
		for (int j = 0; j < nodes.size(); ++j) {
			if (G[j][i] != null) {
				int aux = j;
				source.add(aux);
			}
		}

		return source;
	}

	/*
	 * PRE: Debe existir el nodo con el atributo name = name en el ArrayList
	 * nodes. POST: Mtodo que retorna un ArrayList con las posiciones i de los
	 * nodos destino del nodo con el atributo name = name.
	 */
	public ArrayList<Integer> getNeighbours(String name) {
		return (getNeighbours(getNodeId(name)));
	}

	/*
	 * PRE: Debe existir el nodo con el atributo name = name en el ArrayList
	 * nodes. POST: Mtodo que retorna un ArrayList con las posiciones de los
	 * nodos origen del nodo con el atributo name = name.
	 */
	public ArrayList<Integer> getReverseNeighbours(String name) {
		return (getReverseNeighbours(getNodeId(name)));
	}

	/*
	 * PRE: Debe existir el nodo en la posicin i en el ArrayList nodes. POST:
	 * Mtodo que nos retorna el nombre del nodo en la posicin i.
	 */
	public String getNodeName(int i) {
		return (nodes.get(i).name);
	}

	/*
	 * PRE: Debe existir el nodo con atributo name = name en el ArrayList nodes.
	 * POST: Mtodo que nos retorna el nombre del nodo con atributo name = name.
	 */
	public int getNodeId(String name) {
		int aux = -1;
		for (int i = 0; i < nodes.size(); ++i) {
			if (nodes.get(i).name.equals(name))
				return i;
		}

		return aux;
	}

	/*
	 * PRE: No hay precondicin. POST: Mtodo que nos retorna si existe un nodo
	 * origen.
	 */
	public boolean hasSource() {
		return (source_node >= 0);
	}

	/*
	 * PRE: No hay precondicin. POST: Mtodo que nos retorna si existe un nodo
	 * destino.
	 */
	public boolean hasSink() {
		return (sink_node >= 0);
	}

	/*
	 * PRE: Debe existir un nodo origen en la clase Graph. POST: Mtodo que nos
	 * retorna la posicion i del nodo origen en la matriz G y la posicin i del
	 * nodo origen.
	 */
	public int getSource() {
		return source_node;
	}

	/*
	 * PRE: Debe existir un nodo destino en la clase Graph. POST: Mtodo que nos
	 * retorna la posicion i del nodo destino en la matriz G y la posicin del
	 * nodo destino.
	 */
	public int getSink() {
		return sink_node;
	}

	/* OPERACIONES MODIFICADORAS */

	/*
	 * PRE: No puede existir un nodo con atributo name = name y name no puede
	 * ser null. POST: Mtodo que aade un nodo en ArrayList con el atributo name
	 * = name y en la posicin que le corresponde. La matriz se actualiza con una
	 * columna y fila ms, para que tenga el tamao nodes.size();
	 */
	public int addNode(String name) {
		Edge[][] aux = new Edge[nodes.size() + 1][nodes.size() + 1];
		for (int i = 0; i < nodes.size(); ++i) {
			for (int j = 0; j < nodes.size(); ++j)
				aux[i][j] = G[i][j];
		}

		G = aux;
		Node aux2 = new Node();
		aux2.name = name;
		nodes.add(aux2);
		return nodes.size() - 1;
	}

	/*
	 * PRE: La arista G[i][j] y la arista G[j][i] no pueden existir en la clase
	 * Graph. Flow <= Maxflow. i != j. Deben existir el nodo i y el nodo j.
	 * POST: Mtodo que aade la arista G[i][j] y que tiene los atributos con los
	 * valores flow, maxflow y cost. La arista es dirigida y va del nodo i -> al
	 * nodo j.
	 */
	public void addEdge(int flow, int maxflow, double cost, int i, int j) {
		G[i][j] = new Edge();
		G[i][j].cost = cost;
		G[i][j].flow = flow;
		G[i][j].maxflow = maxflow;
	}

	/*
	 * PRE: La arista G[getNodeId(name)][getNodeId(name1)] y la arista
	 * G[getNodeId(name1)][getNodeId(name)] no pueden existir en la clase Graph.
	 * Flow <= Maxflow. name != name1. Deben existir el nodo name y el node
	 * name1. POST: Mtodo que aade la arista
	 * G[getNodeId(name)][getNodeId(name1)] y que tiene los atributos con los
	 * valores flow, maxflow y cost. La arista es dirigida y va del nodo name ->
	 * al nodo name1.
	 */
	public void addEdge(int flow, int maxflow, double cost, String name,
			String name1) {
		addEdge(flow, maxflow, cost, getNodeId(name), getNodeId(name1));
	}

	/*
	 * PRE: El nodo en la posicin i debe existir en el ArrayList nodes. POST:
	 * Mtodo donde el nodo es eliminado del ArrayList nodes y la matriz es
	 * actualizada a Todos los nodos con su posicion > i son actualizados a su
	 * posicin - 1. Los Edges de la matriz G, i -> j y j -> i desaparecen.
	 */
	public void removeNode(int i) {
		Edge[][] aux = new Edge[nodes.size() - 1][nodes.size() - 1];
		for (int j = 0; j < i; ++j) {
			for (int k = 0; k < i; ++k)
				aux[j][k] = G[j][k];
			for (int k = i; k < nodes.size() - 1; ++k)
				aux[j][k] = G[j][k + 1];
		}

		for (int j = i; j < nodes.size() - 1; ++j) {
			for (int k = 0; k < i; ++k)
				aux[j][k] = G[j + 1][k];
			for (int k = i; k < nodes.size() - 1; ++k)
				aux[j][k] = G[j + 1][k + 1];
		}
		if (isSource(i))
			source_node = -1;
		else if (isSink(i))
			sink_node = -1;
		nodes.remove(i);
	}

	/*
	 * PRE: El nodo con atributo name = name debe existir en el ArrayList nodes.
	 * POST: Mtodo donde el nodo es eliminado del ArrayList nodes y la matriz es
	 * actualizada a Todos los nodos con su posicion > i son actualizados a su
	 * posicin - 1. Los Edges de la matriz G, i -> j y j -> i desaparecen.
	 */
	public void removeNode(String name) {
		removeNode(getNodeId(name));
	}

	/*
	 * PRE: Debe existir la arista G[i][j]. POST: Mtodo que elimina la arista
	 * G[i][j] y deja el valor a null.
	 */
	public void removeEdge(int i, int j) {
		G[i][j] = null;
	}

	/*
	 * PRE: Debe existir la arista G[i][j]. POST: Mtodo que cambia el atributo
	 * cost de la arista G[i][j] a cost.
	 */
	public void setEdgeCost(int i, int j, double cost) {
		G[i][j].cost = cost;
	}

	/*
	 * PRE: Debe existir la arista G[i][j]. Flow <= G[i][j].maxFlow. POST: Mtodo
	 * que cambia el atributo flow de la arista G[i][j] a flow.
	 */
	public void setEdgeFlow(int i, int j, int flow) {
		G[i][j].flow = flow;
	}

	/*
	 * PRE: Debe existir la arista G[i][j]. maxflow >= G[i][j].flow. POST: Mtodo
	 * que cambia el atributo maxflow de la arista G[i][j] a maxflow.
	 */
	public void setEdgeMaxFlow(int i, int j, int maxflow) {
		G[i][j].maxflow = maxflow;
	}

	/*
	 * PRE: Debe existir el nodo en la posicin i en el ArrayList nodes. POST:
	 * Mtodo que hace que el nodo en la posicin i sea el nodo origen de la clase
	 * Graphs.
	 */
	public void setSourceNode(int i) {
		source_node = i;
	}

	/*
	 * PRE: Debe existir el nodo en la posicin i en el ArrayList nodes. POST:
	 * Mtodo que hace que el nodo en la posicin i sea el nodo destino de la
	 * clase Graphs.
	 */
	public void setSinkNode(int i) {
		sink_node = i;
	}

	/*
	 * PRE: Debe existir el nodo con atributo name = name en el ArrayList nodes.
	 * POST: Mtodo que hace que el nodo con atributo name = name sea el nodo
	 * origen de la clase Graphs y que retorna la posicin i de dicho nodo.
	 */
	public int setSourceNode(String name) {
		int aux = getNodeId(name);
		setSourceNode(aux);
		return aux;
	}

	/*
	 * PRE: Debe existir el nodo con atributo name = name en el ArrayList nodes.
	 * POST: Mtodo que hace que el nodo con atributo name = sea el nodo destino
	 * de la clase Graphs y que retorna la posicin i de dicho nodo.
	 */
	public int setSinkNode(String name) {
		int aux = getNodeId(name);
		setSinkNode(aux);
		return aux;
	}

}