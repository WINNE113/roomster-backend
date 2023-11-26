package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.entity.PostEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-23T22:55:08+0700",
    comments = "version: 1.4.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
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
