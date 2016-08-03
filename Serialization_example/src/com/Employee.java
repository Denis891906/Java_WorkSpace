package com;

import java.io.Serializable;

public class Employee implements Serializable{
	String lName;
	String fName;
	// allow to hide value for variable salary
	transient int salary;

}
