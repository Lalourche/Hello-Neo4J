/**
 * 
 */
package fr.lalourche.helloNeo4J.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;

import fr.lalourche.helloNeo4J.db.DatabaseActions;

/**
 * @author Lalourche
 * 
 */
public class DatabaseTest
{

  private static GraphDatabaseService db_;
  private static Index<Node> nodeIndex_;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
    // Start up database
    db_ = DatabaseActions.startup();
    nodeIndex_ = DatabaseActions.getNodeIndex(db_);
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
    // Shut down the database
    db_.shutdown();
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public final void createDeleteSingleTest()
  {
    createDeleteTest(1);
  }

  @Test
  public final void createDeleteMultipleTest()
  {
    createDeleteTest(100);
  }

  @Test
  public final void createDeleteHugeTest()
  {
    createDeleteTest(1000);
  }

  /**
   * Creates, tests and deletes CommandCentres.
   * @param ccNumber the amount to create.
   */
  public final void createDeleteTest(int ccNumber)
  {
    // All must run in a transaction
    Transaction tx = db_.beginTx();
    try
    {
      // Create CommandCentres
      for (int i = 0; i < ccNumber; i++)
      {
        CommandCentre cc = new CommandCentre("CommandCentre_" + i);
        Node ccNode = Entity.createAndIndex(cc, db_, nodeIndex_);
      }

      // Retrieve CommandCentres
      IndexHits<Node> nodes = nodeIndex_.query(Entity.NAME_KEY, "*");
      Assert.assertEquals("Bad number of CommandCentre in database",
          nodes.size(), ccNumber);

      for (int i = 0; i < ccNumber; i++)
      {
        Node n = nodeIndex_.get(Entity.NAME_KEY, "CommandCentre_" + i)
            .getSingle();
        Assert.assertNotNull(n);
      }

      // Delete the CommandCentres and remove them from the index
      for (Node n : nodeIndex_.query(Entity.NAME_KEY, "*"))
      {
        nodeIndex_.remove(n, Entity.NAME_KEY, n.getProperty(Entity.NAME_KEY));
        n.delete();
      }

      tx.success();
    }
    finally
    {
      tx.finish();
    }
  }

}
