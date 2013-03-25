package lambda

object Result {
  
  type λ[A <: Variable, B <: Lambda] = Abstraction[A, B]
  type ∘[A <: Lambda, B <: Lambda] = Application[A, B]
  
  type X = BaseVariable
  type Y = NotBaseVariable[X] // NotBaseVariable[BaseVariable]
  type Z = NotBaseVariable[Y] // NotBaseVariable[NotBaseVariable[BaseVariable]]
  type W = NotBaseVariable[Z] // NotBaseVariable[NotBaseVariable[NotBaseVariable[BaseVariable]]]
  
  type Add = Abstraction[X, Abstraction[Y, Abstraction[Z, Application[Application[X, Z], Application[Y, Z]]]]]
  type Two = Abstraction[X, Abstraction[Y, Application[X, Application[X, Y]]]]
  type Three = Abstraction[X, Abstraction[Y, Application[X, Application[X, Application[X, Y]]]]]
  type Five = Abstraction[X, Abstraction[Y, Application[X, Application[X, Application[X, Application[X, Application[X, Y]]]]]]]
  
  type show = X#substitute[X, X]
  implicitly[X#substitute[X, X] =:= BaseVariable]
  type result1 = Equal[X#evaluate, X]
  type result2 = Equal[Y#evaluate, Y]
  type result3 = Equal[X#apply[X], Application[X, X]]
  type result4 = Equal[X#apply[Y], Application[X, Y]]
  type result5 = Equal[Y#apply[X], Application[Y, X]]
  type result6 = Equal[Y#apply[Y], Application[Y, Y]]
  type result7 = X#Compare[BaseVariable]
  
  type _0 = BaseVariable
  type _1 = NotBaseVariable[_0]
  type _2 = NotBaseVariable[_1]
  type _3 = NotBaseVariable[_2]
  type _4 = NotBaseVariable[_3]
  type _5 = NotBaseVariable[_4]

  type r1 = _0#Compare[_0]#eq
  type r2 = _1#Compare[_0]#eq
  type r3 = _3#Compare[_4]#eq
  type r4 = _4#Compare[_4]#eq
  type r5 = _4#Compare[_3]#eq
  type r6 = Z#Compare[_3]#eq
  type r7 = Z#Compare[NotBaseVariable[NotBaseVariable[BaseVariable]]]#eq
  type r9 = Equal[True#If[Z, X], Z]
  type r10 = True#If[Z, X]
  type r12 = Equal[BaseVariable#Compare[BaseVariable]#eq#If[Z, X], Z]
  type r13 = BaseVariable#Compare[BaseVariable]#eq#If[Z, X]
  type r14 = BaseVariable#substitute[BaseVariable, BaseVariable]  

  
  implicitly[X =:= X]
  implicitly[X#substitute[X, Y] =:= Y]
  implicitly[X#substitute[Y, Y] =:= X]
  
  implicitly[Application[X, X] =:= Application[X, X]]
  implicitly[Application[X, X]#substitute[X, Y] =:= Application[Y, Y]]

  
  implicitly[Abstraction[W, X]#substitute[X, Y] =:= Abstraction[W, Y]]
  implicitly[Abstraction[X, X] =:= Abstraction[X, X]]
  
  implicitly[Application[Abstraction[X, X], X]#evaluate =:= X]
  implicitly[Application[Abstraction[X, X], Y]#evaluate =:= Y]
  implicitly[Abstraction[X, Application[X, X]]#evaluate =:= Abstraction[X, Application[X, X]]]
  implicitly[Application[Abstraction[X, Application[X, X]], Y]#evaluate =:= Application[Y, Y]]
  implicitly[Application[Abstraction[X, Abstraction[Z, Application[X, Z]]], Y]#evaluate =:= Abstraction[Z, Application[Y, Z]]]
  implicitly[Abstraction[X, Application[X, X]]#substitute[X, Y]#evaluate =:= Abstraction[X, Application[X, X]]]
  type result = Abstraction[X, Application[X, Abstraction[Y, Application[Y, Y]]]]#substitute[X, Z]#evaluate
  implicitly[Abstraction[X, Application[Z, Abstraction[Y, Application[Y, Y]]]]#substitute[Z, Y]#evaluate =:= Abstraction[X, Application[Y, Abstraction[Y, Application[Y, Y]]]]]
  
  implicitly[Application[Abstraction[X, Application[X, Abstraction[X, Application[X, X]]]], Y]#evaluate =:= Application[Y, Abstraction[X, Application[X, X]]]]
  
  //implicitly[Application[Application[Add, Two], Three] =:= Five]
  //implicitly[ =:= ]
  //implicitly[ =:= ]
  //implicitly[ =:= ]
  
  /*
  implicitly[(λ[X, X] ∘ X)#evaluate =:= X]
  implicitly[(λ[X, X] ∘ Y)#evaluate =:= Y]
  implicitly[∘[λ[X, X ∘ X], Y]#evaluate =:=  ∘[Y, Y]]
  
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
