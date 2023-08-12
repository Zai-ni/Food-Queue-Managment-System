public class Customer {
    private String firstName;
    private String secondName;
    private int noOfBurgers;
    public Customer(String firstName,String secondName,int noOfBurgers){
        this.firstName = firstName;
        this.secondName = secondName;
        this.noOfBurgers = noOfBurgers;

    }

    public String getFirstName(){
        return firstName;
    }
    public String getSecondName(){
        return secondName;
    }
    public String getFullName(){
        return firstName +" "+ secondName;
    }
    public int getNoOfBurgers() {
        return noOfBurgers;
    }
}
