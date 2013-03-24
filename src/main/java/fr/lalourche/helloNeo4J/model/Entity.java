/**
 * 
 */
package fr.lalourche.helloNeo4J.model;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;

/**
 * @author Lalourche
 * 
 */
public abstract class Entity
{

  public final static String NAME_KEY = "Entity.name";
  
  private String name_;

  public Entity(String name)
  {
    name_ = name;
  }

  public static Node createAndIndex(
      final Entity entity,
      final GraphDatabaseService db,
      final Index<Node> index)
  {
    Node node = db.createNode();
    node.setProperty(NAME_KEY, entity.getName());
    index.add(node, NAME_KEY, entity.getName());

    return node;
  }
  
  public String getIndexName()
  {
    return getClass().getCanonicalName();
  }

  /**
   * @return the name
   */
  public final String getName()
  {
    return name_;
  }
}
