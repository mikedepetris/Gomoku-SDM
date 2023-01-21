package it.units.gomokusdm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utilities {

    //Implementazione adottata presente su StackOverflow https://stackoverflow.com/questions/5824825/efficient-way-to-divide-a-list-into-lists-of-n-size
    public static <T> List<List<T>> partition(List<T> list, int sizePartition){
        if(list.size() % sizePartition != 0) throw new RuntimeException();
        return new ArrayList<>(IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors
                        .groupingBy(e->e/sizePartition,Collectors
                                .mapping(list::get, Collectors.toList())))
                .values());
    }

}
