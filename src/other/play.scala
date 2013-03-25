import scala.language.higherKinds

/*
 * Parametrised types.
 */
class ScalaPair[A, B](val first: A, val second: B) {
}

trait HasToPair {
  
  def toPair[T](value: T): ScalaPair[T, T]
  
}

/*
 * Parametrised types.
 */
class ScalaHasToPairPair[A, B](first: A, second: B) extends ScalaPair[A, B](first, second) with HasToPair {
  
  def toPair[T](value: T): ScalaPair[T, T] = {
    new ScalaPair(value, value)
  }
  
}  

/*
 * Type bounds.
 */
class ScalaNumberPair[A <: Number, B <: Number](val first: A, val second: B) {
  
  def toNumberPair[T <: Number](value: T): ScalaNumberPair[T, T] = {
    new ScalaNumberPair(value, value)
  }
  
}


/*
 * Types as members of class.
 */
trait ScalaTraitPair {
  
  type First
  
  type Second
  
  def getFirst(): First
  
  def getSecond(): Second
  
} 


class NumberPair(val first: Number, val second: Number) extends ScalaTraitPair {
  
  type First = Number
  
  type Second = Number
  
  def getFirst() = first
  
  def getSecond() = second
  
}

class BooleanPair(val first: Boolean, val second: Boolean) extends ScalaTraitPair {
  
  type First = Boolean
  
  type Second = Boolean
  
  def getFirst() = first
  
  def getSecond() = second
  
}

object Run {
  
  
  /*
   * Accessing type members.
   */
  def getFirstElement[SomePair <: ScalaTraitPair](pair: SomePair): SomePair#First = {
    pair.getFirst
  }

}


trait Apply {
 
  def apply[A, B](argument: A, function: A => B) = {
    function(argument)
  }
  
}

class ScalaListPair[A <: Pair[_, _], B <: Pair[_, _]](first: A, second: B) extends ScalaPair(first, second) {
  
}

