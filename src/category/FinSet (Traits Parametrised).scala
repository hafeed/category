package category.traits.parametrised

trait Cat[o, a] {
  
  /* Function that determines the source object of each arrow */
  def s(arrow: a): o
  
  /* Function that determines the destination object of each arrow */
  def t(arrow: a): o 
  
  /* Function that determines the composition of two arrows */
  def ident(objct: o): a
  
  /* Function that determines the composition of two arrows */
  def comp(arrow1: a, arrow2: a): a
  
}

case class Set_Arrow[a](source: Set[a], function: a => a, target: Set[a])

class FinSet[a] extends Cat[Set[a], Set_Arrow[a]] {
  
  def s(arrow: Set_Arrow[a]): Set[a] = arrow.source
  
  def t(arrow: Set_Arrow[a]): Set[a] = arrow.target
  
  def ident(set: Set[a]): Set_Arrow[a] = Set_Arrow(set, (x: a) => x, set)
  
  def comp(arrow1: Set_Arrow[a], arrow2: Set_Arrow[a]): Set_Arrow[a] = (arrow1, arrow2) match {
    case (Set_Arrow(a,f,b), Set_Arrow(c,g,d)) =>
      if (b == c)
        Set_Arrow(a, (x: a) => g(f(x)), d)
      else
        throw new Exception
  }
  
}