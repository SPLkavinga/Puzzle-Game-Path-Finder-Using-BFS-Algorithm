/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.MedicalRecordDAO;
import com.example.model.Appointment;
import com.example.model.MedicalRecord;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/medical-records")

public class MedicalRecordResource {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordResource.class);
    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicalRecord> get_All_Medical_Records() {
        return medicalRecordDAO.getAllMedicalRecords();
    }
    
    
    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicalRecord> get_Medical_Record(@PathParam("patientId") int id) {
            return medicalRecordDAO.getMedicalRecords(id);
         
    }
    
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add_Medical_Record(MedicalRecord medicalRecord) {
        try {
            medicalRecordDAO.addMedicalRecord(medicalRecord);
            logger.info("Medical record added successfully: " + medicalRecord.getId());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
           
            throw new WebApplicationException("Failed to add medical record. Please try again later.", ex);
        }
    }
    
    
    @DELETE
    @Path("/{ID}")
    public void delete_Student(@PathParam("ID") int id) {
        try {
            medicalRecordDAO.deleteMedicalRecord(id);
        } catch (Exception ex) {
            logger.error("Something Wrongeith deleting record with ID {}: {}", id, ex.getMessage());
            throw new WebApplicationException("Record deleting is failde with ID " + id + ". Try again.", ex);
        }
    }

 
}

