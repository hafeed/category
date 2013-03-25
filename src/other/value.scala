package value

class Value[A](val value: A) {
}

trait HasGetValue {
  def getValue[B](b: B): Value[B]
}

class ValueHasGetValue[A](value: A) 
extends Value(value) 
with HasGetValue {
  def getValue[B](b: B) = {
    new Value(b)
  }
}

class NumberValue[A <: Number](val value: A) {
}

object Test {
  
  trait Value {
    type Type
    val value: Type
  }
  
  class NumberValue(number: Number) extends Value {
    type Type = Number
    val value = number
  }
  
    
  class BooleanValue(boolean: Boolean) extends Value {
    type Type = Boolean
    val value = boolean
  }
  
   
  /*
   * Accessing type members.
   */
  def getValue[SomeValue <: Value](value: SomeValue): SomeValue#Type = {
    value.value
  }
}