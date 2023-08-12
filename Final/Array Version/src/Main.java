import java.io.File;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        //initialising 2D array
        String[][] queues = new String[3][];
        queues[0] = new String[2];
        queues[1] = new String[3];
        queues[2] = new String[5];

        //print the menu options
        System.out.println(
                """
                        WelCome To Foodies Fave Food Center
                        
                        ----MENU----
                        100 or VFQ: View all Queues.
                        101 or VEQ: View all Empty Queues.
                        102 or ACQ: Add customer to a Queue.
                        103 or RCQ: Remove a customer from a Queue.
                        104 or PCQ: Remove a served customer.
                        105 or VCS: View Customers Sorted in alphabetical order.
                        106 or SPD: Store Program Data into file.
                        107 or LPD: Load Program Data from file.
                        108 or STK: View Remaining burgers Stock.
                        109 or AFS: Add burgers to Stock.
                        999 or EXT: Exit the Program.
                        """
        );

        boolean loopBody = true;
        int balanceBurgerStock = 50;
        int queueNo;
        int rowNo;
        boolean served;

        while (loopBody) {
            Scanner input = new Scanner(System.in);//resetting input to prevent from exception bug(Edited after interim submission)
            System.out.println();
            System.out.print("Choose an option from the above menu: ");
            String option = input.next().toUpperCase();
            System.out.println();
            switch (option) {
                //View all Queues.
                case "100":
                case "VFQ":
                    System.out.println("""
                            ********************
                            *     Cashiers     *
                            ********************
                                 1    2    3
                            """
                    );
                    queueFormat(queues, "O", "X");
                    System.out.println("\n" + "O – Occupied  X – Not Occupied");
                    break;
                //View all Empty Queues.
                case "101":
                case "VEQ":
                    System.out.println("""
                            ********************
                            *  Empty Cashiers  *
                            ********************
                                 1    2    3
                            """
                    );
                    queueFormat(queues, " ", "X");
                    System.out.println("\n" + "X – Not Occupied");
                    break;
                //Add customer to a Queue.
                case "102":
                case "ACQ":
                    try {
                        System.out.print("Enter the queue no(1-3): ");
                        queueNo = input.nextInt() - 1;
                        addCustomer(queues, queueNo);
                    } catch (InputMismatchException e) {
                        System.out.println("Enter an integer.");
                    }
                    break;
                //Remove a customer from a Queue.
                case "103":
                case "RCQ":
                    try {
                        System.out.print("Enter the queue no(1-3): ");
                        queueNo = input.nextInt() - 1;
                        System.out.print("Enter the row no" + "(1-" + queues[queueNo].length + ")" + ": ");
                        rowNo = input.nextInt() - 1;
                        served = false;
                        removeCustomer(queues, queueNo, rowNo, served, balanceBurgerStock);
                    } catch (InputMismatchException e) {
                        System.out.println("Enter an integer.");
                    }
                    break;
                //Remove a served customer.
                case "104":
                case "PCQ":
                    try {
                        System.out.print("Enter the queue no(1-3): ");
                        queueNo = input.nextInt() - 1;
                        rowNo = 0;
                        served = true;
                        balanceBurgerStock = removeCustomer(queues, queueNo, rowNo, served, balanceBurgerStock);
                    } catch (InputMismatchException e) {
                        System.out.println("Enter an integer.");
                    }
                    break;
                //View Customers Sorted in alphabetical order.
                case "105":
                case "VCS":
                    sortCustomer(queues);
                    break;
                //Store Program Data into file.
                case "106":
                case "SPD":
                    storeData(queues, balanceBurgerStock);
                    break;
                //Load Program Data from file.
                case "107":
                case "LPD":
                    balanceBurgerStock = loadStoredData(queues, balanceBurgerStock);
                    break;
                //View Remaining burgers Stock.
                case "108":
                case "STK":
                    System.out.println("Remaining burgers stock is " + balanceBurgerStock);
                    break;
                //Add burgers to Stock.
                case "109":
                case "AFS":
                    balanceBurgerStock = addBurgers(balanceBurgerStock);
                    break;
                //Exit the Program.
                case "999":
                case "EXT":
                    loopBody = false;
                    System.out.println("Exiting Program");
                    break;
                default:
                    System.out.println("""
                            Invalid input.
                            Ender a valid option from the above menu
                            """);
                    break;
            }
        }
    }

    public static void queueFormat(String[][] array, String Occupied, String notOccupied) {
        //find the length of the longest row (sub-array) in a 2D array
        int maxRowLength = 0;
        for (int i = 0; i < array.length; i++) {
            int subArrayLength = array[i].length;
            if (subArrayLength > maxRowLength) {
                maxRowLength = subArrayLength;
            }
        }
        //print the visual representation of queues
        for (int row = 0; row < maxRowLength; row++) {
            System.out.print(row + 1);
            for (int queue = 0; queue < array.length; queue++) {

                if (row < array[queue].length) {
                    if (array[queue][row] == null) {
                        System.out.print("    " + notOccupied);
                    } else {
                        System.out.print("    " + Occupied);
                    }
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println();
        }


    }

    public static void addCustomer(String[][] array,int queue) {
        //validate the queue number
        if(queue<array.length){
            // prompt customer names and add them to specific seats in a 2D array
            for (int row = 0; row<array[queue].length; row++) {
                    if (array[queue][row] == null) {

                        Scanner input = new Scanner(System.in);
                        System.out.print("Enter the customer's name to add the queue: ");
                        String customerName = input.next().toLowerCase();
                        array[queue][row] = customerName;
                        System.out.println(customerName + " was added to the row" + (row+1 )+ " of queue" + (queue+1));
                        break;
                    } else if (array[queue][array[queue].length-1] != null) {
                        System.out.println("This queue is full");
                        break;
                    }
            }
        }else{
            System.out.println("Invalid queue number.\nChoose between 1 to "+array.length);
        }


    }

    public static int removeCustomer(String[][] array, int queue, int row, boolean served, int burgerStock) {
        //validate the queue number and row number
        if (queue < array.length) {
            if (row < array[queue].length) {
                //performing null check
                if(array[queue][row]!=null){
                    //validate served customer and print appropriate message
                    if (served) {
                        System.out.println(array[queue][row] + " was served from queue" + (queue + 1));
                    } else {
                        System.out.println(array[queue][row] + " was removed from the row" + (row + 1) + " of queue" + (queue + 1));
                    }
                    //removing customer from a specific location and adjusting the queue
                    for (int i = row; i < array[queue].length; i++) {

                        if (i + 1 < array[queue].length) {
                            array[queue][i] = array[queue][i + 1];
                        } else {
                            array[queue][i] = null;
                        }

                    }
                    //validate served customer and update the stock
                    if (served) {
                        burgerStock = burgerStock - 5;
                        if (burgerStock <= 10) {
                            System.out.println("Only" + burgerStock + "burgers are available");
                        }
                    }
                }else{
                    if (served) {
                        System.out.println("Check the queue number");
                    } else {
                        System.out.println("Check the queue number and row number");
                    }
                }
            } else {
                System.out.print("Invalid row number");
            }
        } else {
            System.out.print("Invalid queue number");
        }
        return burgerStock;
    }

    public static void sortCustomer(String[][] array) {
        //counting the customer names
        int count = 0;
        for (int queue = 0; queue < array.length; queue++) {
            for (int row = 0; row < array[queue].length; row++) {
                if (array[queue][row] != null){
                    count++;
                }

            }
        }
        if(count==0){
            System.out.println("All queues are empty.\nPlease add customers to the queue");
        }
        //changing 2D array version to 1D array version
        String[] singleArray = new String[count];
        int index=0;
        for (int queue = 0; queue < array.length; queue++) {
            for (int row = 0; row < array[queue].length; row++) {
                if (array[queue][row] != null) {
                    singleArray[index] = array[queue][row];
                    index++;
                }
            }
        }
        //using bubble sort algorithm to sort array elements
        for (int index1 = 0; index1 < singleArray.length-1; index1++) {
            for (int index2 = 0; index2 < (singleArray.length - index1 - 1); index2++) {

                if (compareString(singleArray[index2],singleArray[index2 + 1]) > 0) { //comparing elements in 1D array using compareString method
                    String temp = singleArray[index2];
                    singleArray[index2] = singleArray[index2 + 1];
                    singleArray[index2 + 1] = temp;
                }
            }
        }
        //display the sorted elements
        for (String element : singleArray) {
            System.out.println(element);
        }
    }

    public static int compareString(String string1,String string2) {
        //finding the string which has the shortest length.
        int minLength = Math.min(string1.length(), string2.length());
        //comparing each character of strings
        for (int letterIndex = 0; letterIndex < minLength; letterIndex++) {
            if (string1.charAt(letterIndex) != string2.charAt(letterIndex)) {
                return string1.charAt(letterIndex) - string2.charAt(letterIndex);
            }
        }
        //comparing length of strings
        return string1.length() - string2.length();

    }

    public static int addBurgers(int balanceBurgerStock) {
        //validating remaining burgers in the stock
        if (balanceBurgerStock < 50) {
            System.out.println("Only" + (50 - balanceBurgerStock) + "burgers can be added to the stock.");
            Scanner input = new Scanner(System.in);
            while(true) {
                try{
                    //adding burgers to th stock
                    System.out.print("Enter the number of burgers to which you want to add the stock: ");
                    int burger = input.nextInt();
                    //checking the total amount of burger
                    if((balanceBurgerStock + burger)<=50){
                        balanceBurgerStock = balanceBurgerStock + burger;
                        System.out.print(burger + " burgers added to the stock.\n" + "Remaining Burger Stock is " + balanceBurgerStock);
                        break;
                    }else{
                        System.out.println("Only " + (50 - balanceBurgerStock) + " burgers can be added to the stock.");
                    }
                }catch(InputMismatchException e){
                    System.out.println("Enter an integer.");
                }
            }

        } else {
            System.out.print("Maximum Burger stock reached");

        }

        return balanceBurgerStock;
    }

    public static void storeData(String[][] array,int balanceBurgerStock) {
        try{
            //opening or creating file
            FileWriter file=new FileWriter("FoodiesFaveFoodCenter.txt");
            //writing data to the file
            file.write("--Customer Name list--\n");
            for (int queue = 0; queue < array.length; queue++) {
                for (int row = 0; row < array[queue].length; row++) {
                    file.write(array[queue][row] + "\n");

                }
            }
            file.write("-----------------------\n");
            file.write("Remaining burgers stock\n");
            file.write(balanceBurgerStock+"\n");
            file.close();//closing the file
            System.out.println("The current program data is saved in the document file named \"FoodiesFaveFoodCenter.txt\".");
        }catch(IOException e){
            System.out.println("An error occurred.");
        }

    }
    public static int loadStoredData(String[][] array,int balanceBurgerStock) {
        try{
            //opening a file
            File file=new File("FoodiesFaveFoodCenter.txt");
            //validate the existence of the file
            boolean fileExist= file.exists();
            if(fileExist){
                Scanner readFile=new Scanner(file);
                String fileData;
                //count the lines that the file contains
                int lineCount=0;
                while(readFile.hasNext()){
                    fileData=readFile.nextLine();
                    lineCount++;
                }
                readFile.close();//closing the file

                String[] dataArray=new String[lineCount];
                readFile=new Scanner(file);// Reset the scanner to the beginning of the file
                //read the file and save the data of the file to an array
                int index=0;
                while(readFile.hasNext()){
                    fileData=readFile.nextLine();
                    dataArray[index]=fileData;
                    index++;
                }
                readFile.close();//closing the file
                //access the specific data that stored in the array and loading it to the program
                balanceBurgerStock= Integer.parseInt(dataArray[dataArray.length-1]);
                index=1;
                if(dataArray.length>4){
                    while(index<dataArray.length-3){
                        for (int queue = 0; queue < array.length; queue++) {
                            for (int row = 0; row < array[queue].length; row++){
                                if(!dataArray[index].equals("null")){
                                    array[queue][row]=dataArray[index];
                                }else {
                                    array[queue][row]=null;
                                }
                                index++;
                            }
                        }
                    }
                }
                System.out.println("Data uploaded successfully from the document file named \"FoodiesFaveFoodCenter.txt\".");
            }else{
                System.out.println("This file was not created yet.\n"+"Enter 106 to the option to create the file");
            }


        }catch (IOException e){
            System.out.println("An error occurred.");
        }
        return balanceBurgerStock;

    }
}

