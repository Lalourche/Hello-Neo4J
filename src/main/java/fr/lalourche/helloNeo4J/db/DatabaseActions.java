/**
 * Main database actions.
 */
package fr.lalourche.helloNeo4J.db;

import java.util.ResourceBundle;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

/**
 * @author Lalourche
 *
 */
public class DatabaseActions
{

  private final static String DB_PATH = ResourceBundle.getBundle("Configuration").getString("db.path");
  private final static String NODE_INDEX_KEY = "index.node";

  public static GraphDatabaseService startup()
  {
    // Instanciate the database
    GraphDatabaseService graphDb = new GraphDatabaseFactory()
        .newEmbeddedDatabase(DB_PATH);
    registerShutdownHook(graphDb);
    
    return graphDb;
  }

  public static Index<Node> getNodeIndex(final GraphDatabaseService graphDb)
  {
    return graphDb.index().forNodes(NODE_INDEX_KEY);
  }

  public static void shutdown(final GraphDatabaseService graphDb)
  {
    graphDb.shutdown();
  }

  private static void registerShutdownHook(final GraphDatabaseService graphDb)
  {
    // Registers a shutdown hook for the Neo4j instance so that it
    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
    // running example before it's completed)
    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      @Override
      public void run()
      {
        graphDb.shutdown();
      }
    });
  }
}
