package org.anthony.tablePrinter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.anthony.library.util.LibraryLogger;

public class TablePrinter {
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Column
    {
        String name();
        int weight() default 0;
        int width() default 10;
    }
    static Map<Class<?>, List<Method>> sortedColumns;

    static
    {
        sortedColumns = new HashMap<>();
    }

    public static <T> void PrintTable(List<T> list, Class<T> c) throws InvocationTargetException
    {
        try
        {
            if (!sortedColumns.containsKey(c))
            {
                //get methods
                List<Method> m = new ArrayList<>(Arrays.asList(c.getDeclaredMethods()));

                //filter
                m.removeIf((Method o) -> !o.isAnnotationPresent(Column.class));

                //sort greatest to least
                m.sort((Method l, Method r) -> l.getAnnotation(Column.class).weight() - r.getAnnotation(Column.class).weight());

                sortedColumns.put(c, m);
            }

            var columns = sortedColumns.get(c);
            int fullWidth = 1;
            for (var col : columns) {
                Column data = col.getAnnotation(Column.class);
                int wid = Math.max(data.width(), data.name().length() + 1);
                fullWidth += wid + 1;
            }

            char[] arr = new char[fullWidth];
            Arrays.fill(arr, '-');
            System.out.println(arr);

            //print headers
            for (Method m : columns)
            {
                Column data = m.getAnnotation(Column.class);
                int wid = Math.max(data.width(), data.name().length() + 1);
                
                System.out.printf("|%"+wid+"s", data.name());
            }
            System.out.println("|");
            System.out.println(arr);

            //print data
            for (T elm : list)
            {
                for (Method m : columns)
                {
                    Column data = m.getAnnotation(Column.class);
                    int wid = Math.max(data.width(), data.name().length() + 1);
                    String str = m.invoke(elm).toString();
                    if (str.length() > wid)
                    {
                        str = str.substring(0, wid - 3);
                        str += "...";
                    }

                    System.out.printf("|%"+wid+"s", str);
                }
                System.out.println("|");
            }

            System.out.println(arr);
        }
        catch(IllegalAccessException e)
        {
            LibraryLogger.LogException(e);
        }
    }
}
