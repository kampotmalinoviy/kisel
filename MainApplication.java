
package bsu.rfe.java.group10.lab1.Kisel.varA3;
import java.util.Scanner;
public class MainApplication {
    @SuppressWarnings("unchecked")

    public static void main(String[] args) throws Exception {

         Food[] breakfast = new Food[20];
        Scanner scanner = new  Scanner(System.in);

        int Apple_count = 0, Cheese_count = 0, Milk_count = 0;

        System.out.println("Enter the components of the breakfast");

        String input = scanner.nextLine();
        String[] buff = input.split(" ");

        int itemsSoFar = 0;

        for (String arg : buff) {
            String[] parts = arg.split("/");
            if (parts[0].equals("Cheese")) {
                breakfast[itemsSoFar] = new Cheese();
            } else if (parts[0].equals("Apple")) {
                breakfast[itemsSoFar] = new Apple(parts[1]);
            } else if (parts[0].equals("Milk")) {
                breakfast[itemsSoFar] = new Milk(parts[1]);
            }
            itemsSoFar++;

        }
// Перебор всех элементов массива
            for (Food item : breakfast) {
                if (item != null) {
                    if (item.equals(new Apple("big"))){
                        Apple_count++;
                    }
                    if (item.equals(new Cheese())) {
                        Cheese_count++;
                    }
                    if (item.equals(new Milk("1,5%"))) {
                        Milk_count++;
                    }

// Если элемент не null – употребить продукт
                    item.consume();
                }
                else
// Если дошли до элемента null – значит достигли конца
// списка продуктов, ведь 20 элементов в массиве было
// выделено с запасом, и они могут быть не
// использованы все
                    break;
            }
            System.out.println("Number of apples eaten:" + Apple_count);
            System.out.println("Number of cheese eaten:" + Cheese_count);
            System.out.println("Bottles of milk drunk:" + Milk_count);
            System.out.println("Have a good day!");
        }
    }
