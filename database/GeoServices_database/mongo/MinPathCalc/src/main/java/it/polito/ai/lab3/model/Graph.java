package it.polito.ai.lab3.model;

import java.util.*;
import java.util.concurrent.*;

import it.polito.ai.lab3.mongoClasses.Edge;

/**
 * Questa classe rappresenta il grafo direzionale all'interno del quale i
 * nodi(Node.class) rappresentano le fermate mentre gli archi(Edge.class)
 * rappresentano il costo fra un nodo e un successivo. Malnati consiglia di
 * vedere il grafo come una lista di adiacenze dove per adiacenza si intende la
 * distanza che c'è fra uno stop e il successivo in una fermata
 */

public class Graph {
	public ConcurrentMap<String, Node> myNodes;
	public ConcurrentMap<String, Map<String, Edge>> myAdjList;

	public Graph() {
		myAdjList = new ConcurrentHashMap<>();
		myNodes = new ConcurrentHashMap<>();
	}

	public void addNode(Node myNode) {
		String myNodeId = myNode.getId();
		myNodes.put(myNodeId, myNode);
		myAdjList.put(myNodeId, new HashMap<>());
	}

	/*public void addNode(Node myNode, Set<Edge> myEdgeList) {
		String myNodeId = myNode.getId();
		myNodes.put(myNodeId, myNode);
		myAdjList.put(myNodeId, myEdgeList);
	}*/

	// Aggiunge la lista di edge ad un nodo esistente
	public void addEdges(Set<Edge> myEdgeList) {
			for (Edge e : myEdgeList) {
				Edge storedEdge = myAdjList.get(e.getIdSource()).get(e.getIdDestination());
				// if there is not an edge between this source and destination
				// or the stored edge is higher in cost
				if (storedEdge == null || storedEdge.getCost() > e.getCost()) {
					myAdjList.get(e.getIdSource()).put(e.getIdDestination(), e);
				}
		}
	}

	public Set<String> getNodes() {
		return myNodes.keySet();
	}

	public Map<String, Edge> getEdges(String sourceId) {
		return myAdjList.get(sourceId);
	}

}
