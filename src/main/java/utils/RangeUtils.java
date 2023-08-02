package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 戴志豪
 * @date 2021/5/15 17:32
 */
public class RangeUtils {

    public static List<Integer> range(Integer start,Integer end){

        List<Integer> integerList = new ArrayList<>();
        for(int i = start+1;i<end;i++){
            integerList.add(i);
        }
        return  integerList;
    }

}
