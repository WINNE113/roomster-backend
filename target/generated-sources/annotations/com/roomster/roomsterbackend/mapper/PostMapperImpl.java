package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.entity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-07T22:55:17+0700",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
*/
@Component
@Primary
public class PostMapperImpl extends PostMapperDecorator implements PostMapper {

    @Autowired
    @Qualifier("delegate")
    private PostMapper delegate;

    @Override
    public PostEntity updatePost(PostEntity oldPost, PostEntity newPost)  {
        return delegate.updatePost( oldPost, newPost );
    }
}
