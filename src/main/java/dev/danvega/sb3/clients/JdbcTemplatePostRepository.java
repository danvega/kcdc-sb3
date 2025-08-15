package dev.danvega.sb3.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplatePostRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcTemplatePostRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> findAll() {
        return jdbcTemplate.query(
                "SELECT id, user_id, title, body FROM post",
                (rs, rowNum) -> new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("body")
                )
        );
    }

    public Optional<Post> findById(Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, user_id, title, body FROM post WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> Optional.of(new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("body")
                ))
        );
    }

    public void create(Post post) {
        int update = jdbcTemplate.update(
                "INSERT INTO Post(id,user_id,title,body) VALUES(?,?,?,?)",
                post.id(), post.userId(), post.title(), post.body()
        );
        log.info("Inserted {} rows", update);
    }

    public void update(Post post, Integer id) {
        int update = jdbcTemplate.update(
                "UPDATE Post SET user_id = ?, title = ?, body = ? WHERE id = ?",
                post.userId(), post.title(), post.body(), id
        );
        log.info("Updated {} rows", update);
    }

    public void delete(Integer id) {
        int update = jdbcTemplate.update("DELETE FROM Post WHERE id = ?", id);
        log.info("Deleted {} rows", update);
    }

}
