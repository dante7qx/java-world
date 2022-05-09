package com.dante.xml.vo;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="person")
public class Person {

    private String name;
    private Integer age;
    private String sex;
    private String telephone;

    @JacksonXmlElementWrapper(localName="friends")
    @JacksonXmlProperty(localName="string")
    private List<String> friends;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }
    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }
    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }
    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }
    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }
    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    /**
     * @return the friends
     */
    public List<String> getFriends() {
        return friends;
    }
    /**
     * @param friends the friends to set
     */
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
    /**
     * @param name
     * @param age
     * @param sex
     * @param telephone
     * @param friends
     */
    public Person(String name, Integer age, String sex, String telephone, List<String> friends) {
        super();
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.telephone = telephone;
        this.friends = friends;
    }
    /**
     * 
     */
    public Person() {
        super();
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", sex=" + sex + ", telephone=" + telephone + ", friends="
                + Arrays.toString(friends.toArray()) + "]";
    }


}
