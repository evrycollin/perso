/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstart.hibernate4.web;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.as.quickstart.hibernate4.model.Member;
import org.jboss.as.quickstart.hibernate4.service.MemberService;

@Path("/members")
public class MemberResource {


    @Inject
    private MemberService memberRegistration;

    @GET
    @Produces({ "application/json" })
    public Object members() {
	return memberRegistration.findAllOrderedByName();

    }

    @GET
    @Path("{id}")
    @Produces({ "application/json" })
    public Object getById(@PathParam("id") Long id) {
	return memberRegistration.findById(id);
    }

    @DELETE
    @Path("{id}")
    @Produces({ "application/json" })
    public boolean delete(@PathParam("id") Long id) throws Exception {
	memberRegistration.delete(id);
	return true;
    }

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Member register(Member member) throws Exception {
	memberRegistration.register(member);
	return member;
    }

    @PUT
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public boolean save(Member member) throws Exception {
	memberRegistration.save(member);
	return true;
    }

}
