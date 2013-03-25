package category

object Types {

/*
type Category[Object, Arrow] = (
  Set[Object], /* Set of objects */
  Set[Arrow], /* Set of arrows */
  Arrow => Object, /* Function that determines the source object of each arrow */
  Arrow => Object, /* Function that determines the destination object of each arrow */
  Object => Arrow, /* Function that determines the identity arrow for each object */
  (Arrow, Arrow) => Arrow /* Function that determines the composition of two arrows */
)
*/
  
trait Category[Object, Arrow] {
  
  /* Set of objects */
  val objects: Set[Object];
  
  /* Set of arrows */
  val arrows: Set[Arrow];
  
  /* Function that determines the source object of each arrow */
  val source: Arrow => Object;
  
  /* Function that determines the destination object of each arrow */
  val target: Arrow => Object;
  
  /* Function that determines the identity arrow for each object */
  val identity: Object => Arrow;
  
  /* Function that determines the composition of two arrows */
  val composition: (Arrow, Arrow) => Arrow;
  
}




def isCategory[Object, Arrow](category: Category[Object, Arrow]): Boolean = {
  /*
   * Check the reflexivity of arrows.
   */
  
  
  /*
   * Check the transitivity of the composition function.
   */
  
  
  /*
   * Check the reflexivity of composition function. 
   */
  
  
  /*
   * Check the associativity of composition function.
   */
  
  false;
}
/*


 */

/*
 * Determines whether the category commutes or not.
 */
def doesCommute[Object, Arrow](category: Category[Object, Arrow]): Boolean = {
  false;
}

/*
 * Determines whether one category is a subcategory of another category.
 */
def isSubcategory[Object, Arrow](category1: Category[Object, Arrow], category2: Category[Object, Arrow]): Boolean =  {
  false;
}

/*
 * Determines if a given arrow is an isomorphism in a given category.
 */
def isIsomorphism[Object, Arrow](category:  Category[Object, Arrow], arrow: Arrow): Boolean = {
  false;
}

/*
 * Determines if two given objects of a given category are isomorphic.
 */
def areIsomorphic[Object, Arrow](category: Category[Object, Arrow], object1: Object, object2: Object): Boolean = {
  false;
}

/*
 * Determines if a given arrow is a monic in a given category.
 */
def isMonic[Object, Arrow](category:  Category[Object, Arrow], arrow: Arrow): Boolean = {
  false;
}


/*
 * Determines if a given arrow is an epi in a given category.
 */
def isEpi[Object, Arrow](category:  Category[Object, Arrow], arrow: Arrow): Boolean = {
  false;
}


/* type Initial[, B] = () */

}