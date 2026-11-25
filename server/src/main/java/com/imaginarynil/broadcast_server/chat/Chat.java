package com.imaginarynil.broadcast_server.chat;

import com.imaginarynil.broadcast_server.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String message;
    @Column(updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    User user;
}
