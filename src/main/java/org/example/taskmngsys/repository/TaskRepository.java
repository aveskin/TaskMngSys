package org.example.taskmngsys.repository;

import org.example.taskmngsys.entity.Task;
import org.example.taskmngsys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Integer> {

    List<Task> findAllByAuthorId(Integer authorId);

    @Query("SELECT t FROM Task t WHERE t.author.id = :userId AND t.id = :taskId")
    Optional<Task> findByUserIdAndTaskId(@Param("userId") Integer userId, @Param("taskId") Integer taskId);
}
