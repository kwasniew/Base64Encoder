package com.schibsted.status;

import com.googlecode.totallylazy.Uri;
import com.googlecode.utterlyidle.*;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;

public class StatusResource {

    private LastCommit lastCommit;
    private BuildVersion buildVersion;
    private Redirector redirector;

    public StatusResource(LastCommit lastCommit, BuildVersion buildVersion, Redirector redirector) {
        this.lastCommit = lastCommit;
        this.buildVersion = buildVersion;
        this.redirector = redirector;
    }

    @GET
    @Path("")
    public Response landingPage() {
        return redirector.seeOther(Uri.uri("index.html"));
    }

    @GET
    @Path("status/git")
    public Response lastCommit() {
        return ResponseBuilder.response(Status.OK).
                               contentType(MediaType.TEXT_PLAIN).
                               entity(lastCommit.toString()).
                               build();
    }

    @GET
    @Path("status/build")
    public Response buildVersion() {
        return ResponseBuilder.response(Status.OK).
                               contentType(MediaType.TEXT_PLAIN).
                               entity(buildVersion.toString()).
                               build();
    }
}
