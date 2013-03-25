package booleans

sealed trait Bool {
  type If[T <: Bool, F <: Bool] <: Bool
}

sealed trait False extends Bool {
  type If[T <: Bool, F <: Bool] = F
}

sealed trait True extends Bool {
  type If[T <: Bool, F <: Bool] = T
}

sealed trait Number {
  
  type Match[IsZero <: Comparison, IsNotZero[SomeNumber <: Number] <: Comparison] <: Comparison

  type Compare[Value <: Number] <: Comparison
  
}

sealed trait Zero extends Number {
  
  type Match[IsZero <: Comparison, IsNext[SomeNumber <: Number] <: Comparison] = IsZero
  
  type Compare[Value <: Number] = Value#Match[Equal, ValueNotEqual]
  
  type ValueNotEqual[Value] = NotEqual
   
}

sealed trait Next[SomeNumber <: Number] extends Number {
  
  type Match[IsZero <: Comparison, IsNext[SomeOtherNumber <: Number] <: Comparison] = IsNext[SomeNumber]
  
  type Compare[Value <: Number] = Value#Match[NotEqual, SomeNumber#Compare]
   
}

sealed trait Comparison {
  
  type Match[IfEqual <: Bool, IfNotEqual <: Bool] <: Bool

  type equal = Match[True, False]
  
  type notequal = Match[False, True]
  
}

sealed trait NotEqual extends Comparison {
  
  type Match[IfEqual <: Bool, IfNotEqual <: Bool] = IfNotEqual
  
}

sealed trait Equal extends Comparison {
  
  type Match[IfEqual <: Bool, IfNotEqual <: Bool] = IfEqual
  
}



object Bool {
  
  type Not[A <: Bool] = A#If[False, True]
  
  type And[A <: Bool, B <: Bool] = A#If[B, False]
  
  type Or[A <: Bool, B <: Bool] = A#If[True, B]
  
  type result = Equals[True And False, Not[True]]
    
  type Number0 = Zero
  type Number1 = Next[Zero]
  type Number2 = Next[Next[Zero]]
  type Number3 = Next[Number2]
  
  type r1 = Number0#Compare[Number0]#equal
  type r2 = Number2#Compare[Number1]#equal

  trait Apple extends Bool {}
  trait Orange extends Bool{}
  type choose[Value1 <: Number, Value2 <: Number] = Value1#Compare[Value2]#equal#If[Apple, Orange]
  type r3 = Equals[choose[Number1, Number2], Orange]

  /*
  def toBoolean[B <: Bool](implicit b: BoolRep[B]): Boolean = b.value
  class BoolRep[B <: Bool](val value: Boolean)
  implicit val falseRep: BoolRep[False] = new BoolRep(false)
  implicit val trueRep: BoolRep[True] = new BoolRep(true)

  toBoolean[ True And False Or Not[False] ]
  */
   
  /*
  class NumberWitness[N <: Number](val value: Integer)
  
  implicit val Number0Witness: NumberWitness[Number0] = new NumberWitness(0)
  implicit val Number1Witness: NumberWitness[Number1] = new NumberWitness(1)
  implicit val Number2Witness: NumberWitness[Number2] = new NumberWitness(2)
  
  def toNumber[N <: Number](implicit number: NumberWitness[N]): Integer = number.value
  
  print(toNumber[Number2])
  */
  
}

object Test  extends App {
  
  trait Complex {
    type C
    def +(c1: C, c2: C): C
  }
   
  type Number0 = Zero
  type Number1 = Next[Zero]
  type Number2 = Next[Next[Zero]]
  type Number3 = Next[Number2]
   
  val z:Zero = null
  val y:Next[Zero] = null
  
  class NumberValue[T <: Number](val value: Integer) {}

  implicit val ZeroToInt: NumberValue[Zero] = new NumberValue[Zero](0)
  
  implicit def NextToInt[N <: Number](implicit number : NumberValue[N]): NumberValue[Next[N]] = 
     new NumberValue[Next[N]](1 + number.value)
     
  def toNumber[N <: Number](implicit number : NumberValue[N]): Integer = number.value
  
  print(toNumber[Number3])
  
}

sealed trait Equals[T1 >: T2 <: T2, T2] {}
