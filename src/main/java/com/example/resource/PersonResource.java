/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PersonDAO;
import com.example.model.Person;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    private PersonDAO personDAO = new PersonDAO();

    @POST
    public Response add_Person(Person person) {
        personDAO.addPerson(person);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{ID}")
    public Response get_Person(@PathParam("ID") int id) {
        try {
            Person person = personDAO.getPerson(id);
            return Response.ok(person).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{ID}")
    public Response update_Person(@PathParam("ID") int id, Person updatedPerson) {
        try {
            personDAO.updatePerson(id, updatedPerson);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{ID}")
    public Response delete_Person(@PathParam("ID") int id) {
        try {
            personDAO.deletePerson(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}

