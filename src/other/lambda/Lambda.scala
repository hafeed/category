package lambda.old

trait Lambda {
  
  type substitute[U <: Lambda] <: Lambda
  
  type apply[U <: Lambda] <: Lambda
  
  type evaluate <: Lambda

}

trait Application[S <: Lambda, T <: Lambda] extends Lambda {
  
  type substitute[U <: Lambda] = Application[S#substitute[U], T#substitute[U]]
  
  type apply[U /* <: Lambda */ ] = Nothing
  
  type evaluate = S#evaluate#apply[T]
  
}

trait Abstraction[T <: Lambda] extends Lambda {

  type substitute[U <: Lambda] = Abstraction[T#substitute[U]]
  
  type apply[U <: Lambda] = T#substitute[U]#evaluate
  
  type evaluate = Abstraction[T]
  
}

trait Variable extends Lambda {
  
  type substitute[U <: Lambda] = U
  
  type apply[U /* <: Lambda */] = Lambda
  
  type evaluate = Variable
  
}

trait Equal[T1 >: T2 <: T2, T2] {}

object Result {
  type A = Application[Y, Y]
  type B = Application[Abstraction[Application[X, X]], Y]#evaluate
  implicitly[A =:= B]
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
}
 


trait X extends Lambda {
  
  type substitute[U <: Lambda] = U
  
  type apply[U <: Lambda] = Application[X, U]
  
  type evaluate = X
  
}

trait Y extends Lambda {
  
  type substitute[U <: Lambda] = U
  
  type apply[U <: Lambda] = Application[Y, U]
  
  type evaluate = Y
  
}

trait Z extends Lambda {
  
  type substitute[U <: Lambda] = U
  
  type apply[U <: Lambda] = Application[Z, U]
  
  type evaluate = Z
  
}