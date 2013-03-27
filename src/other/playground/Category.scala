package category.playground

object Category {
  def identity[A]: A => A = a => a
  def composition[A, B, C](f: A => B, g: B => C): (A => C) = g compose f  
}

