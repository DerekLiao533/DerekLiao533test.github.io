package com.example.demo.dao;

import com.example.demo.Student;
import com.example.demo.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDaoImpl implements StudentDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Student getById(Integer studentId) {
        String sql = "Select id,name From student";

        Map<String, Object> map = new HashMap<>();

        if( studentId!=null) {
            sql = "Select id,name From student Where id = :Id ";

            map.put("Id", studentId);
        }

        List<Student> studentList=namedParameterJdbcTemplate.query(sql, map, new StudentRowMapper());

        if( studentList.size()>0 ){
            return studentList.get(0);
        }
        else{
            return null;
        }

    }
}
