# simple-todo
RestApi built with Java. MongoDB and Spring Boot

#### Todo Application

This is a simple todo application built with Java , SpringBoot and MongoDb.

---

### Endpoints

- GET /todos
> http://localhost:6789/todos

- POST /todos
```json
{
    "title": "Todo 1",
    "description": "Todo 1 description",
    "completed": false
}
```

##### Add validation dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Then add your configuration

```java
package com.example.simple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(){
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }
}

```

