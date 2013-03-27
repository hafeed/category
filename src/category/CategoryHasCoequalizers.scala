package category

trait CategoryHasCoequalizers extends Category{

  abstract class Coequalizer[A <: Object, B <: Object](arrow1: Arrow[A, B], arrow2: Arrow[A, B]) {
    
    type CoequalizerType <: Object
    
    val coequalizer: CoequalizerType
    
    val arrow: Arrow[B, CoequalizerType]
    
    /*
     * @injection
     */
    def universal[D <: Object](objct: D, arrow: Arrow[B, D]): Arrow[CoequalizerType, D] 
    
  }
  
  def coequalizer[A <: Object, B <: Object](arrow1: Arrow[A, B], arrow2: Arrow[A, B]): Coequalizer[A, B]
  
}