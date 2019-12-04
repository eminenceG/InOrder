package model;

/**
 * The data model for Customer table. Customer is information about the customer, including name, address, city, state,
 * country, postal code. The customer also has a customer id that is a numeric gensym.
 */
public class Customer {
    private int customerId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private static String NOT_VALID_ARGUMENT = "The arguments are not valid.";

    /**
     * Initializes a Customer instance.
     *
     * @param customerId the customer ID.
     * @param name       the customer's name.
     * @param address    the customer's address.
     * @param city       the customer's city.
     * @param state      the customer's state.
     * @param country    the customer's country.
     * @param postalCode the customer's postalCode.
     * @throws IllegalArgumentException if the customer ID is not valid.
     */
    public Customer(int customerId, String name, String address, String city, String state, String country,
                    String postalCode) throws IllegalArgumentException {

        if (customerId < 0) {
            throw new IllegalArgumentException(NOT_VALID_ARGUMENT);
        }
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }


    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Customer other = (Customer) obj;
        return other.getCustomerId() == this.getCustomerId() &&
                other.getAddress().equals(this.getAddress()) &&
                other.getCity().equals(this.getCity()) &&
                other.getCountry().equals(this.getCountry()) &&
                other.getName().equals(this.getName()) &&
                other.getPostalCode().equals(this.getPostalCode()) &&
                other.getState().equals(this.getState());
    }

    @Override
    public String toString() {
        return String.format("CustomerId: %d, name: %s, address: %s, city: %s, state: %s, country: %s", customerId,
                name, address, city, state, country);
    }
}
