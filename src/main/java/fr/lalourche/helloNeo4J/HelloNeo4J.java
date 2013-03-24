package fr.lalourche.helloNeo4J;
import fr.lalourche.helloNeo4J.db.DatabaseActions;

import org.neo4j.graphdb.GraphDatabaseService;


/**
 * @author Lalourche
 * 
 */
public class HelloNeo4J
{

  

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    // Start up database
    GraphDatabaseService db = DatabaseActions.startup();
    
    // Shut down the database
    db.shutdown();
  }

}
