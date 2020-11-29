package com.exam.service;

import com.exam.model.Admin;
import com.exam.model.Student;
import com.exam.model.Teacher;

public interface LoginService {

    public Admin adminLogin(Integer username, String password);

    public Teacher teacherLogin(Integer username, String password);

    public Student studentLogin(Integer username, String password);
}
