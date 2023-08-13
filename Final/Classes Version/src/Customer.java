public class Customer {
    private String firstName;
    private String secondName;
    private int noOfBurgers;
    public Customer(String firstName,String secondName,int noOfBurgers){
        this.firstName = firstName;
        this.secondName = secondName;
        this.noOfBurgers = noOfBurgers;

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setNoOfBurgers(int noOfBurgers) {
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
