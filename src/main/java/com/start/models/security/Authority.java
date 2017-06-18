package com.start.models.security;

import com.start.models.*;
import java.util.List;


public class Authority {

    
    private Long id;
    private AuthorityName name;

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    
}