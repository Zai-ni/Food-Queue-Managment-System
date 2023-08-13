
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList<Customer>> queues= new ArrayList<>();//initiate an 2D arraylist
        FoodQueue queue1=new FoodQueue(2,new ArrayList<>());//initiate a FoodQueue class object
        FoodQueue queue2=new FoodQueue(3,new ArrayList<>());//initiate a FoodQueue class object
        FoodQueue queue3=new FoodQueue(5,new ArrayList<>());//initiate a FoodQueue class object
        queues.add(queue1.getQueue());//add food queue class object to an array list
        queues.add(queue2.getQueue());//add food queue class object to an array list
        queues.add(queue3.getQueue());//add food queue class object to an array list


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
                        110 or IFQ: View income for each queue.
                        111 or MQC: View main queue customer names.
                        112 or WQC: View waiting queue customer names.
                        113 or CMC: Change main queue customer details.
                        114 or CWC: Change waiting queue customer details.
                        999 or EXT: Exit the Program.
                        """
        );

        boolean loopBody = true;
        int balanceBurgerStock = 50;
        int queueNo;
        int rowNo;
        boolean served;
        int queue1Income=0;
        int queue2Income=0;
        int queue3Income=0;
        WaitingQueue  waitingQueue= new WaitingQueue();//initiate a WaitingQueue class Object

        while (loopBody) {
            Scanner input = new Scanner(System.in);
            System.out.println();
            System.out.print("Choose an option from the above menu: ");
            String option = input.next().toUpperCase();//prompt user option
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
                    allQueueFormat(queues);
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
                    emptyQueueFormat(queues);
                    break;
                //Add customer to a Queue.
                case "102":
                case "ACQ":
                    try{
                        //prompt customer details
                        System.out.print("Enter the customer's first name: ");
                        String firstName = input.next().toLowerCase();
                        System.out.print("Enter the customer's second name: ");
                        String secondName = input.next().toLowerCase();
                        System.out.print("Enter the number of burgers required: ");
                        int noOfBurgers = input.nextInt();
                        if (noOfBurgers<=50){
                            Customer customer = new Customer(firstName, secondName, noOfBurgers);//initiate customer class object
                            addCustomer(customer,queues,waitingQueue);
                        }else{
                            System.out.println("The maximum number of burgers that can be ordered is 50.");
                        }

                    }catch(Exception e){
                        System.out.println("Enter an integer for the number of required burgers.");
                    }

                    break;
                //Remove a customer from a Queue.
                case "103":
                case "RCQ":
                    try {
                        //prompt queue position to user
                        System.out.print("Enter the queue no(1-3): ");
                        queueNo = input.nextInt() - 1;
                        System.out.print("Enter the row no" + "(1-"+ queues.get(queueNo).size()+ ")" + ": ");
                        rowNo = input.nextInt() - 1;

                        served = false;
                        removeCustomer(queueNo, rowNo, served, balanceBurgerStock,0,queues,waitingQueue);
                    } catch (Exception e) {
                        System.out.println("Enter an integer.");
                    }
                    break;
                //Remove a served customer.
                case "104":
                case "PCQ":
                    try {
                        int customerBurger=0;
                        //prompt served queue number
                        System.out.print("Enter the queue no(1-3): ");
                        queueNo = input.nextInt() - 1;
                        rowNo = 0;
                        served = true;
                        customerBurger= removeCustomer(queueNo, rowNo, served, balanceBurgerStock,customerBurger,queues,waitingQueue);

                        //updating each queue income
                        if(customerBurger!=0){
                            if (queueNo == 0) {
                            queue1Income = queue1Income + (customerBurger * 650);
                            } else if (queueNo == 1) {
                            queue2Income = queue2Income + (customerBurger * 650);
                            } else if (queueNo == 2) {
                            queue3Income = queue3Income + (customerBurger * 650);
                            }
                            balanceBurgerStock=balanceBurgerStock-customerBurger;
                        }
                    } catch (Exception e) {
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
                    storeData(queues,waitingQueue.getWaitingQueue(),balanceBurgerStock,queue1Income,queue2Income,queue3Income);
                    break;
                //Load Program Data from file.
                case "107":
                case "LPD":
                    int[] shopData={balanceBurgerStock,queue1Income,queue2Income,queue3Income};
                    loadStoredData(queues,waitingQueue,shopData);
                    balanceBurgerStock=shopData[0];
                    queue1Income=shopData[1];
                    queue2Income=shopData[2];
                    queue3Income=shopData[3];
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
                //View each queue income
                case "110":
                case "IFQ":
                    System.out.println("Queue1 income is $" + queue1Income);
                    System.out.println("Queue2 income is $" + queue2Income);
                    System.out.println("Queue3 income is $" + queue3Income);
                    break;
                //View main queue customer names
                case "111":
                case "MQC":
                    for (ArrayList<Customer> mainQueue:queues){
                        for(Customer element:mainQueue){
                            if (element!=null){
                                System.out.println(element.getFullName());
                            }
                        }
                    }
                    break;
                //View waiting queue customer names
                case "112":
                case "VWQ":
                    Customer[] waitingQueueList= waitingQueue.getWaitingQueue();
                    for(Customer element:waitingQueueList){
                        if (element!=null){
                            System.out.println(element.getFullName());
                        }
                    }
                    break;
                //Change main queue customer details
                case "113":
                case "CMC":
                    changeCustomerDetails(queues,waitingQueue,"main");
                    break;
                //Change waiting queue customer details
                case "114":
                case "CWC":
                    changeCustomerDetails(queues,waitingQueue,"waiting");
                    break;
                //Exit from program
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

    public static int maxQueueLength(ArrayList<ArrayList<Customer>> queues) {
        //find the length of the longest row (sub-array) in a 2D array
        int maxQueueLength = 0;
        for (int index = 0; index < queues.size(); index++) {
            int queueLength = queues.get(index).size();
            if (queueLength > maxQueueLength) {
                maxQueueLength = queueLength;
            }
        }
        return maxQueueLength;
    }

    public static void allQueueFormat(ArrayList<ArrayList<Customer>> queues) {
        int maxQueueLength = maxQueueLength(queues);

        //print the visual representation of queues
        for (int slot = 0; slot < maxQueueLength; slot++) {
            System.out.print(slot + 1);
            for (int queue = 0; queue < queues.size(); queue++) {

                if (slot < queues.get(queue).size()) {
                    if (queues.get(queue).get(slot) == null) {
                        System.out.print("    X");
                    } else {
                        System.out.print("    O");
                    }
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println();
        }


    }

    public static void emptyQueueFormat(ArrayList<ArrayList<Customer>> queues) {
        int maxQueueLength = maxQueueLength(queues);

        //print the visual representation of queues
        for (int slot = 0; slot < maxQueueLength; slot++) {
            System.out.print(slot + 1);
            for (int queue = 0; queue < queues.size(); queue++) {

                if (slot < queues.get(queue).size()) {
                    if (queues.get(queue).get(slot) == null) {
                        System.out.print("    X");
                    } else {
                        System.out.print("     ");
                    }
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println();
        }


    }

    public static void addCustomer(Customer customer,ArrayList<ArrayList<Customer>> queues,WaitingQueue waitingQueue) {

        int maxQueueLength = maxQueueLength(queues);

        //add customer to the shortest queue
        for (int slot = 0; slot < maxQueueLength; slot++) {

                for (int queue = 0; queue < queues.size(); queue++) {
                    if (slot < queues.get(queue).size()) {
                        if (queues.get(queue).get(slot) == null) {
                            queues.get(queue).set(slot, customer);
                            System.out.println(customer.getFullName()+" is added to the food queue"+(queue+1)+" slot"+(slot+1));
                            return;
                        } else if (queues.get(queues.size()-1).get(queues.get(queues.size()-1).size() - 1) != null) {
                            waitingQueue.enqueue(customer);
                            return;
                        }
                    }
                }

        }
    }

    public static  int removeCustomer(int queue, int slot, boolean served, int burgerStock,int customerBurger,ArrayList<ArrayList<Customer>> queues,WaitingQueue waitingQueue) {
        //validate the queue number and row number
        if (queue < queues.size()) {
            if (slot < queues.get(queue).size()) {
                //performing null check
                if (queues.get(queue).get(slot) != null) {
                    //removing customer from a specific location
                    if (served) {
                        if (queues.get(queue).get(slot).getNoOfBurgers() < burgerStock) {

                            System.out.println(queues.get(queue).get(slot).getFullName() + " was served from queue" + (queue + 1));
                            customerBurger =queues.get(queue).get(slot).getNoOfBurgers();
                            queues.get(queue).remove(slot);
                            Customer waitingQueueCustomer= waitingQueue.dequeue();
                            queues.get(queue).add(waitingQueueCustomer);
                            if(waitingQueueCustomer!=null){
                                System.out.println(waitingQueueCustomer.getFullName()+" was moved to food queue from  waiting queue ");
                            }
                            burgerStock = burgerStock - customerBurger;
                            if (burgerStock <= 10) {
                                System.out.println("Only" + burgerStock + "burgers are available");
                            }



                        } else {
                            System.out.println("Not enough burgers.\n" + "Only" + burgerStock + "burgers are available.");
                        }

                    } else {
                        System.out.println(queues.get(queue).get(slot).getFullName() + " was removed from the row" + (slot + 1) + " of queue" + (queue + 1));
                        queues.get(queue).remove(slot);
                        Customer waitingQueueCustomer= waitingQueue.dequeue();
                        queues.get(queue).add(waitingQueueCustomer);
                        if(waitingQueue.dequeue()!=null){
                            System.out.println((waitingQueueCustomer.getFullName()+" was moved to food queue from  waiting queue "));
                        }
                    }

                } else {
                    if (served) {
                        System.out.println("Check the queue number");
                    } else {
                        System.out.println("Check the queue number and slot number");
                    }
                }
            } else {
                System.out.print("Invalid slot number");
            }
        } else {
            System.out.print("Invalid queue number");
        }
        return customerBurger;
    }
    public static void sortCustomer(ArrayList<ArrayList<Customer>> array) {
        //counting the customer names
        int count = 0;
        for (int queue = 0; queue < array.size(); queue++) {
            for (int slot = 0; slot < array.get(queue).size(); slot++) {
                if (array.get(queue).get(slot) != null){
                    count++;
                }
            }
        }

        if(count==0){
            System.out.println("All queues are empty.\nPlease add customers to the queue");
        }
        //changing 2D array version to 1D array version
        Customer[] singleArray = new Customer[count];
        int index=0;
        for (int queue = 0; queue < array.size(); queue++) {
            for (int slot = 0; slot < array.get(queue).size(); slot++) {
                if (array.get(queue).get(slot) != null) {
                    singleArray[index] = array.get(queue).get(slot);
                    index++;
                }
            }
        }
        //using bubble sort algorithm to sort array elements
        for (int index1 = 0; index1 < singleArray.length-1; index1++) {
            for (int index2 = 0; index2 < (singleArray.length - index1 - 1); index2++) {

                if (compareString(singleArray[index2].getFullName(),singleArray[index2 + 1].getFullName()) > 0) { //comparing elements in 1D array using compareString method
                    Customer temp = singleArray[index2];
                    singleArray[index2] = singleArray[index2 + 1];
                    singleArray[index2 + 1] = temp;
                }
            }
        }
        //display the sorted elements
        for ( Customer element : singleArray) {
            System.out.println(element.getFullName());
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
                }catch(Exception e){
                    System.out.println("Enter an integer.");
                }
            }

        } else {
            System.out.print("Maximum Burger stock reached");

        }

        return balanceBurgerStock;
    }
    public static void storeData(ArrayList<ArrayList<Customer>>array,Customer[] waitingQueueArray, int balanceBurgerStock, int queue1Income, int queue2Income, int queue3Income) {
        try{
            Customer customer;
            String customerFirstName;
            String customerSecondName;
            int customerBurger;
            //opening or creating file
            FileWriter file=new FileWriter("FoodiesFaveFoodCenter.txt");
            //writing data to the file
            for (int queue = 0; queue < array.size(); queue++) {
                for (int row = 0; row < array.get(queue).size(); row++) {
                    if(array.get(queue).get(row)!=null){
                        customer=array.get(queue).get(row);
                        customerFirstName= customer.getFirstName();
                        customerSecondName=customer.getSecondName();
                        customerBurger=customer.getNoOfBurgers();

                        file.write(customerFirstName+"\n"+customerSecondName+"\n"+customerBurger+"\n\n");
                    } else{
                        file.write("null\n\n");
                    }

                }
            }
            for (int index=0;index<waitingQueueArray.length;index++){
                if(waitingQueueArray[index]!=null){
                    customer=waitingQueueArray[index];
                    customerFirstName= customer.getFirstName();
                    customerSecondName=customer.getSecondName();
                    customerBurger=customer.getNoOfBurgers();

                    file.write(customerFirstName+"\n"+customerSecondName+"\n"+customerBurger+"\n\n");
                }else{
                    file.write("null\n\n");
                }
            }
            file.write(balanceBurgerStock+"\n");
            file.write(queue1Income+"\n"+queue2Income+"\n"+queue3Income);
            file.close();//closing the file
            System.out.println("The current program data is saved in the document file named \"FoodiesFaveFoodCenter.txt\".");
        }catch(IOException e){
            System.out.println("An error occurred.");
        }

    }
    public static void loadStoredData(ArrayList<ArrayList<Customer>> array, WaitingQueue waitingQueue, int[] shopData) {
        try {
            File file = new File("FoodiesFaveFoodCenter.txt");
            boolean fileExist = file.exists();
            if (fileExist) {
                Scanner readFile = new Scanner(file);
                ArrayList<String> dataArray = new ArrayList<>();

                while (readFile.hasNextLine()) {
                    String line = readFile.nextLine();
                    dataArray.add(line);
                }
                readFile.close();

                int slot = 0;
                int index = 0;
                while (index < dataArray.size() - 4) {
                    for (int queue = 0; queue < array.size(); queue++) {
                        for (int row = 0; row < array.get(queue).size(); row++) {
                            if (dataArray.get(index).equals("null")) {
                                if (array.get(queue).get(row) != null) {
                                    waitingQueue.getWaitingQueue()[slot] = null;
                                    slot++;
                                }else{
                                    array.get(queue).set(row, null);
                                }
                                index = index + 2;
                            } else {
                                String firstName = dataArray.get(index);
                                String secondName = dataArray.get(index + 1);
                                int noOfBurger = Integer.parseInt(dataArray.get(index + 2));
                                Customer customer = new Customer(firstName, secondName, noOfBurger);
                                if (array.get(queue).get(row) != null) {
                                    waitingQueue.getWaitingQueue()[slot] = customer;
                                    slot++;
                                }else{
                                    array.get(queue).set(row, customer);
                                }
                                index = index + 4;
                            }
                        }
                    }
                }

                shopData[0] = Integer.parseInt(dataArray.get(dataArray.size() - 4));
                shopData[1] = Integer.parseInt(dataArray.get(dataArray.size() - 3));
                shopData[2] = Integer.parseInt(dataArray.get(dataArray.size() - 2));
                shopData[3] = Integer.parseInt(dataArray.get(dataArray.size() - 1));

                System.out.println("Data loaded successfully from the file named \"FoodiesFaveFoodCenter.txt\".");
            } else {
                System.out.println("The file \"FoodiesFaveFoodCenter.txt\" does not exist.\n" +
                        "Enter 106 to create the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading data.");
        }
    }
    public static void changeCustomerDetails(ArrayList<ArrayList<Customer>> array, WaitingQueue waitingQueue,String queueName){
        Scanner input= new Scanner(System.in);
        if (queueName.equals("main")){
            try{
                //prompt queue position to user
                System.out.print("Enter the queue no(1-3): ");
                int queueNo = input.nextInt() - 1;
                System.out.print("Enter the row no" + "(1-"+ array.get(queueNo).size()+ ")" + ": ");
                int rowNo = input.nextInt() - 1;
                //validate the queue number and row number
                if (queueNo < array.size()) {
                    if (rowNo < array.get(queueNo).size()) {
                        //performing null check
                        if (array.get(queueNo).get(rowNo) != null) {
                            setCustomerData(array.get(queueNo).get(rowNo));
                        } else {
                            System.out.println("Check the queue number and slot number");
                        }
                    } else {
                        System.out.print("Invalid slot number");
                    }
                } else {
                    System.out.print("Invalid queue number");
                }
            } catch (Exception e) {
                System.out.println("Enter integer");
            }
        } else if (queueName.equals("waiting")) {
            Customer[] waitingQueueList = waitingQueue.getWaitingQueue();
            try {
                System.out.print("Enter the customer's waiting queue no : ");
                int waitingQueueNo= input.nextInt();
                if (waitingQueueNo<waitingQueueList.length) {
                    setCustomerData(waitingQueueList[waitingQueueNo]);
                }else {
                    System.out.println("Enter a valid waiting queue slot number");
                }
            } catch (Exception e) {
                System.out.println("Enter an integer");
            }

        }
    }
    public static void setCustomerData(Customer customer) {
        Scanner input= new Scanner(System.in);
        try{
            //prompt customer details
            String tempFirst=customer.getFirstName();
            System.out.print("If you want to change the first name enter 1 :");
            String option1= input.next();
            if (option1.equals("1")) {
                System.out.print("Enter the customer's first name to change: ");
                String firstName = input.next().toLowerCase();
                customer.setFirstName(firstName);
                System.out.println("Customer's First name "+tempFirst+" changed to "+firstName);
            }
            String tempSecond= customer.getSecondName();
            System.out.print("If you want to change the first name enter 2 :");
            String option2= input.next();
            if (option2.equals("2")) {
                System.out.print("Enter the customer's second name to change: ");
                String secondName = input.next().toLowerCase();
                customer.setSecondName(secondName);
                System.out.println("Customer's First name "+tempSecond+" changed to "+secondName);
            }
            int tempBurgerCount= customer.getNoOfBurgers();
            System.out.print("If you want to change the order burger count enter 3 :");
            String option3= input.next();
            if (option3.equals("3")) {
                System.out.print("Enter the number of burgers required to change: ");
                int noOfBurgers = input.nextInt();
                if (noOfBurgers<=50){
                    customer.setNoOfBurgers(noOfBurgers);
                    System.out.println("Customer's order burger count "+tempBurgerCount+" changed to "+noOfBurgers);
                }else{
                    System.out.println("The maximum number of burgers that can be ordered is 50.");
                }
            }

        }catch(Exception e){
            System.out.println("Enter an integer for the number of required burgers.");
        }
    }
}
