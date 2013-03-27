package category.traits.arrow

class DoesNotExistException extends Exception

trait Category[Object, Arrow] {
  
  case class CategoryObject(val categoryObject: Object)
  
  case class CategoryArrow(val source: CategoryObject, val arrow: Arrow, val target: CategoryObject)
  
  /* 
   * This function must be total, 
   * even though not every two arrows are composable, 
   * because the composeCategoryArrows ensures that the 
   * arguments passed to the function must be composable.
   * 
   * This function is allowed to tell wrong answers to the
   * questioins that it is guaranteed to be never asked.
   */
  def comp(a1: Arrow,  a2: Arrow): Arrow
  
  /*
   * This function must be total and must
   * to provide an arrow for every object.
   */
  def ident(o: Object): Arrow
  
  def composeCategoryArrows(categoryArrow1: CategoryArrow, categoryArrow2: CategoryArrow): CategoryArrow = {
    if (categoryArrow1.target == categoryArrow2.source) {
      
      /*
       * Make sure that the composition of an identity arrow results in the other arrow.
       */
      if (categoryArrow1 == getIdentityCategoryArrow(categoryArrow1.source)) {
        categoryArrow2
      } else if (categoryArrow2 == getIdentityCategoryArrow(categoryArrow2.source)) {
        categoryArrow1
      }
      
      CategoryArrow(categoryArrow1.source, comp(categoryArrow1.arrow, categoryArrow2.arrow), categoryArrow2.target)
    } else {
      throw new DoesNotExistException // InvalidArgumentException
    }
  }
  
  /* Function that determines the composition of two arrows */
  def getIdentityCategoryArrow(categoryObject: CategoryObject): CategoryArrow = {
    CategoryArrow(categoryObject, ident(categoryObject.categoryObject), categoryObject)
  }
  
  def composeCategoryArrow(categoryArrow1: CategoryArrow, categoryArrow2: CategoryArrow): CategoryArrow = {
    CategoryArrow(categoryArrow1.source, comp(categoryArrow1.arrow, categoryArrow2.arrow), categoryArrow2.target)
  }

}

case class Set_Arrow(source: Set[Any], function: Any => Any, target: Set[Any])

class FinSet extends Category[Set[Any], Set_Arrow] {
  
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