package spring.rest.todos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import spring.rest.assignees.Assignee;

@Entity
@Table(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "done")
    private Boolean done;

    @Column(name = "urgent")
    private Boolean urgent;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @ManyToOne
    @JoinColumn(name = "assignee_id", insertable = false, updatable = false)
    private Assignee assignee;
}
