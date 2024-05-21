/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.DoctorDAO;
import com.example.model.Doctor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/doctors")

public class DoctorResource {
    
    private static final Logger LOGGER = Logger.getLogger(DoctorResource.class.getName());
    private DoctorDAO doctorDAO = new DoctorDAO();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_All_Doctors() {
          try {
            List<Doctor> doctors = DoctorDAO.getAllDoctors();
            LOGGER.log(Level.INFO, "Successfully collect all the Doctors details");
            return Response.ok().entity(doctors).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to collect all the doctors details", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to collect all the doctors details. Please Re-Enter details again!.").build();
        }
    }

    @GET
    @Path("/{Id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_Doctor_ById(@PathParam("Id") int id) {
        try {
            Doctor doctor = doctorDAO.getDoctorById(id);
            if (doctor != null) {
                return Response.ok(doctor).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Doctor not found").build();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something wrong with getting doctors details by ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error getting doctor").build();
        }
    }

    @POST
    public Response add_Doctor(Doctor doctor) {
        try {
            doctorDAO.addDoctor(doctor);
            return Response.status(Response.Status.CREATED).entity("Successfully added Doctors details.").build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something wrong with adding doctors details", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something wrong with adding doctors details").build();
        }
    }

    @PUT
    @Path("/{doctorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update_Doctor(@PathParam("doctorId") int doctorId, Doctor updatedDoctor) {
        try {
            Doctor existingDoctor = doctorDAO.getDoctorById(doctorId);
            if (existingDoctor != null) {
                updatedDoctor.setID(doctorId);
                doctorDAO.updateDoctor(updatedDoctor);
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Doctor details cannot found").build();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something wrong with updating doctors details", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something wrong with updating doctors details").build();
        }
    }

    @DELETE
    @Path("/{Id}")
    public Response delete_Doctor(@PathParam("Id") int id) {
        try {
            doctorDAO.deleteDoctor(id);
            LOGGER.log(Level.INFO, "Deleted doctor with ID {0} successfully", id);
            return Response.ok().entity("Details of doctor with ID " + id + " Deleted successfully.").build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something wrong with deleting details", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something wrong with deleting details").build();
        }
    }
}

