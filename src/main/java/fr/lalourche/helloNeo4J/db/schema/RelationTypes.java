/**
 * Relation types for the database
 */
package fr.lalourche.helloNeo4J.db.schema;

import org.neo4j.graphdb.RelationshipType;

/**
 * @author Lalourche
 *
 */
public enum RelationTypes implements RelationshipType
{
  ASSOCIATION,
  COMPOSITION
}
