# POC - Groovy Reload with Classloader Isolation

**AKA**: A simple Spring Boot service with an exposed end-point allowing execution of arbitrary Groovy scripts. Groovy scripts are executed within their own cached (and isolated) ClassLoader.

#### Why

An excuse to play around with Spring Boot and do a _very quick_ comparison of it to common Dropwizard capabilities.

- Overriding ObjectMapper (_ie. enable custom serialization features_)
- URL Generation (_ie. UriComponentsBuilder_)
- Configuration Validation (_ie. JSR-303_)
- Authentication (_ie. Spring Security Basic Auth_)
- Task Scheduling

#### Running
    [ajordens@Phaeton]$ gradle wrapper
    [ajordens@Phaeton]$ ./gradlew bootRun
    [ajordens@Phaeton]$ curl http://localhost:8080/api/v1/dynamic
    {
      "results" : [ {
        "name" : "TestScript",
        "lastUpdated" : "2014-08-18T05:39:48.000+0000",
        "actions" : {
          "execute" : "http://localhost:8080/api/v1/dynamic/TestScript"
        }
      } ]
    }
    
    [ajordens@Phaeton]$ curl -u user:password http://localhost:8080/api/v1/dynamic/TestScript
    {
      "results" : {
        "output" : "TestScript executed!",
        "hashCode" : 1286548743
      }
    }
    
    