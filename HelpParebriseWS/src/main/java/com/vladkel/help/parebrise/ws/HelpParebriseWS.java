package com.vladkel.help.parebrise.ws;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vladkel.help.parebrise.ws.xml.service.InterventionService;

public class HelpParebriseWS extends Application {
	
	private static final Logger log = LoggerFactory.getLogger(HelpParebriseWS.class);

	private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();
 
    public HelpParebriseWS() {
        // ADD YOUR RESTFUL RESOURCES HERE
        this.singletons.add(new InterventionService());
    }
 
    public Set<Class<?>> getClasses()
    {
        return this.empty;
    }
 
    public Set<Object> getSingletons()
    {
        return this.singletons;
    }
}
