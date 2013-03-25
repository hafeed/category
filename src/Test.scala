object Test extends App {
  
  def test[B <: Any](set: Set[B]): Unit = {
    
  }
  
  test(Set[Int](1, 2))

}