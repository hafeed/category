package category.caseclasses

case class cat[o, a](
  
  /* Function that determines the source object of each arrow */
  s: a => o,
  
  /* Function that determines the destination object of each arrow */
  t: a => o, 
  
  /* Function that determines the composition of two arrows */
  ident: o => a,
  
  /* Function that determines the composition of two arrows */
  comp: (a, a) => a
)

case class set_arrow[a](source: Set[a], function: a => a, target: Set[a])

object FinSet {
  
  def set_s[a](arrow: set_arrow[a]) = arrow match {
    case set_arrow(a, _, _) => a
  }
  
  def set_t[a](arrow: set_arrow[a]) = arrow match {
    case set_arrow(_, _, b) => b 
  }
  
  def set_ident[a](a: Set[a]) = set_arrow(a, (x: a) => x, a)
  
  def set_comp[a](ab: set_arrow[a], cd: set_arrow[a]) = (ab, cd) match {
    case (set_arrow(a,f,b), set_arrow(c,g,d)) => 
      if (b == c)
        set_arrow(a, (x: a) => g(f(x)), d)
      else
        throw new Exception
  }
  
  val FinSet = cat(set_s, set_t, set_ident, set_comp).comp
  
}
