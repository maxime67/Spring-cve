package com.example.demo.repositories;

import com.example.demo.entities.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeDAO extends JpaRepository<Node, Long> {
    Node save(Node node);
    Node findById(long id);
}
