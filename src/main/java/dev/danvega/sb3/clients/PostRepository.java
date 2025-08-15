package dev.danvega.sb3.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {

    private static final Logger log = LoggerFactory.getLogger(PostRepository.class);
    private final JdbcClient jdbcClient;

    public PostRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Post> findAll() {
        return jdbcClient.sql("SELECT id, user_id, title, body FROM post")
                .query(Post.class)
                .list();
    }

    public Optional<Post> findById(Integer id) {
        return jdbcClient.sql("SELECT id, user_id, title, body FROM post WHERE id = ?")
                .param(id)
                .query(Post.class)
                .optional();
    }

    public void create(Post post) {
        int update = jdbcClient.sql("INSERT INTO Post(id,user_id,title,body) VALUES(?,?,?,?)")
                .params(post.id(), post.userId(), post.title(), post.body())
                .update();
        log.info("Inserted {} rows", update);
    }

    public void update(Post post, Integer id) {
        int update = jdbcClient.sql("UPDATE Post SET user_id = ?, title = ?, body = ? WHERE id = ?")
                .params(post.userId(), post.title(), post.body(), id)
                .update();
        log.info("Updated {} rows", update);
    }

    public void delete(Integer id) {
        int update = jdbcClient.sql("DELETE FROM Post WHERE id = ?").param(id).update();
        log.info("Deleted {} rows", update);
    }

}
