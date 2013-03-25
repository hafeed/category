package lambda.past

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

  /*
   * ! XXX If the Name is equal to the Argument, the Body should not be substituted, 
   * because of the clash of the variable names happens at this point.
   */
  type substitute[Name <: Variable, Value <: Lambda] = Abstraction[Argument, Body#substitute[Name, Value]]
  
  type apply[U <: Lambda] = Body#substitute[Argument, U]#evaluate
  
  type evaluate = Abstraction[Argument, Body#evaluate]
  
}

sealed trait Variable extends Lambda {
 
  type Match[IsBaseVariable <: T, IsNotBaseVariable[SomeVariable <: Variable] <: T, T] <:T

  type Compare[Value <: Variable] <: Comparison
  
}

sealed trait BaseVariable extends Variable {
  
  type Match[IsBaseVariable <: T, IsNotBaseVariable[SomeVariable <: Variable] <: T, T] = IsBaseVariable
  
  type Compare[Name <: Variable] = Name#Match[EQ, NotEQ, Comparison]
  
  type NotEQ[Value] = NE
   
  type substitute[Name <: Variable, Value <: Lambda] = Compare[Name]#eq#If[Value, BaseVariable, Lambda]
 
  type apply[U <: Lambda] = Application[BaseVariable, U]
  
  type evaluate = BaseVariable
  
}

sealed trait NotBaseVariable[SomeVariable <: Variable] extends Variable {
  
  type Match[IsBaseVariable <: T, IsNotBaseVariable[SomeOtherVariable <: Variable] <: T, T] = IsNotBaseVariable[SomeVariable]
  
  type Compare[Value <: Variable] = Value#Match[NE, SomeVariable#Compare, Comparison]
   
  type substitute[Name <: Variable, Value <: Lambda] = Compare[Name]#eq#If[Value, NotBaseVariable[SomeVariable], Lambda]
  
  type apply[U <: Lambda] = Application[NotBaseVariable[SomeVariable], U]
  
  type evaluate = NotBaseVariable[SomeVariable]

}

sealed trait Comparison {
  
  type Match[IfEQ <: Up, IfNE <: Up, Up] <: Up

  type eq = Match[True, False, Bool]
  
  type ne = Match[False, True, Bool]
  
}

sealed trait NE extends Comparison {
  
  type Match[IfEQ <: Up, IfNE <: Up, Up] = IfNE
  
}

sealed trait EQ extends Comparison {
  
  type Match[IfEQ <: Up, IfNE <: Up, Up] = IfEQ
  
}

sealed trait Bool {
  
  type If[T <: Up, F <: Up, Up] <: Up
  
}

sealed trait False extends Bool {
  
  type If[T <: Up, F <: Up, Up] = F
  
}

sealed trait True extends Bool {
  
  type If[T <: Up, F <: Up, Up] = T

}

sealed trait Equal[T1 >: T2 <: T2, T2] {}