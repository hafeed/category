package category

/*
 * TODO:
 * 
 * XXX Rename Object member type to Objct, in order for it not to clash with 
 * the Java type.
 * 
 * XXX Make the methods that return arrows and have commutativity requirements private
 * and wrap them into public methods that assert the commutativity requirement on 
 * Category.composition method using Category.equal method.
 * 
 * 
 * XXX ? How can uniqueness of the result be captured? Is there a type for an injective function?
 * ? Is there a type for an associative function?
 * 
 * XXX ? Show that initial objects, coequalizers, pushouts and products are colimits.
 */

trait Category {
    
  type Object
    
  type Arrow[A <: Object, B <: Object]
  
    /* Function that determines the source object of each arrow */
  def source[A <: Object, B <: Object](arrow: Arrow[A, B]): A
  
  /* Function that determines the destination object of each arrow */
  def target[A <: Object, B <: Object](arrow: Arrow[A, B]): B 
  
  /* Function that determines the composition of two arrows */
  def identity[A <: Object](objct: A): Arrow[A, A]
  
  /* Function that determines the composition of two arrows */
  def composition[A <: Object, B <: Object, C <: Object](arrow1: Arrow[A, B], arrow2: Arrow[B, C]): Arrow[A, C]
  
}