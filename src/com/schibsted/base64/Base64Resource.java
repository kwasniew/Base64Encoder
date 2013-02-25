package com.schibsted.base64;

import com.googlecode.utterlyidle.RequestBuilder;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.ResponseBuilder;
import com.googlecode.utterlyidle.Status;
import com.googlecode.utterlyidle.annotations.FormParam;
import com.googlecode.utterlyidle.annotations.POST;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.handlers.ClientHttpHandler;
import org.apache.commons.codec.binary.Base64;

public class Base64Resource {

    private ClientHttpHandler clientHttpHandler;

    public Base64Resource(ClientHttpHandler clientHttpHandler) {
        this.clientHttpHandler = clientHttpHandler;
    }

    @POST
    @Path("encoding")
    public Response encode(@FormParam("source") String source) throws Exception {
        Response response = clientHttpHandler.handle(RequestBuilder.get(source).build());
        byte[] imageAsBytes = response.entity().asBytes();
        byte[] base64 = Base64.encodeBase64(imageAsBytes);
        return ResponseBuilder.response(Status.OK).header("Access-Control-Allow-Origin", "*").entity(new String(base64)).build();
    }
}
