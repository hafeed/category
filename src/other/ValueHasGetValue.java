public class ValueHasGetValue<A> extends Value<A> implements HasGetValue {
  
  public ValueHasGetValue(A a) {
    super(a);
  }
  
  public <B> Value<B> getValue(B b) {
    return new Value<B>(b);
  }  
  
}