package lambda

import scala.language.higherKinds

sealed trait Lambda {
  
  type substitute[Name <: Variable, Value <: Lambda] <: Lambda
  
  type apply[U <: Lambda] <: Lambda
  
  type evaluate <: Lambda
  
}

sealed trait Application[Function <: Lambda, Argument <: Lambda] extends Lambda {
  
  type substitute[Name <: Variable, Value <: Lambda] = Application[Function#substitute[Name, Value], Argument#substitute[Name, Value]]
  
  type apply[U <: Lambda] = Application[Application[Function, Argument], U]
  
  type evaluate = Function#evaluate#apply[Argument]
  
}

sealed trait Abstraction[Argument <: Variable, Body <: Lambda] extends Lambda {
  
  type substitute[Name <: Variable, Value <: Lambda] = Argument#Compare[Name]#eq#If[Abstraction[Argument, Body], Abstraction[Argument, Body#substitute[Name, Value]]]
  
  type apply[U <: Lambda] = Body#substitute[Argument, U]#evaluate
  
  type evaluate = Abstraction[Argument, Body#evaluate]
  
}

sealed trait Variable extends Lambda {
 
  type Match[IsBaseVariable <: Comparison, IsNotBaseVariable[SomeVariable <: Variable] <: Comparison] <: Comparison

  type Compare[Value <: Variable] <: Comparison
  
}

sealed trait BaseVariable extends Variable {
  
  type Match[IsBaseVariable <: Comparison, IsNotBaseVariable[SomeVariable <: Variable] <: Comparison] = IsBaseVariable
  
  type Compare[Name <: Variable] = Name#Match[EQ, NotEQ]
  
  type NotEQ[Value] = NE
   
  type substitute[Name <: Variable, Value <: Lambda] = Compare[Name]#eq#If[Value, BaseVariable]
 
  type apply[U <: Lambda] = Application[BaseVariable, U]
  
  type evaluate = BaseVariable
  
}

sealed trait NotBaseVariable[SomeVariable <: Variable] extends Variable {
  
  type Match[IsBaseVariable <: Comparison, IsNotBaseVariable[SomeOtherVariable <: Variable] <: Comparison] = IsNotBaseVariable[SomeVariable]
  
  type Compare[Value <: Variable] = Value#Match[NE, SomeVariable#Compare]
   
  type substitute[Name <: Variable, Value <: Lambda] = Compare[Name]#eq#If[Value, NotBaseVariable[SomeVariable]]
  
  type apply[U <: Lambda] = Application[NotBaseVariable[SomeVariable], U]
  
  type evaluate = NotBaseVariable[SomeVariable]

}

sealed trait Comparison {
  
  type Match[IfEQ <: Bool, IfNE <: Bool] <: Bool

  type eq = Match[True, False]
  
  type ne = Match[False, True]
  
}

sealed trait NE extends Comparison {
  
  type Match[IfEQ <: Bool, IfNE <: Bool] = IfNE
  
}

sealed trait EQ extends Comparison {
  
  type Match[IfEQ <: Bool, IfNE <: Bool] = IfEQ
  
}

sealed trait Bool {
  
  type If[T <: Lambda, F <: Lambda] <: Lambda
  
}

sealed trait False extends Bool {
  
  type If[T <: Lambda, F <: Lambda] = F
  
}

sealed trait True extends Bool {
  
  type If[T <: Lambda, F <: Lambda] = T

}

sealed trait Equal[T1 >: T2 <: T2, T2] {}