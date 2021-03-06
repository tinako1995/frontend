package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.model.Student;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    /**
     * 查詢所有學生
     * @param page
     * @return List<Student>
     */
    @Select("select * from student")
    IPage<Student> findAll(Page page);

    @Select("select * from student where studentId = #{studentId}")
    Student findById(Integer studentId);

    @Delete("delete from student where studentId = #{studentId}")
    int deleteById(Integer studentId);

    /**
     *更新學生資訊
     * @param student 
     * @return 
     */
    @Update("update student set studentName = #{studentName},grade = #{grade},major = #{major},clazz = #{clazz}," +
            "institute = #{institute},tel = #{tel},email = #{email},pwd = #{pwd},cardId = #{cardId},sex = #{sex},role = #{role} " +
            "where studentId = #{studentId}")
    int update(Student student);

    /**
     * 更新密碼
     * @param student
     * @return 
     */
    @Update("update student set pwd = #{pwd} where studentId = #{studentId}")
    int updatePwd(Student student);


    @Options(useGeneratedKeys = true,keyProperty = "studentId")
    @Insert("insert into student(studentName,grade,major,clazz,institute,tel,email,pwd,cardId,sex,role) values " +
            "(#{studentName},#{grade},#{major},#{clazz},#{institute},#{tel},#{email},#{pwd},#{cardId},#{sex},#{role})")
    int add(Student student);
}
