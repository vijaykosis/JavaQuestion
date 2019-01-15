package Java8Ex.FunctionEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionTRExample {
    public static void main(String args[]) {
        Function<Employee, String> funcEmpToString = (Employee e) -> {
            return e.getName();
        };
        List<Employee> employeeList =
                Arrays.asList(new Employee("Tom Jones", 45),
                        new Employee("Harry Major", 25),
                        new Employee("Ethan Hardy", 65),
                        new Employee("Nancy Smith", 15),
                        new Employee("Deborah Sprightly", 29));
        List<String> empNameList = convertEmpListToNamesList(employeeList, funcEmpToString);
        empNameList.forEach(System.out::println);

        Function<Employee, Integer> funcAgeToInteger = (Employee e) -> {

            return e.getAge();
        };

        List<Integer> empAgeList=convertEmpListToAgeList(employeeList,funcAgeToInteger);
        empAgeList.forEach(System.out::println);
    }

    public static List<String> convertEmpListToNamesList(List<Employee> employeeList, Function<Employee, String> funcEmpToString) {
        List<String> empNameList = new ArrayList<String>();
        for (Employee emp : employeeList) {
            empNameList.add(funcEmpToString.apply(emp));
        }
        return empNameList;
    }


    public static List<Integer> convertEmpListToAgeList(List<Employee> employeeList,Function<Employee, Integer> func)
    {
      List<Integer> ageList=employeeList.stream().map(x->func.apply(x)).collect(Collectors.toList());
        return ageList;
    }
}

class Employee {
    private String name;
    private int age;


    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}