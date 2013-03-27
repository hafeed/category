package category

case class Category[Object, Arrow](
  
  /* Function that determines the source object of each arrow */
  set_s: Arrow => Object,
  
  /* Function that determines the destination object of each arrow */
  set_t: Arrow => Object, 
  
  /* Function that determines the composition of two arrows */
  set_ident: Object => Arrow,
  
  /* Function that determines the composition of two arrows */
  set_comp: (Arrow, Arrow) => Arrow
)

/*
 * Operator type, consisting of a operator name and the names 
 * of the arguments to that operator.
 */

object FinKleisli {

  type Symbol = String
  type Element = String
  
  trait Term
  
  case class Operator(val symbol: Symbol, val arity: Set[Element])
  
  case class Variable(val element: Element) extends Term
  
  case class Application(val operator: Operator, substitution_function: Element => Term) extends Term
  
  case class Substitution(val source_set: Set[Element], substitution_function: Element => Term, target_set: Set[Element])
  
  /*
   * Apply a given substitution to a given term.
   */
  def apply_substitution(term: Term, substitution: Substitution): Term = {
    substitution match {
      case Substitution(source_set, substitution_function, target_set) => {
        term match {
          /*
           * If the term is a variable, apply the substitution function to it derictly.
           */
          case Variable(variable) => 
            substitution_function(variable)
          /*
           * If the term is an application, make sure the substitution function 
           * is applied to all variables that are passed to this operator first. 
           */
          case Application(operator, subsitution_function) =>
            Application(operator, (variable) => apply_substitution(substitution_function(variable), substitution))
        }
      }
    }
  }
  
  /*
   * Composition of two substitutions together into a single substitution.
   */
  def substitution_composition(substitution1: Substitution, substitution2: Substitution) = {
    (substitution1, substitution2) match {
      case (Substitution(c, g, d), Substitution(a, f, b)) =>
        if (b == c)
          Substitution(a, (x) => apply_substitution(f(x), substitution1), d)
        else
          throw new Exception  
    }
  }
  
  def substitution_identity(set: Set[Element]) = Substitution(set, (x) => Variable(x), set)
  
  def substitution_source(substitution: Substitution) = substitution match {
    case Substitution(a, _, _) => a
  }
  
  def substitution_target(substitution: Substitution) = substitution match {
    case Substitution(_, _, b) => b
  }
  
  val FinKleisli = Category(substitution_source, substitution_target, substitution_identity, substitution_composition)
  
}