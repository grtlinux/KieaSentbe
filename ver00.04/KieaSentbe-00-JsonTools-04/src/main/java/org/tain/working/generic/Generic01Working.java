package org.tain.working.generic;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Generic01Working {

	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			Box<String> box1 = new Box<>();
			box1.set("Hello");
			System.out.println(">>>>> box1: " + box1.get());
			
			Box<Integer> box2 = new Box<>();
			box2.set(12345);
			System.out.println(">>>>> box2: " + box2.get());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void test02() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			Product<Tv, String> product1 = new Product<>();
			product1.setKind(new Tv());
			product1.setModel("Smart TV");
			Tv tv = product1.getKind();
			String tvModel = product1.getModel();
			System.out.printf(">>>>> product1: %s %s%n", tv.getName(), tvModel);
			
			Product<Car, String> product2 = new Product<>();
			product2.setKind(new Car());
			product2.setModel("Sonata");
			Car car = product2.getKind();
			String carModel = product2.getModel();
			System.out.printf(">>>>> product2: %s %s%n", car.getName(), carModel);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void test03() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			Box<Integer> box1 = Util.<Integer>boxing(1234);
			int value1 = box1.get();
			System.out.println(">>>>> value1: " + value1);
			
			Box<String> box2 = Util.boxing("Hello, world!!!");
			String value2 = box2.get();
			System.out.println(">>>>> value2: " + value2);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void test04() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			Pair<Integer,String> p1 = new Pair<>(1, "Apple");
			Pair<Integer,String> p2 = new Pair<>(1, "Apple");
			boolean result1 = Util.<Integer,String>compare(p1, p2);  // success
			//boolean result1 = Util.compare(p1, p2);  // success
			if (result1) {
				System.out.println(">>>>> 1. Equals method is TRUE");
			} else {
				System.out.println(">>>>> 1. Equals method is FALSE");
			}
			
			Pair<String,String> p3 = new Pair<>("user1", "Hello");
			Pair<String,String> p4 = new Pair<>("user2", "Hello");
			//boolean result2 = Util.<String,String>compare(p3, p4);  // success
			boolean result2 = Util.compare(p3, p4);  // success
			if (result2) {
				System.out.println(">>>>> 2. Equals method is TRUE");
			} else {
				System.out.println(">>>>> 2. Equals method is FALSE");
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void test05() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			//String result1 = Util.<String>compare("a", "b");  // incorrect number type error
			
			int result2 = Util.<Integer>compare(10, 20);
			System.out.println(">>>>> result2: " + result2);
			
			//int result3 = Util.<Number>compare(4.5, 3);  // success
			int result3 = Util.compare(4.5, 3);  // success
			System.out.println(">>>>> result3: " + result3);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * Person
	 *     Student
	 *         HighStudent
	 *     Worker
	 */
	public void test06() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			Course<Person> personCourse = new Course<>("일반인과정", 5);
			personCourse.add(new Person("일반인"));
			personCourse.add(new Worker("직장인"));
			personCourse.add(new Student("학생"));
			personCourse.add(new HighStudent("고등학생"));
			
			Course<Worker> workerCourse = new Course<>("직장인과정", 5);
			workerCourse.add(new Worker("직장인"));
			
			Course<Student> studentCourse = new Course<>("학생과정", 5);
			studentCourse.add(new Student("학생"));
			studentCourse.add(new HighStudent("고등학생"));
			
			Course<HighStudent> highStudentCourse = new Course<>("고등학생과정", 5);
			highStudentCourse.add(new HighStudent("고등학생"));
			
			WildCard.registerCourse(personCourse);
			WildCard.registerCourse(workerCourse);
			WildCard.registerCourse(studentCourse);
			WildCard.registerCourse(highStudentCourse);
			System.out.println();
			
			//WildCard.registerCourseStudent(personCourse);
			//WildCard.registerCourseStudent(workerCourse);
			WildCard.registerCourseStudent(studentCourse);
			WildCard.registerCourseStudent(highStudentCourse);
			System.out.println();
			
			WildCard.registerCourseWorker(personCourse);
			WildCard.registerCourseWorker(workerCourse);
			//WildCard.registerCourseWorker(studentCourse);
			//WildCard.registerCourseWorker(highStudentCourse);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void test07() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			Child<Tv, String, String> product = new Child<>();
			product.setKind(new Tv());
			product.setModel("Smart TV");
			product.setCompany("Samsung");
			System.out.println(">>>>> product: " + product);
			
			Storage<Tv> storage = new StorageImpl<>(10);
			storage.add(new Tv(), 0);
			System.out.println(">>>>> storage.tv: " + storage.get(0));
		}
	}
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////

class Box<T> {
	private T t;
	public void set(T t) { this.t = t; }
	public T get() { return this.t; }
}

class Product<T, M> {
	private T kind;
	private M model;
	public void setKind(T kind) { this.kind = kind; }
	public void setModel(M model) { this.model = model; }
	public T getKind() { return this.kind; }
	public M getModel() { return this.model; }
}

class Car {
	private String name = "CAR";
	public String getName() { return this.name; }
	public String toString() { return this.name; }
}

class Tv {
	private String name = "TV";
	public String getName() { return this.name; }
	public String toString() { return this.name; }
}

class Util {
	public static <T> Box<T> boxing(T t) {
		Box<T> box = new Box<>();
		box.set(t);
		return box;
	}
	
	public static <K, V> boolean compare(Pair<K,V> p1, Pair<K,V> p2) {
		boolean keyCompare = p1.getKey().equals(p2.getKey());
		boolean valueCompare = p1.getValue().equals(p2.getValue());
		return keyCompare && valueCompare;
	}
	
	public static <T extends Number> int compare(T t1, T t2) {
		double v1 = t1.doubleValue();
		double v2 = t2.doubleValue();
		return Double.compare(v1, v2);
	}
}

class Pair<K, V> {
	private K key;
	private V value;
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	public void setKey(K key) { this.key = key; }
	public void setValue(V value) { this.value = value; }
	public K getKey() { return this.key; }
	public V getValue() { return this.value; }
}

///////////////////////////////////////////////////////////////////////////////

class WildCard {
	public static void registerCourse(Course<?> course) {
		System.out.println(course.getName() + " - " + Arrays.toString(course.getStudents()));
	}
	public static void registerCourseStudent(Course<? extends Student> course) {   // HighStudent -> Student
		System.out.println(course.getName() + " - " + Arrays.toString(course.getStudents()));
	}
	public static void registerCourseWorker(Course<? super Worker> course) {   // Worker -> Person
		System.out.println(course.getName() + " - " + Arrays.toString(course.getStudents()));
	}
}

class Course<T> {
	private String name;
	private T[] students;
	
	@SuppressWarnings("unchecked")
	public Course(String name, int capacity) {
		this.name = name;
		this.students = (T[]) (new Object[capacity]);
	}
	public String getName() { return this.name; }
	public T[] getStudents() { return this.students; }
	public void add(T t) {
		for (int i=0; i < students.length; i++) {
			if (students[i] == null) {
				students[i] = t;
				break;
			}
		}
	}
}

class Person {
	private String name;
	public Person(String name) { this.name = name; }
	public String toString() {
		return this.name;
	}
}

class Worker extends Person {
	public Worker(String name) { super(name); }
	public String toString() {
		return super.toString();
	}
}

class Student extends Person {
	public Student(String name) { super(name); }
	public String toString() {
		return super.toString();
	}
}

class HighStudent extends Student {
	public HighStudent(String name) { super(name); }
	public String toString() {
		return super.toString();
	}
}

///////////////////////////////////////////////////////////////////////////////

class Parent<T, M> {
	private T kind;
	private M model;
	public void setKind(T kind) { this.kind = kind; }
	public void setModel(M model) { this.model = model; }
	public T getKind() { return this.kind; }
	public M getModel() { return this.model; }
	public String toString() {
		return String.format("Parent[%s, %s]", this.kind, this.model);
	}
}

class Child<T, M, C> extends Parent<T, M> {
	private C company;
	public void setCompany(C company) { this.company = company; }
	public C getCompany() { return this.company; }
	public String toString() {
		return String.format("Child[%s, %s]", super.toString(), this.company);
	}
}

interface Storage<T> {
	public void add(T item, int index);
	public T get(int index);
}

class StorageImpl<T> implements Storage<T> {
	private T[] array;
	
	@SuppressWarnings("unchecked")
	public StorageImpl(int capacity) {
		this.array = (T[]) (new Object[capacity]);
	}
	@Override
	public void add(T item, int index) { array[index] = item; }
	@Override
	public T get(int index) { return array[index]; }
}