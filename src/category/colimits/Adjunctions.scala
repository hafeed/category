package category.adjunctions


object Colimit {
  
  trait Category[Object, Arrow] {
  
    /* Function that determines the source object of each arrow */
    def s(arrow: Arrow): Object
    
    /* Function that determines the destination object of each arrow */
    def t(arrow: Arrow): Object 
    
    /* Function that determines the composition of two arrows */
    def ident(objct: Object): Arrow
    
    /* Function that determines the composition of two arrows */
    def comp(arrow1: Arrow, arrow2: Arrow): Arrow
    
  }
  
  /*
   * Natural transformation may exist and may not exist for a pair of functors.
   */
  
  
  
}