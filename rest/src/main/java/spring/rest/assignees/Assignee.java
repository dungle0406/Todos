package spring.rest.assignees;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import spring.rest.todos.Todo;
import spring.rest.todos.Todo_;

import java.util.List;

@Entity
@Table(name = "assignees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Assignee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = Todo_.ASSIGNEE, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Todo> todos;
}
