package category

class FinSet extends CategoryHasCoproducts 
                with CategoryHasInitialObject
                with CategoryHasCoequalizers{
  
  /*
   * The class is declared in order to have a way to
   * refer to the type of the set elements directly.
   * 
   * XXX ? Is there a way to use the Set type directly
   * and remove TypedSet abstract class definition?
   */
  abstract class TypedSet {
    
    type Type
    
    var set: Set[Type]
    
  }
     
  case class SetArrow[A <: Object, B <: Object](source: A, function: A#Type => B#Type, target: B)
     
  type Object = TypedSet
  
  type Arrow[A <: Object, B <: Object] = SetArrow[A, B]

  /* 
   * Function that determines the source object of each arrow 
   */
  def source[A <: Object, B <: Object](arrow: Arrow[A, B]): A = arrow.source
  
  /* 
   * Function that determines the destination object of each arrow 
   */
  def target[A <: Object, B <: Object](arrow: Arrow[A, B]): B = arrow.target
  
  /* 
   * Function that determines the identity arrow of every object. 
   */
  def identity[A <: Object](objct: A): Arrow[A, A] = SetArrow[A, A](objct, (x: A#Type) => x, objct)
  
  /* 
   * Function that determines the composition of two arrows
   */
  /*
   * @associative
   */
  def composition[A <: Object, B <: Object, C <: Object](arrow1: Arrow[A, B], arrow2: Arrow[B, C]): Arrow[A, C] = {
      SetArrow[A, C](arrow1.source, (x: A#Type) => arrow2.function(arrow1.function(x)), arrow2.target)
  }

  /*
   * Implementation of HasCoproducts trait.
   */
  class SetCoproduct[A <: Object, B <: Object](override val objct1: A, override val objct2: B) extends Coproduct[A, B](objct1, objct2) {
    
    type C = TypedSet { type Type = Either[A#Type, B#Type] }
    
    def left(value: A#Type): Either[A#Type, B#Type] = Left[A#Type, B#Type](value)
    def right(value: B#Type): Either[A#Type, B#Type] = Right[A#Type, B#Type](value)

    val coproduct = new TypedSet { 
      type Type = Either[A#Type, B#Type]
      var set = objct1.set.map(left) union objct2.set.map(right) 
    }
    
    val arrow1 = SetArrow[A, C](objct1, left, coproduct)
    
    val arrow2 = SetArrow[B, C](objct2, right, coproduct)

    def universal[D <: Object](objct: D, arrow1: SetArrow[A, D], arrow2: SetArrow[B, D]) = {
      SetArrow[C, D](
          coproduct, 
          (element: C#Type) => element match {
            case Left(value) => arrow1.function(value)
            case Right(value) => arrow2.function(value)
          },
          objct
      )
      
    }
    
  }
  
  def coproduct[A <: Object, B <: Object](objct1: A, objct2: B) = new SetCoproduct[A, B](objct1, objct2)

  /*
   * Implementation of HasInitialObject trait.
   */
  def initialObject = new InitialObject {
    
    type InitialObjectType = TypedSet { type Type = Nothing }
    
    val initialObject = new TypedSet { 
      type Type = Nothing
      var set = Set[Nothing]() 
    }
    
    /*
     * The arrow from the empty set to any other set is the empty function
     * that is not defined on any inputs.
     */
    def universal[X <: TypedSet](set: X) = SetArrow[InitialObjectType, X](
      initialObject, 
      (element: InitialObjectType#Type) => { throw new Exception }, 
      set
    )
    
  }
  
  /*
   * Implementation of HasCoequalizeers trait.
   */
  
  def pop[A](set: Set[A]): (A, Set[A]) = {
    val element = set.iterator.next
    (element, set - element)
  }
  
  def coequalizer[A <: Object, B <: Object](arrow1: Arrow[A, B], arrow2: Arrow[A, B]): Coequalizer[A, B] = {
        
    /*
     * If the set is empty, then the coequalizer is the identity.  
     */
    if (arrow1.source.set.isEmpty) {
      new Coequalizer[A, B](arrow1: Arrow[A, B], arrow2: Arrow[A, B]) {
        val coequalizer = arrow1.target
        val arrow = identity(arrow1.target)
        def universal[D <: Object](objct: D, arrow: Arrow[B, D]) = arrow
      }
    }
    /*
     * If the set contains only one element,
     * then the elements are checked to be equal.
     */
    else if (arrow1.source.set.size == 1) {
      //val (element, _) = pop(arrow1.source.set)
      //val arrow1source = arrow1.source.set.iterator.next
      //val arrow1destination = arrow1.function(arrow1source)
      //val arrow2destination = arrow2.function(arrow1source)
      val element = arrow1.source.set.iterator.next
      if (arrow1.function(element) == arrow2.function(element)) {
        new Coequalizer[A, B](arrow1: Arrow[A, B], arrow2: Arrow[A, B]) {
          val coequalizer = arrow1.target
          val arrow = identity(arrow1.target)
          def universal[D <: Object](objct: D, arrow: Arrow[B, D]) = arrow
        }
      } else {
        /*
         * XXX Problems with automatically typing the operation.
         */
        val arrow1target: B = arrow1.target//.set.asInstanceOf[Set[B#Type]]
        val arrow1target_set: Set[B#Type] = arrow1target.set.asInstanceOf[Set[B#Type]]
        val arrow2target_element: B#Type = arrow2.function(element)
        //val differenceset: Set[B#Type] = 
        
        val difference = new TypedSet {
          type Type = B#Type
          val set = arrow1target_set - arrow2target_element
        }
        
        /*
         * XXX ? What does this function do?
         */
        def ff(arrow2target_element: B#Type): B#Type = {
          if (arrow2target_element == arrow2.function(element)) {
            arrow1.function(element)
          } else {
            arrow2target_element
          }
        }
        new Coequalizer[A, B](arrow1: Arrow[A, B], arrow2: Arrow[A, B]) {
          type CoequalizerType = TypedSet { type Type = B#Type }
          val coequalizer = difference
          val arrow = SetArrow(arrow1.target, ff, difference)
          def universal[D <: Object](objct: D, arrow3: Arrow[B, D]): Arrow[CoequalizerType, D] = {
            SetArrow[difference.type, D](difference, arrow3.function, objct)
          }
        }
      }
    }
    
  }    
  
  
}

/*
    new Coequalizer[A <: Object, B <: Object](arrow1: Arrow[A, B], arrow2: Arrow[A, B]) {
    
    type CoequalizerType = Object
    
    val coequalizer: CoequalizerType
    
    val arrow: Arrow[B, C]
    
    /*
     * @injection
     */
    def universal[D <: Object](objct: D, arrow: Arrow[B, D]): Arrow[C, D] 

 */

