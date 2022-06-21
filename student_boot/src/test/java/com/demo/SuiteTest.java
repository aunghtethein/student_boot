package com.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	
	TestUserController.class,
	TestUserService.class,
	TestStudentController.class,
	TestStudentService.class,
	TestClassController.class,
	TestClassService.class
})
public class SuiteTest {

}
