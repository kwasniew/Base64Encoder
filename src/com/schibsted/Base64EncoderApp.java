package com.schibsted;

import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.RestApplication;
import com.googlecode.utterlyidle.ServerConfiguration;
import com.googlecode.utterlyidle.httpserver.RestServer;
import com.googlecode.utterlyidle.modules.Module;
import com.schibsted.base64.Base64Module;
import com.schibsted.status.StatusModule;
import java.net.URL;

import static com.googlecode.totallylazy.URLs.packageUrl;
import static com.googlecode.utterlyidle.dsl.DslBindings.bindings;
import static com.googlecode.utterlyidle.dsl.StaticBindingBuilder.in;
import static com.googlecode.utterlyidle.modules.Modules.bindingsModule;

public class Base64EncoderApp extends RestApplication {

    public static final int DEFAULT_PORT = 8181;

    public Base64EncoderApp(BasePath basePath) {
        super(basePath);
        add(new StatusModule());
        add(new Base64Module());

        add(staticFilesModule(Base64EncoderApp.class, "static/", ""));
    }

    private Module staticFilesModule(Class classpathRelativeTo, String localDir, String urlPath) {
        URL url = packageUrl(classpathRelativeTo);
        String newurl = url.toString() + localDir;
        try{
            System.out.println(newurl);
            url = new URL(newurl);
        }catch(Exception e){
            //eat
        }
        return bindingsModule(bindings(in(url).path(urlPath)));
    }

    public static void main(String[] args) throws Exception {
        int port = readPort(args);

        new RestServer(new Base64EncoderApp(BasePath.basePath("")),
                       ServerConfiguration.defaultConfiguration().port(port).maxThreadNumber(10));
    }

    private static int readPort(String[] args) {
        int port = DEFAULT_PORT;
        if(args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        return port;
    }
}
