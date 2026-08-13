package com.example.services;

import com.example.Security.CurrentUser;
import com.example.dto.tasks.TaskRequest;
import com.example.dto.tasks.TaskResponse;
import com.example.models.Task;
import com.example.repositories.TaskRepository;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    //    private final UserRepository userRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        var owner = CurrentUser.get();
        var task = new Task(taskRequest.content(), taskRequest.done(), owner);
        Task saved = taskRepository.save(task);
        return TaskResponse.from(saved);
    }

    @Transactional
    public List<TaskResponse> findAll() {
        return taskRepository
            .findByOwnerId(getCurrentUserId())
            .stream()
            .map(TaskResponse::from)
            .toList();
    }

    @Transactional
    public TaskResponse findById(Long id) {
        return TaskResponse.from(findOwned(id));
    }

    @Transactional
    public TaskResponse update(Long id, TaskRequest taskRequest) {
        var task = findOwned(id);
        task.setContent(taskRequest.content());
        task.setDone(taskRequest.done());
        return TaskResponse.from(task);
    }

    @Transactional
    public void delete(Long id) {
        var task = findOwned(id);
        taskRepository.delete(task);
    }

    private Long getCurrentUserId() {
        return CurrentUser.get().getId();
    }

    private Task findOwned(Long id) {
        return taskRepository
            .findByIdAndOwnerId(id, getCurrentUserId())
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
                )
            );
    }
}
