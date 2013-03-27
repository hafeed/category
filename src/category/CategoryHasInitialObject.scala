package category

trait CategoryHasInitialObject extends Category {

  /*
   * Uniqueness of arrows is not guaranteed.
   * 
   * Source and targets of arrows are not guaranteed.
   */
  
  abstract class InitialObject {
          
    /*
     * The type of the initial object.
     */
    type InitialObjectType <: Object
        
    /*
     * The initial object.
     */
    val initialObject: InitialObjectType
    
    def universal[X <: Object](objct: X): Arrow[InitialObjectType, X]
    
  }
  
  def initialObject: InitialObject
  
}