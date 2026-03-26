/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.repo;

import com.joysistvi.brdrms.model.DocumentType;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Hacutina
 */
public interface DocTypeRepo {

    public boolean createDocType(DocumentType dt);

    public List<DocumentType> getDocTypes();

    public List<DocumentType> getActiveDocTypes();

    public DocumentType getDocTypeById(int id);

    public boolean updateDocType(DocumentType dt);

    public boolean archiveDocType(int id);

    public boolean restoreDocType(int id);

    public boolean deleteDocType(int id);

    
     
}
