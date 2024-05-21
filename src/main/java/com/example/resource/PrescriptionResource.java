/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PrescriptionDAO;
import com.example.model.Prescription;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/prescriptions")

public class PrescriptionResource {

    private static final Logger logger = Logger.getLogger(PrescriptionResource.class.getName());
    
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prescription> getAllPrescriptions() {
        return prescriptionDAO.getAllPrescriptions();
    }
    
    
    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_Prescription(@PathParam("patientId") int id) {
        try {
            Prescription prescription = prescriptionDAO.getPrescription(id);
            if (prescription != null) {
                logger.log(Level.INFO, "Load All the data Successfully");
                return Response.ok().entity(prescription).build();
            } else {
                logger.log(Level.INFO, "Prescription is not found");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Prescription is not found").build();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Prescription is not found. Try again", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch prescription details" ).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add_Prescription(Prescription prescription) {
        try {
            prescriptionDAO.addPrescription(prescription);
            logger.log(Level.INFO, "Successfully Added details of prescription ");
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error, Try again!", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong. Please Re-Enter details again!.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete_Student(@PathParam("id") int id) {
        try {
            prescriptionDAO.deletePrescription(id);
            logger.log(Level.INFO, "Successfully delete the  details of prescription");
            return Response.ok().build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "prescription cannot Found", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong. Please Re-Enter details again! ").build();
        }
    }
}

