package ua.patterns.gof.creational;

//Prototype through constructors

//class Address{
//    public String streetAddress, city, country;
//
//    public Address(String streetAddress, String city, String country)
//    {
//        this.streetAddress = streetAddress;
//        this.city = city;
//        this.country = country;
//    }
//
//    public Address(Address other)
//    {
//        this(other.streetAddress, other.city, other.country);
//    }
//
//    @Override
//    public String toString()
//    {
//        return "Address{" +
//                "streetAddress='" + streetAddress + '\'' +
//                ", city='" + city + '\'' +
//                ", country='" + country + '\'' +
//                '}';
//    }
//}
//
//class Employee{
//    public String name;
//    public Address address;
//
//    public Employee(String name, Address address)
//    {
//        this.name = name;
//        this.address = address;
//    }
//
//    public Employee(Employee other)
//    {
//        name = other.name;
//        address = new Address(other.address);
//    }
//
//    @Override
//    public String toString()
//    {
//        return "Employee{" +
//                "name='" + name + '\'' +
//                ", address=" + address +
//                '}';
//    }
//}

//Prototype through serialization

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

class Foo implements Serializable {
    public int staff;
    public String smth;

    public Foo(int staff, String smth) {
        this.staff = staff;
        this.smth = smth;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "staff=" + staff +
                ", smth=" + smth +
                '}';
    }
}

class Prototype {
    public static void main(String[] args) {
//        Employee john = new Employee("John", new Address("123 London Road", "London", "UK"));
//        Employee chris = new Employee(john); // copy of object
//        chris.name = "Chris";
//        chris.address = new Address("123 Kovtun str", "Kharkiv", "UA");
//        System.out.println(john);
//        System.out.println(chris);

        Foo foo = new Foo(23, "Number");
        Foo foo2 = SerializationUtils.roundtrip(foo);

        foo2.staff = 32;
        foo2.smth = "Literal";
        System.out.println(foo);
        System.out.println(foo2);
    }
}