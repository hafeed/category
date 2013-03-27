package category


trait CategoryHasCoproducts extends Category {
         
  abstract class Coproduct[A <: Object, B <: Object/*, C <: Object*/](val objct1: A, val objct2: B) {
        
    /*
     * The type of the coproduct object.
     * 
     * XXX Rename the type into CoproductType.
     */
    type C <: Object
        
    /*
     * The coproduct object.
     */
    val coproduct: C
        
    /*
     * XXX ? How are these arrows called?
     */
    val arrow1: Arrow[A, C]
        
    val arrow2: Arrow[B, C]
     
    /*
     * Universal property of the coproduct.
     */
    def universal[D <: Object](objct: D, objctarrow1: Arrow[A, D], objctarrow2: Arrow[B, D]): Arrow[C, D]
      
  }

  def coproduct[A <: Object, B <: Object](objct1: A, objct2: B): Coproduct[A, B]

}