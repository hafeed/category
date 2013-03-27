package category

trait CategoryCoComplete extends Category {
   
  type Node = String
  type Edge = String
  
  case class Graph(
      
    nodes: Set[Node], 
    
    edges: Set[Edge], 
    
    source: Edge => Node, 
    
    target: Edge => Node
    
  )
  
  abstract class Diagram (val graph: Graph) { 
    
    def objects(node: Node): Object 
    
    /*
     * XXX Make sure the arrows corresponding to the
     * edges exist for the source and target nodes of 
     * the edges corresponding to the source and target
     * objects of the arrow.
     */
    
    def arrows(edge: Edge): Arrow[_, _]
    
  }
  
  abstract class CoCone[A <: Object] (val diagram: Diagram) {
    
    //type A <: Object
    
    val apex: A  
    
    def universal[B <: Object](node: B): Arrow[B, A]
    
    /*
     * XXX Express the commutativity requirement of the arrow
     * in the diagram using the equality on arrows provided
     * by the definition of Category type.
     */
  }
  

  abstract class Colimit[A <: Object](val diagram: Diagram) {
    
    val cocone: CoCone[A]
    
    /*
     * For any other cocone of the diagram, 
     * there is an arrow to that cocone
     * from the given cocone.
     */
    /*
     * XXX @injective
     */
    def universal[B <: Object](cocone: CoCone[B]): Arrow[A, B]

  }
   
  /*
   * The function produces a colimit for any diagram in the category.
   */
  def colimit[A <: Object](diagram: Diagram): Colimit[A]
  
}