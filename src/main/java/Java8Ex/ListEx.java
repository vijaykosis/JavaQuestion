package Java8Ex;

import java.util.ArrayList;
import java.util.Iterator;

public class ListEx {
    public static void main(String args[]) {
        ArrayList<String> list = new ArrayList<>();

        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");

        Iterator itr = list.iterator();
        for(String t: list)
        {
            if ("D".equals(t)) {
                list.remove(t);
            }
        }

        while (itr.hasNext()) {

            String s = (String) itr.next();

            System.out.println(s);

            if ("D".equals(s)) {
                itr.remove();
            }
        }

        System.out.println(list);
/*
        for (String s : list) {
            list.remove(s);

        }
*/


    }

}
