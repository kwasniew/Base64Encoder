package com.schibsted.base64;

import com.googlecode.utterlyidle.RequestBuilder;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.ResponseBuilder;
import com.googlecode.utterlyidle.Status;
import com.googlecode.utterlyidle.annotations.*;
import com.googlecode.utterlyidle.handlers.ClientHttpHandler;
import org.apache.commons.codec.binary.Base64;

import static com.googlecode.utterlyidle.HttpHeaders.CACHE_CONTROL;
import static java.lang.String.format;

public class Base64Resource {

    public static final int CACHE_TIME = 60 * 60 * 12;   // 12h
    private ClientHttpHandler clientHttpHandler;

    public Base64Resource(ClientHttpHandler clientHttpHandler) {
        this.clientHttpHandler = clientHttpHandler;
    }

    @GET
    @Path("encoding")
    public Response encode(@QueryParam("source") String source) throws Exception {
        Response response = clientHttpHandler.handle(RequestBuilder.get(source).accepting("*/*").build());
        byte[] imageAsBytes = response.entity().asBytes();
        byte[] base64 = Base64.encodeBase64(imageAsBytes);
        if(!response.status().equals(Status.OK)) {
            return ResponseBuilder.modify(response).header("Access-Control-Allow-Origin", "*").build();
        }
        String prefix = "data:"+response.headers().getValue("Content-Type")+";charset=UTF-8;base64,";
        return ResponseBuilder.response(Status.OK).
                header("Access-Control-Allow-Origin", "*").
                header(CACHE_CONTROL, format("public, max-age=%s", CACHE_TIME)).
                entity(prefix + new String(base64)).build();
    }

}
