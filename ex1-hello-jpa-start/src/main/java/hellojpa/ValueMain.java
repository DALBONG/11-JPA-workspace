package hellojpa;

import jakarta.persistence.Entity;

public class ValueMain {

    public static void main(String[] args) {

        Integer a = 10;
        Integer b = 10;

        Address address1 = new Address("서울", "백스트리트", "05488");
        Address address2 = new Address("서울", "백스트리트", "05488");

        System.out.println("add1 == add2 : " + address1.equals(address2));

    }
}
