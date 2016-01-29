package com.kidmobile.csr.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCase1 {
	public interface roleFather {
		int start_age = 30;
		
		void printFatherRole();
	}	
	public interface roleHusband {
		int start_age = 25;
		
		void printHusbandRole();
	}
	public interface roleSon {
		int start_age = 0;
		
		void printSonRole();
	}
	
	public abstract class Animal {
		int count_foot;
		
		void printIam()
		{
			System.out.println("Animal...");
		}
		
		abstract void printFootNum();
	}
	
	public class My extends Animal implements roleFather, roleHusband, roleSon {
		int count_foot = 2;
		
		public My() {
		}

		public My(int count_foot) {
			this.count_foot = count_foot;
		}

		@Override
		public void printFatherRole() {
			System.out.println("Role is = Father");
		}

		@Override
		public void printHusbandRole() {
			System.out.println("Role is = Husband");
		}
		
		@Override
		public void printSonRole() {
			System.out.println("Role is = Son");
		}
		
		@Override
		public void printFootNum()
		{
			System.out.println("Foot num = " + count_foot);
		}
		
		public void printStartAge() {
			roleFather father = (roleFather)this;
			System.out.println("father start_age = " + father.start_age);
			
			roleHusband husband = (roleHusband)this;
			System.out.println("husband start_age = " + husband.start_age);
			
			roleSon son = (roleSon)this;
			System.out.println("son start_age = " + son.start_age);
		}
	}
	
	public class Ari extends My {

		public Ari(int count_foot) {
			super(count_foot);
		}
		
		@Override
		public void printFootNum()
		{
			System.out.println("Ari Foot num = " + count_foot);
		}
	}

	@Test
	public void test() {
		My my_one = new My(2);	
		my_one.count_foot = 2;
		my_one.printStartAge();
		my_one.printFootNum();
		my_one.printIam();
		my_one.printFatherRole();
		my_one.printHusbandRole();
		my_one.printSonRole();
		
		Ari ari_one = new Ari(2);
		ari_one.printStartAge();
		ari_one.printFootNum();
		ari_one.printIam();
		ari_one.printFatherRole();
		ari_one.printHusbandRole();
		ari_one.printSonRole();
		
		My my_two = new My();	
		my_two.printStartAge();
		my_two.printFootNum();
		my_two.printIam();
		my_two.printFatherRole();
		my_two.printHusbandRole();
		my_two.printSonRole();
		
		System.out.println("hello...");
		my_one.printFootNum();
	}

}
