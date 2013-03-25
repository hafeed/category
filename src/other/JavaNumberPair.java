public class JavaNumberPair<A extends Number, B extends Number> {
  public final A first; 
  public final B second;
  public JavaNumberPair(A a, B b) {
    first = a;
    second = b;
  }
  
  public <T extends Number> JavaNumberPair<T, T> toPair(T value) {
    return new JavaNumberPair<T, T>(value, value);
  }
}