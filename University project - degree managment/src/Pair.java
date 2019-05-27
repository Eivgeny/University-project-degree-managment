public class Pair<E,S> {
	private E first ;
	private S second ;
	public Pair (E first, S second) {
		this.first = first ;
		this.second = second ;
	}
	public Pair (Pair<E,S> other) {
		first = other.first ;
		second = other.second ;
	}

	public Pair()
	{
		first = null;
		second = null;
	}
	public E getFirst () {
		return first ;
	}
	public S getSecond () {
		return second ;
	}

	public void setFirst (E value) {
		first = value ;
	}
	public void setSecond (S value) {
		second = value ;
	}
	public String toString () {
		return "(" + first + ", " + second + ")" ;
	}
	public boolean equals (Object other) {
		return other instanceof Pair<?,?> &&
				first.equals(((Pair<?,?>) other).getFirst()) &
				second.equals(((Pair<?,?>)other).getSecond());
	}
}