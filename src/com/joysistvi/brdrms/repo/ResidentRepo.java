/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.repo;

import com.joysistvi.brdrms.model.Resident;
import java.util.List;

/**
 *
 * @author Hacutina
 */
public interface ResidentRepo {

    public abstract List<Resident> getResident();

    public List<Resident> getResidentArchived();

    public boolean createResident(Resident resident);

    public Resident getResidentById(int id);

    public Resident getArchivedResidentById(int id);

    public boolean updateResidentInfo(Resident resident);

    public boolean deleteResident(int id);
    
    public boolean archiveResident(int id);
    
    public boolean restoreResident(int id);
    
    public List<Resident> searchResident(String searchKeyword);
}
