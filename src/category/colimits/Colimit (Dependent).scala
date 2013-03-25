package category.colimits.dependent
  
import scala.language.higherKinds

object Colimit {
  
  trait Category {
    
    type Object
    
    type Arrow[A <: Object, B <: Object]
  
    /* Function that determines the source object of each arrow */
    def s[A <: Object, B <: Object](arrow: Arrow[A, B]): A
    
    /* Function that determines the destination object of each arrow */
    def t[A <: Object, B <: Object](arrow: Arrow[A, B]): B 
    
    /* Function that determines the composition of two arrows */
    def ident[A <: Object](objct: A): Arrow[A, A]
    
    /* Function that determines the composition of two arrows */
    def comp[A <: Object, B <: Object, C <: Object](arrow1: Arrow[A, B], arrow2: Arrow[B, C]): Arrow[A, C]
    
  }
  
  trait CategoryHasCoproducts extends Category {
         
    abstract class Coproduct[A <: Object, B <: Object](val objct1: A, val objct2: B) {
      
      type C <: Object
      
      val coproduct: C
      
      val arrow1: Arrow[A, C]
      
      val arrow2: Arrow[B, C]
      
      def univ[D <: Object](objct: D, objctarrow1: Arrow[A, D], objctarrow2: Arrow[B, D]): Arrow[C, D]
      
    }

    def coproduct[A <: Object, B <: Object](obj1: A, obj2: B): Coproduct[A, B]
    
  }
  
  trait CategoryGroupoid extends Category {
    
    def invert[A <: Object, B <: Object](arrow: Arrow[A, B]): Arrow[B, A]
    
  }
 
  class FinSet extends CategoryHasCoproducts {
  
    abstract class TypedSet {
      
      type Type
      
      var set: Set[Type]
      
    }
       
    type Object = TypedSet
       
    case class SetArrow[A <: Object, B <: Object](source: A, function: A#Type => B#Type, target: B)
    
    //class SetInt extends TypedSet { 
    //  type Type = Int
    //}
    //val s1 = SetInt()
    //val s2  = SetInt()
    
    val arr = SetArrow[TypedSet{ type Type = Int}, TypedSet{ type Type = Int}](
        new TypedSet {type Type = Int; var set = Set[Int](1,2,3)}, 
        (x: Int) => x + 1, 
        new TypedSet {type Type = Int; var set = Set[Int](1,2,3)})
    
    type Arrow[A <: Object, B <: Object] = SetArrow[A, B]
  
    /* Function that determines the source object of each arrow */
    def s[A <: Object, B <: Object](arrow: Arrow[A, B]): A = arrow.source
    
    /* Function that determines the destination object of each arrow */
    def t[A <: Object, B <: Object](arrow: Arrow[A, B]): B = arrow.target
    
    /* Function that determines the composition of two arrows */
    def ident[A <: Object](objct: A): Arrow[A, A] = SetArrow[A, A](objct, (x: A#Type) => x, objct)
    
    /* Function that determines the composition of two arrows */
    def comp[A <: Object, B <: Object, C <: Object](arrow1: Arrow[A, B], arrow2: Arrow[B, C]): Arrow[A, C] = {
        SetArrow[A, C](arrow1.source, (x: A#Type) => arrow2.function(arrow1.function(x)), arrow2.target)
    }

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
  
      def univ[D <: Object](objct: D, arrow1: SetArrow[A, D], arrow2: SetArrow[B, D]) = {
        SetArrow[C, D](
            coproduct, 
            (element: C#Type) => element match {
              case Left(value) => arrow1.function(value)
              case Right(value) => arrow2.function(value)
            },
            objct)
      }
    }
    
    def coproduct[A <: Object, B <: Object](obj1: A, obj2: B) = new SetCoproduct[A, B](obj1, obj2)
    
  }

}