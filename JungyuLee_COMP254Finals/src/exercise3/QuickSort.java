package exercise3;
import java.util.Comparator;

class QuickSort {

  //-------- support for top-down quick-sort of queues --------
  /** Quick-sort contents of a queue. */
  public static <K> void quickSort(Queue<K> S, Comparator<K> comp) {
    int n = S.size();
    if (n < 2) return;                       // queue is trivially sorted
    // divide
    K pivot = S.first();                     // using first as arbitrary pivot
    Queue<K> L = new LinkedQueue<>();
    Queue<K> E = new LinkedQueue<>();
    Queue<K> G = new LinkedQueue<>();
    while (!S.isEmpty()) {                   // divide original into L, E, and G
      K element = S.dequeue();
      int c = comp.compare(element, pivot);
      if (c < 0)                             // element is less than pivot
        L.enqueue(element);
      else if (c == 0)                       // element is equal to pivot
        E.enqueue(element);
      else                                   // element is greater than pivot
        G.enqueue(element);
    }
    //System.out.println(L.toString());
    //System.out.println(E.toString());
    //System.out.println(G.toString());


    // conquer
    quickSort(L, comp);                      // sort elements less than pivot
    quickSort(G, comp);                      // sort elements greater than pivot
    // concatenate results
    while (!L.isEmpty())
      S.enqueue(L.dequeue());
    while (!E.isEmpty())
      S.enqueue(E.dequeue());
    while (!G.isEmpty())
      S.enqueue(G.dequeue());
  }

  //-------- support for in-place quick-sort of an array --------
  /** Quick-sort contents of a queue. */
  public static <K> void quickSortInPlace(K[] S, Comparator<K> comp) {
    quickSortInPlace(S, comp, 0, S.length-1);
  }

  /** Sort the subarray S[a..b] inclusive. */
  private static <K> void quickSortInPlace(K[] S, Comparator<K> comp,
                                                                   int a, int b) {
    if (a >= b) return;                // subarray is trivially sorted
    int left = a;
    int right = b-1;
    K pivot = S[b];
    K temp;                            // temp object used for swapping
    while (left <= right) {
      // scan until reaching value equal or larger than pivot (or right marker)
      while (left <= right && comp.compare(S[left], pivot) < 0) left++;
      // scan until reaching value equal or smaller than pivot (or left marker)
      while (left <= right && comp.compare(S[right], pivot) > 0) right--;
      if (left <= right) {             // indices did not strictly cross
        // so swap values and shrink range
        temp = S[left]; S[left] = S[right]; S[right] = temp;
        left++; right--;
      }
    }
    // put pivot into its final place (currently marked by left index)
    temp = S[left]; S[left] = S[b]; S[b] = temp;
    // make recursive calls
    quickSortInPlace(S, comp, a, left - 1);
    quickSortInPlace(S, comp, left + 1, b);
  }
  public static void main(String[] args)
  {
      Queue<Employee> employees = new LinkedQueue<>();

      employees.enqueue(new Employee(1, "Won", 3.4));
      employees.enqueue(new Employee(2, "Min", 6.3));
      employees.enqueue(new Employee(3, "Gyu", 4.5));
      employees.enqueue(new Employee(4, "Jun", 2.2));
      employees.enqueue(new Employee(5, "Lee", 1.1));

      // comparator based on employee salary
      Comparator<Employee> salaryComp = Comparator.comparingDouble(Employee::getEmployeeSalary);

      quickSort(employees, salaryComp);
      // System.out.println(employees);

      while (!employees.isEmpty()) {
          System.out.println(employees.dequeue());
      }
  }


    /**
     * This exercise sorts collections using different Comparators.
     * Use an Employee class
     * Write a main() method for the QuickSort class.
     * Create a queue of Employee objects and sort them in increasing order.
     * Populate the queue with several Employee objects and then call the quicksort method.
     * The Employee class has the following instance variables:
     * int employeeNumber
     * String employeeName
     * double employeeSalary
     * Hint: Your solution should use the employeeSalary to compare Employee objects.
     */
}

class Employee {
    private int employeeNumber;
    private String employeeName;
    private double employeeSalary;

    public Employee(int employeeNumber, String employeeName, double employeeSalary) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeSalary(double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", employeeName='" + employeeName + '\'' +
                ", employeeSalary=" + employeeSalary +
                '}';
    }
}