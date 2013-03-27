object Test extends App {
  
  
   abstract class TypedSet[+A] {
    
    type Type = A
    
    var set: Set[Type]
    
  }
  
  
  def test[B <: TypedSet[Number] /* { type Type = Number } */](set: B): Unit = {
    
  }
  
  def test2[B <: Number](set: B): Unit = {
    
  }
  
  val tset1 = new TypedSet[Number] {
    //type Type = Number
    var set = Set[Number](1, 2, 3)
  }
  
  val tset2 = new TypedSet[Integer] {
    //type Type = Integer
    var set = Set[Integer](1, 2, 3)
  }  
  
  val tset3 = new TypedSet[String] {
    //type Type = Integer
    var set = Set[String]("1", "2", "3")
  }  
  
  test(tset2)
  
  val a: Number = 0
  val b: Integer = 0
  test2(a)

  
  
  
  
  
  
}