package category

trait CategoryGroupoid extends Category {
    
  def invert[A <: Object, B <: Object](arrow: Arrow[A, B]): Arrow[B, A]
    
}
