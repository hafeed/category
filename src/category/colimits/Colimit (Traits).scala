package category.colimits.traits
  
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
  
  trait CategoryHasCoproducts[Object, Arrow] extends Category[Object, Arrow] {
    
    def coproduct(obj1: Object, obj2: Object): Coproduct[Object, Arrow]
    
  }
  
  case class SetArrow(source: Set[Any], function: Any => Any, target: Set[Any])
  
  case class Coproduct[Object, Arrow](
    obj: Object,
    ar1: Arrow,
    ar2: Arrow,
    /*
     * Given any object in the category and two 
     * arrows to it from the components of the product,
     * there is an arrow to that object from the product object.
     */
    univ: (Object, Arrow, Arrow) => Arrow
  )
   
  class FinSet extends CategoryHasCoproducts[Set[Any], SetArrow] {
    
    def s(arrow: SetArrow): Set[Any] = arrow.source
    
    def t(arrow: SetArrow): Set[Any] = arrow.target
    
    def ident(set: Set[Any]): SetArrow = SetArrow(set, (x: Any) => x, set)
    
    def comp(ab: SetArrow, bc: SetArrow): SetArrow = (ab, bc) match {
      case (SetArrow(a,f,b), SetArrow(c,g,d)) =>
        if (b == c)
          SetArrow(a, (x: Any) => g(f(x)), d)
        else
          throw new Exception
    }
        
    def coproduct(set1: Set[Any], set2: Set[Any]): Coproduct[Set[Any], SetArrow] = {
      /*
       * Produce a disjoint union of the two sets.
       */
      def left(element: Any): Any = Left(element)
      def right(element: Any): Any = Right(element)

      val sum = set1.map(left) union set2.map(right)
      val arrow1 = SetArrow(set1, left, sum)
      val arrow2 = SetArrow(set2, right, sum)
      /*
       * BUT: the arrows may not correspond to the provided set.
       */
      val univ = (set: Set[Any], arrow1: SetArrow, arrow2: SetArrow) => {
        SetArrow(
            sum, 
            (element: Any) => element match {
              case Left(element) => arrow1.function(element)
              case Right(element) => arrow2.function(element)
            },
            set)
      }
      Coproduct(sum, arrow1, arrow2, univ)
    }
    
  }
  
  
  case class Isomorphism[A](arrow1: A, arrow2: A)

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
    /*
     * The arrow from the empty set to any other set is the empty function
     * that is not defined on any inputs.
     */
    (a: Set[Any]) => SetArrow(Set[Any](), (set: Any) => {new Exception}, a)
  )
  
  def isomorphism(o1: InitialObject[Set[Any], SetArrow], o2: InitialObject[Set[Any], SetArrow]) = {
    Isomorphism[SetArrow](o1.univ(o2.obj), o2.univ(o1.obj))
  }
  
  type Node = String
  type Edge = String
  
  case class Graph(
      
    nodes: Set[Node], 
    
    edges: Set[Edge], 
    
    source: Edge => Node, 
    
    target: Edge => Node
  )
  
  case class Diagram[Object, Arrow](
      
    graph: Graph, 
    
    objects: Node => Object, 
    
    arrows: Edge => Arrow
    
  )
  
  case class CoCone[Object, Arrow](
    
    diagram: Diagram[Object, Arrow],
    
    apex: Object,  
    
    arrows: Node => Arrow
    
  )
  
  /*
   * May be it is necessary to introduce CoConeArrow type.
   */
  case class Colimit[Object, Arrow](
    
    diagram: Diagram[Object, Arrow], 
    
    cocone: CoCone[Object, Arrow], 
    
    /*
     * For any other cocone of the diagram, 
     * there is an arrow to that cocone
     * from the given cocone.
     */
    univ: CoCone[Object, Arrow] => Arrow

  )
  
  trait CategoryCoComplete[Object, Arrow] extends Category[Object, Arrow] {
    
    /*
     * The function produces a colimit for any diagram in the category.
     */
    def colimit(diagram: Diagram[Object, Arrow]): Colimit[Object, Arrow] 
    
  }
  
}