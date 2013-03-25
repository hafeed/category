package lambda.test

import scala.language.higherKinds

sealed trait Bool {
  type If[T <: Up, F <: Up, Up] <: Up
}

sealed trait False extends Bool {
  type If[T <: Up, F <: Up, Up] = F  
}

sealed trait True extends Bool {
  type If[T <: Up, F <: Up, Up] = T
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

sealed trait Variable {
   type Match[NonZero[N <: Variable] <: Up, IfZero <: Up, Up] <: Up

   type Compare[N <: Variable] <: Comparison
}

sealed trait BaseVariable extends Variable {
   type Match[NonZero[N <: Variable] <: Up, IfZero <: Up, Up] = IfZero

   type Compare[N <: Variable] = N#Match[NotEQ, EQ, Comparison]

   type NotEQ[A] = NE
}

sealed trait NotBaseVariable[N <: Variable] extends Variable {
   type Match[NonZero[N <: Variable] <: Up, IfZero <: Up, Up] = NonZero[N]

   type Compare[O <: Variable] = O#Match[N#Compare, NE, Comparison]
}

object test {
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
}
