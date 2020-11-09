package by.tms.entity;

import by.tms.entity.embeddable.LikeId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "likes")

@NamedQuery(name = "Like.findAll", query = "from Like l where l.id.postId = :postId")
@NamedQuery(name = "Like.deleteByPostId", query = "delete from Like l where l.id.postId = :postId")
@NamedQuery(name = "Like.existById", query = "select count(*) from Like l where l.id = :likeId")

public class Like {

    @EmbeddedId
    private LikeId id;

}
