package com.littlesquare.services.poc.resources

import com.littlesquare.services.poc.ApplicationConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder

import java.util.concurrent.ConcurrentHashMap

/**
 * @author Adam Jordens (adam@jordens.org)
 */
@RestController
@RequestMapping(value = "/api/v1/dynamic")
public class DynamicScriptResource {
    private final File scriptsDirectory

    // TODO-AJ potential for an LRU cache (eviction should free up perm gem?)
    private ConcurrentHashMap<String, GroovyClassLoader> cachedClassLoaders = [:]

    @Autowired
    DynamicScriptResource(ApplicationConfiguration configuration) {
        this.scriptsDirectory = configuration.scriptsDirectory
    }

    @Secured(["ROLE_USER"])
    @RequestMapping(value = "/{script}", method = RequestMethod.GET)
    Map execute(@PathVariable("script") String scriptName) {
        def originalClassloader = Thread.currentThread().getContextClassLoader()
        try {
            def groovyClassLoader = new GroovyClassLoader()
            def existingClassLoader = cachedClassLoaders.putIfAbsent("${scriptName}.groovy", groovyClassLoader)
            groovyClassLoader = existingClassLoader ?: groovyClassLoader

            Thread.currentThread().setContextClassLoader(groovyClassLoader)
            Class groovyClass = groovyClassLoader.parseClass(new File(scriptsDirectory, "${scriptName}.groovy"))

            return [
                    results: [
                            output  : groovyClass.newInstance().execute(),
                            hashCode: groovyClass.hashCode()
                    ]
            ]
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassloader)
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    Map availableScripts() {
        return [
                results: scriptsDirectory.listFiles().collect { File file ->
                    def scriptName = file.name - ".groovy"
                    def executeAction = MvcUriComponentsBuilder
                            .fromMethodName(this.class, "execute", scriptName)
                            .buildAndExpand()
                            .toUriString()

                    return [
                            name       : scriptName,
                            lastUpdated: new Date(file.lastModified()),
                            actions    : [
                                    execute: executeAction
                            ]
                    ]
                }
        ]
    }
}
