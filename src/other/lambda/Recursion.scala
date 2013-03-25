
trait Recursion {
  
  type Base <: Recursion
  
  type Next[Something <: Recursion] <: Recursion
  
}

trait Loop extends Recursion {
  
  type Base = Loop
  
  type Next[Something <: Recursion] = Something#Next[Something#Base]
  
}

object Halt {
  
  //type C = Loop#Next[Loop] 

}