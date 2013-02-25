package com.schibsted.base64;

import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.handlers.ClientHttpHandler;
import com.googlecode.utterlyidle.modules.RequestScopedModule;
import com.googlecode.utterlyidle.modules.ResourcesModule;
import com.googlecode.yadic.Container;

import static com.googlecode.utterlyidle.annotations.AnnotatedBindings.annotatedClass;

public class Base64Module implements ResourcesModule, RequestScopedModule {

    public static final int TIMEOUT_MS = 10000;

    @Override
    public Container addPerRequestObjects(Container container) throws Exception {
        container.addInstance(ClientHttpHandler.class, new ClientHttpHandler(TIMEOUT_MS));
        return container;
    }

    @Override
    public Resources addResources(Resources resources) throws Exception {
        resources.add(annotatedClass(Base64Resource.class));
        return resources;
    }
}
