package category.colimits
  
object Colimit {
  
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

  case class SetArrow[A](source: Set[A], function: A => A, target: Set[A])
  
  case class Isomorphism[Arrow](arrow1: Arrow, arrow2: Arrow)

  /*
   * Uniqueness of arrows is not guaranteed.
   * 
   * Source and targets of arrows are not guaranteed.
   */
  case class InitialObject[Object, Arrow](
    /* 
     * Initial object.
     */
    obj: Object,
    /*
     * Function assigning the unique arrow to each object in the category.
     * 
     * BUT: Uniqueness of arrows is not guaranteed.
     * 
     * BUT: Source and targets of arrows are not guaranteed.
     */
    univ: Object => Arrow
  )

  val SetInitialObject = InitialObject(
    Set[Any](), 
    (a: Set[Any]) => SetArrow[Any](Set[Any](), (set: Any) => {new Exception}, a)
  )
  
  def isomorphism(o1: InitialObject[Set[Any], SetArrow[Any]], o2: InitialObject[Set[Any], SetArrow[Any]]) = {
    Isomorphism[SetArrow[Any]](o1.univ(o2.obj), o2.univ(o1.obj))
  }
  
  case class Coproduct[Object, Arrow](
    obj: Object,
    ar1: Arrow,
    ar2: Arrow,
    univ: Coproduct[Object, Arrow] => Arrow
  )
   
  case class coproduct[Object, Arrow](function: (Object, Object) => Coproduct[Object, Arrow])
  
  
}