package com.pratham.productivity.repository;

import com.pratham.productivity.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}