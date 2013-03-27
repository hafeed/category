package category.traits.any

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

case class Set_Arrow(source: Set[Any], function: Any => Any, target: Set[Any])

class FinSet extends Cat[Set[Any], Set_Arrow] {
  
  def s(arrow: Set_Arrow): Set[Any] = arrow.source
  
  def t(arrow: Set_Arrow): Set[Any] = arrow.target
  
  def ident(set: Set[Any]): Set_Arrow = Set_Arrow(set, (x: Any) => x, set)
  
  def comp(ab: Set_Arrow, bc: Set_Arrow): Set_Arrow = (ab, bc) match {
    case (Set_Arrow(a,f,b), Set_Arrow(c,g,d)) =>
      if (b == c)
        Set_Arrow(a, (x: Any) => g(f(x)), d)
      else
        throw new Exception
  }
  
}