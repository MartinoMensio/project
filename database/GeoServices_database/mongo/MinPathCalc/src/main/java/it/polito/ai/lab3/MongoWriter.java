package it.polito.ai.lab3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.Document;

import com.mongodb.DBRef;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import it.polito.ai.lab3.mongoClasses.Edge;
import it.polito.ai.lab3.mongoClasses.MinPath;

public class MongoWriter {

	private MongoClient mongoClient;
	private MongoCollection<Document> minPathsCollection;
	private MongoCollection<Document> edgesCollection;

	/**
	 * sets up connection to mongoDB, DELETING EVERYTHING in the min_paths
	 * collection
	 */
	public MongoWriter() {
		mongoClient = new MongoClient("localhost");
		MongoDatabase database = mongoClient.getDatabase("ai");
		minPathsCollection = database.getCollection("min_paths");
		edgesCollection = database.getCollection("edges");
		// clear before starting
		minPathsCollection.drop();
		edgesCollection.drop();
	}
	
	public void addEdges(Collection<Edge> edges) {
		for (Edge edge : edges) {
			Document edgeDocument = new Document("idSource", edge.getIdSource()).append("idDestination", edge.getIdDestination())
					.append("mode", edge.isMode()).append("cost", edge.getCost())
					// also store the lineId useful for displaying solution
					.append("lineId", edge.getLineId())
					.append("stopsId", edge.getStopsId())
					// enable faster lookup by defining the primary key as
					// combination of src and dst ids
					.append("_id", new Document("src", edge.getIdSource()).append("dst", edge.getIdDestination()));;
			edgesCollection.insertOne(edgeDocument);
		}
	}

	public void addMinPath(MinPath minPath) {
		List<DBRef> edges = new ArrayList<>();

		for (Edge edge : minPath.getEdges()) {
			Document edgeId = new Document("src", edge.getIdSource()).append("dst", edge.getIdDestination());
			// create the dbref
			edges.add(new DBRef("edges", edgeId));
		}

		Document document = new Document("idSource", minPath.getIdSource())
				.append("idDestination", minPath.getIdDestination())
				.append("edges", edges)
				.append("totalCost", minPath.getTotalCost())
				// enable faster lookup by defining the primary key as
				// combination of src and dst ids
				.append("_id", new Document("src", minPath.getIdSource()).append("dst", minPath.getIdDestination()));

		minPathsCollection.insertOne(document);
	}

	public void close() {
		mongoClient.close();
	}
}
