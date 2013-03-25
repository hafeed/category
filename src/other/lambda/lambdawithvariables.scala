package lambda.variables
/*
sealed trait Lambda {
  
  type substitute[Name <: Variable, Value <: Lambda] <: Lambda
  
  type apply[U <: Lambda] <: Lambda
  
  type evaluate <: Lambda

}

trait Application[Function <: Lambda, Argument <: Lambda] extends Lambda {
  
  type substitute[Name <: Variable, Value <: Lambda] = Application[Function#substitute[Name, Value], Argument#substitute[Name, Value]]
  
  type apply[U <: Lambda] = Application[Application[Function, Argument], U]
  
  type evaluate = Function#evaluate#apply[Argument]
  
}

trait Abstraction[Argument <: Variable, Body <: Lambda] extends Lambda {

  /*
   * ! XXX If the Name is equal to the Argument, the Body should not be substituted, 
   * because of the clash of the variable names happens at this point.
   */
  type substitute[Name <: Variable, Value <: Lambda] = Abstraction[Argument, Body#substitute[Name, Value]]
  
  type apply[U <: Lambda] = Body#substitute[Argument, U]#evaluate
  
  type evaluate = Abstraction[Argument, Body#evaluate] // Might be worth removing #evaluate
  
}

trait Variable extends Lambda {
  
  type IsX <: Boolean
  
  type IsY <: Boolean
  
  type IsW <: Boolean
  
  type IsZ <: Boolean

}

trait Equal[T1 >: T2 <: T2, T2] {}

trait W extends Variable {
  
  type IsW = True
  
  type IsX = False
  
  type IsY = False
  
  type IsZ = False
  
  type substitute[Name <: Variable, Value <: Lambda] = Name#IsW#If[Value, W, Lambda] 
  
  type apply[U <: Lambda] = Application[W, U]
  
  type evaluate = W
  
}

trait X extends Variable {
  
  type IsW = False
  
  type IsX = True
  
  type IsY = False
  
  type IsZ = False
  
  type substitute[Name <: Variable, Value <: Lambda] = Name#IsX#If[Value, X, Lambda] 
  
  type apply[U <: Lambda] = Application[X, U]
  
  type evaluate = X
  
}

trait Y extends Variable {
  
  type IsW = False
  
  type IsX = False
  
  type IsY = True
  
  type IsZ = False
  
  type substitute[Name <: Variable, Value <: Lambda] = Name#IsY#If[Value, Y, Lambda] 
  
  type apply[U <: Lambda] = Application[Y, U]
  
  type evaluate = Y
  
}

trait Z extends Variable {
  
  type IsW = False
  
  type IsX = False
  
  type IsY = False
  
  type IsZ = True
  
  type substitute[Name <: Variable, Value <: Lambda] = Name#IsZ#If[Value, Z, Lambda] 
  
  type apply[U <: Lambda] = Application[Z, U]
  
  type evaluate = Z
  
}

sealed trait Boolean {
  
  type If[T <: Up, F <: Up, Up]
  
}

trait False extends Boolean {
  
  type If[T <: Up, F <: Up, Up] = F
  
}

trait True extends Boolean {
  
  type If[T <: Up, F <: Up, Up] = T

}

object Result {
  
  type λ[A <: Variable, B <: Lambda] = Abstraction[A, B]
  type ∘[A <: Lambda, B <: Lambda] = Application[A, B]
  
  type Add = Abstraction[X, Abstraction[Y, Abstraction[Z, Application[Application[X, Z], Application[Y, Z]]]]]
  type Two = Abstraction[X, Abstraction[Y, Application[X, Application[X, Y]]]]
  type Three = Abstraction[X, Abstraction[Y, Application[X, Application[X, Application[X, Y]]]]]
  type Five = Abstraction[X, Abstraction[Y, Application[X, Application[X, Application[X, Application[X, Application[X, Y]]]]]]]
  
  
  implicitly[X =:= X]
  implicitly[Application[X, X] =:= Application[X, X]]
  implicitly[Abstraction[X, X] =:= Abstraction[X, X]]
  implicitly[Application[X, X]#substitute[X, Y] =:= Application[Y, Y]]
  implicitly[Abstraction[W, X]#substitute[X, Y] =:= Abstraction[W, Y]]
  implicitly[Application[Abstraction[X, X], X]#evaluate =:= X]
  implicitly[(λ[X, X] ∘ X)#evaluate =:= X]
  implicitly[X#substitute[X, Y] =:= Y]
  implicitly[X#substitute[Y, Y] =:= X]
  implicitly[Application[Abstraction[X, Application[X, X]], Y]#evaluate =:= Application[Y, Y]]
  //implicitly[ =:= ]
  //implicitly[ =:= ]
  //implicitly[ =:= ]
  //implicitly[ =:= ]
  /*
  //implicitly[Application[Y, Y] =:= Application[Abstraction[Application[X, X]], Y]#evaluate]
  type result0 = Equal[B, A]
  type result1 = Equal[Application[Abstraction[Application[X, X]], Y]#evaluate, Application[Y, Y]]
  type result2 = Equal[Application[Abstraction[X], X]#evaluate, X]
  type result3 = Equal[Application[Abstraction[X], Y]#evaluate, Y]
  type result4 = Equal[Application[Abstraction[Abstraction[X]], Y]#evaluate, Abstraction[Y]]
  type result5 = Equal[Application[Abstraction[X], Abstraction[X]]#evaluate, Abstraction[X]]
  type result6 = Equal[Application[Abstraction[X], Application[X, X]]#evaluate, Application[X, X]]
  type result7 = Equal[Application[Abstraction[Application[X, X]], X]#evaluate, Application[X, X]]
  type result8 = Equal[Application[Abstraction[Application[X, Application[Y, Z]]], X]#evaluate, Application[X, Application[X, X]]]
  // StackOVerflow: type x = Application[Abstraction[Application[X, X]], Abstraction[Application[X, X]]]#evaluate
  */
}
*/