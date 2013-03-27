package category.functor

case class Category[Object, Arrow](
  
  /* Function that determines the source object of each arrow */
  s: Arrow => Object,
  
  /* Function that determines the destination object of each arrow */
  t: Arrow => Object, 
  
  /* Function that determines the composition of two arrows */
  ident: Object => Arrow,
  
  /* Function that determines the composition of two arrows */
  comp: (Arrow, Arrow) => Arrow
)

case class Functor[SourceObject, SourceArrow, TargetObject, TargetArrow](
  val source_category: Category[SourceObject, SourceArrow],
  val objects_map: SourceObject => TargetObject,
  val arrow_map: SourceArrow => TargetArrow,
  val target_category: Category[TargetObject, TargetArrow]
)

case class SetArrow[A](source: Set[A], function: A => A, target: Set[A])

object Functor {
  /*
   * FinSet category definition
   */
  def set_s[A](arrow: SetArrow[A]) = arrow match {
    case SetArrow(a, _, _) => a
  }
  
  def set_t[A](arrow: SetArrow[A]) = arrow match {
    case SetArrow(_, _, b) => b 
  }
  
  def set_ident[A](set: Set[A]) = SetArrow(set, (x: A) => x, set)
  
  def set_comp[A](ab: SetArrow[A], cd: SetArrow[A]) = (ab, cd) match {
    case (SetArrow(a,f,b), SetArrow(c,g,d)) => 
      if (b == c)
        SetArrow(a, (x: A) => g(f(x)), d)
      else
        throw new Exception
  }
  
  val FinSet = Category[Set[Any], SetArrow[Any]](set_s, set_t, set_ident, set_comp) 
  
  def identity_functor[Object, Arrow](category: Category[Object, Arrow]) =
    Functor(category, (x: Object) => x, (x: Arrow) => x, category) 
    
  def set_product[A, B](set1: Set[A], set2: Set[B]): Set[(A, B)] = { 
    if (set1.isEmpty)
      Set[(A, B)]()
    else
      set1.flatMap((a) => set2.map((b) => (a, b)))
  }
      
  def arrow_product[A, B](arrow1: SetArrow[A], arrow2: SetArrow[B]) = (arrow1, arrow2) match { 
    case (SetArrow(a, f, b), SetArrow(c, g, d)) => 
       SetArrow(
         set_product(a, c), 
         (x_y: (A, B)) => (f(x_y._1), g(x_y._2)),
         set_product(b, d)
       )
  }
   
  /*
  def cartesian_functor = Functor[Set[_ <: Any], SetArrow[Any], Set[_ <: Any], SetArrow[Any]](
    FinSet, 
    (set) => set_product(set, set), 
    (arrow) => arrow_product(arrow, arrow),
    FinSet
  )
  */
}