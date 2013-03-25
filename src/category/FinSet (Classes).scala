package category.classes

case class cat[o, a](
  
  /* Function that determines the source object of each arrow */
  set_s: a => o,
  
  /* Function that determines the destination object of each arrow */
  set_t: a => o, 
  
  /* Function that determines the composition of two arrows */
  set_ident: o => a,
  
  /* Function that determines the composition of two arrows */
  set_comp: (a, a) => a
)

class Set_Arrow[a](val source: Set[a], val function: a => a, val target: Set[a]) {
}

object FinSet {
  
  def set_s[a](arrow: Set_Arrow[a]) = arrow.source
  
  def set_t[a](arrow: Set_Arrow[a]) = arrow.target
  
  def set_ident[a](set: Set[a]) = new Set_Arrow[a](set, (element: a) => element, set)

  def set_comp[a](ab: Set_Arrow[a], cd: Set_Arrow[a]) = {
    if (ab.target.equals(cd.source)) {
      new Set_Arrow(ab.source, (element: a) => cd.function(ab.function(element)), cd.target)
    } else {
      throw new Exception
    }
  }
  
  val FinSet = cat(set_s, set_t, set_ident, set_comp) 
}