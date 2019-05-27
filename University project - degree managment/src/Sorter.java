import java.util.Comparator;

public class Sorter {
    public static <T extends Comparable<T>> void bSort(List<T> array){
        for (int i = 0; i < array.size(); i = i + 1) {
            for (int j = i + 1; j < array.size(); j = j + 1) {
                if (array.get(i).compareTo(array.get(j)) > 0) {
                    swap(array,i,j);
                }
            }
        }
    }

    private static void swap(List  array, int i, int j){
        Object tmp = array.get(i);
        array.set(i,array.get(j));
        array.set(j,tmp);
    }

    public static <T> void bSort(List<T> array, Comparator<T> comparator){
        for (int i = 0; i < array.size(); i = i + 1) {
            for (int j = i + 1; j < array.size(); j = j + 1) {
                if (comparator.compare(array.get(i),array.get(j)) > 0) {
                    swap(array,i,j);
                }
            }
        }
    }

}
