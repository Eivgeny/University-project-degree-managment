public class ComparableExample implements Comparable<ComparableExample>{
    public int getNumber() {
        return number;
    }

    private int number;

    public ComparableExample(int number) {
        this.number = number;
    }
    /**
     * Defines order between 2 instances of ComparabaleExample
     *
     * @param  other
     *            Instance of ComparableExample
     * @return
     * -1 if this is before other
     * 1 if other is before this
     * 0 if none of the above apply
     *
     * @example
     * ComparableExample n1 = new ComparableExample(2);
     * ComparableExample n2 = new ComparableExample(3);
     * n1.compareTo(n2) --> -1
     * n2.compareTo(n1) --> 1
     * n1.compareTo(n1) --> 0
     *
     */
    @Override
    public int compareTo(ComparableExample other) {
        return this.number - other.getNumber();
    }

    public static void main(String[] args)  {
        ComparableExample n1 = new ComparableExample(2);
        ComparableExample n2 = new ComparableExample(3);
        System.out.println(n1.compareTo(n2));
        System.out.println(n2.compareTo(n1));
        System.out.println(n1.compareTo(n1));

        List<ComparableExample> lst = new LinkedList<ComparableExample>();
        for (int i = 0; i < 10; i = i + 1)
        {
            lst.add(new ComparableExample(i % 4));
        }
        System.out.println("Unsorted list:");
        for (int i = 0; i < 10; i = i + 1)
        {
           System.out.print(lst.get(i).getNumber() + ",");
        }
        System.out.println();
        /*Using Sorter*/
        Sorter.bSort(lst);
        /* Now the list is sorted*/
        System.out.println("Sorted list:");
        for (int i = 0; i < 10; i = i + 1)
        {
            System.out.print(lst.get(i).getNumber() + ",");
        }

    }
}
