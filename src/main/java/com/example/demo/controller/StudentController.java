package com.example.demo.controller;

import com.example.demo.Student;
import com.example.demo.StudentRowMapper;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private StudentService studentService;

    @PostMapping("/students")
    public String insert(@RequestBody Student student){
        String sql = "Insert Into student(name) Values (:studentName)";

        Map<String, Object> map = new HashMap<>();
        map.put("studentName", student.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map), keyHolder);

        int id = keyHolder.getKey().intValue();

        System.out.println("自動生成的id:"+id);

        return "執行insert";
    }

    @PostMapping("/students/batch")
    public String insertList(@RequestBody List<Student> studentList){
        String sql = "Insert Into student(name) Values (:studentName)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[studentList.size()];

        for ( int i=0 ; i<studentList.size() ; i++){
            Student student = studentList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("studentName",student.getName());
        }

        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);

        return "批次執行insert";
    }

    @DeleteMapping("/students/{studentId}")
    public String delete(@PathVariable Integer studentId){
        String sql = "Delete From student Where id = :studentId";

        Map<String, Object> map = new HashMap<>();
        map.put("studentId", studentId);

        namedParameterJdbcTemplate.update(sql,map);

        return "執行delete";
    }

    @GetMapping("/students/{studentId}")
    public Student select( @PathVariable(name="studentId") Integer Id){
        return studentService.getById(Id);
    }
}
