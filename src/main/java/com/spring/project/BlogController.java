package com.spring.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BlogController {

    @Autowired
    BlogRespository blogRespository;

    @GetMapping("/blog")
    public List<Blog> index(){
        return blogRespository.findAll();
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<Blog> show(@PathVariable String id){
        int blogId = Integer.parseInt(id);
        return new ResponseEntity<>(blogRespository.findOne(blogId),HttpStatus.OK);
    }

    @PostMapping("/blog/search")
    public ResponseEntity<List<Blog>> search(@RequestBody Map<String, String> body){
        String searchText = body.get("text");
        return new ResponseEntity<>(blogRespository.findByTitleContainingOrContentContaining(searchText,searchText),HttpStatus.OK);
    }

    @PostMapping("/blog")
    public ResponseEntity<Blog> create(@RequestBody Map<String, String> body){
        if(body.containsKey("title") && body.containsKey("content")) {
            String title = body.get("title");
            String content = body.get("content");
            return new ResponseEntity<>(blogRespository.save(new Blog(title, content)), HttpStatus.OK);
        } else {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/blog/{id}")
    public Blog update(@PathVariable String id, @RequestBody Map<String, String> body){
        int blogId = Integer.parseInt(id);

        //getting blog
        Blog blog = blogRespository.findOne(blogId);
        blog.setTitle(body.get("title"));
        blog.setContent(body.get("content"));
        return blogRespository.save(blog);
    }

    @DeleteMapping("/blog/{id}")
    public boolean delete(@PathVariable String id){
        int blogId = Integer.parseInt(id);
        blogRespository.delete(blogId);
        return true;
    }


}
