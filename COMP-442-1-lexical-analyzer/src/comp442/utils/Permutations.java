package comp442.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Permutations {

	private static<T> List<T> concat(List<T> l, T i){
		List<T> retval = new ArrayList<T>(l.size() + 1);
		for(T x : l){
			retval.add(x);
		}
		retval.add(i);
		return retval;
	}
	
	public static<T> List<List<T>> permutations(T numbers[], int n){
		if(n <= 0){
			return Collections.singletonList(Collections.<T>emptyList());
		}else {
			List<List<T>> nMinusOne = permutations(numbers, n-1);
			List<List<T>> values = new ArrayList<List<T>>(numbers.length * nMinusOne.size());
			for(List<T> perm : nMinusOne){
				for(T i : numbers){
					values.add(concat(perm, i));
				}
			}
			return values;
		}
	}
	
}
