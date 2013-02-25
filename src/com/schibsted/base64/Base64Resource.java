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
        Response response = clientHttpHandler.handle(RequestBuilder.get(source).accepting("*/*").build());
        byte[] imageAsBytes = response.entity().asBytes();
        byte[] base64 = Base64.encodeBase64(imageAsBytes);
        if(!response.status().equals(Status.OK)) {
            return ResponseBuilder.modify(response).header("Access-Control-Allow-Origin", "*").build();
        }
        String prefix = "data:"+response.headers().getValue("Content-Type")+";charset=UTF-8;base64,";
        return ResponseBuilder.response(Status.OK).header("Access-Control-Allow-Origin", "*").entity(prefix + new String(base64)).build();
    }

    @POST
    @Path("asyncEncoding")
    public Response encodeAsynchronously(@FormParam("source") String source) throws Exception {
        Response response = clientHttpHandler.handle(RequestBuilder.get(source).build());
        byte[] imageAsBytes = response.entity().asBytes();
        byte[] base64 = Base64.encodeBase64(imageAsBytes);

        // data:text/csv;charset=UTF-8,
        String prefix = "data:"+response.headers().getValue("Content-Type")+";charset=UTF-8,";

        return ResponseBuilder.response(Status.OK).
                header("Access-Control-Allow-Origin", "*").
                entity(prefix + new String(base64)).build();
    }
}
