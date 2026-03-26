/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.repo;

import com.joysistvi.brdrms.model.DocumentRequest;
import java.util.List;

/**
 *
 * @author Hacutina
 */
public interface DocumentRequestRepo {

    public List<DocumentRequest> fetchDocumentRequestByAdmin();

    public List<DocumentRequest> fetchDocumentRequestByAdminSearch(String kwords);

    public List<DocumentRequest> fetchDocumentRequestPaymentHistoryByAdmin();

    public List<DocumentRequest> fetchDocumentRequestPendingByAdmin();

    public List<DocumentRequest> fetchDocumentRequestNotApprovedByAdmin();

    public List<DocumentRequest> fetchDocumentRequestById(int userId);

    public List<DocumentRequest> fetchDocumentRequestByIdPending(int userId);

    public boolean getCheckDocumentRequestById(int id);

    public boolean getCheckDocumentForDeleteRequestById(int id, int userId);

    public boolean getCheckDocumentRequestPendingForById(int id);

    public boolean getCheckDocumentRequestNotApprovedForById(int id);

    public boolean getCheckDocumentRequestPendingForCancelById(int id, int userId);

    public boolean getCheckDocTypeById(int id);

    public boolean createDocumentRequest(DocumentRequest documentRequest);

    public boolean cancelDocumentRequest(int id);

    public void approveDocumentRequestInsertStatus(int documentRequestId, int adminId, String remarks);

    public void approveDocumentRequestInsertPayment(int documentRequestId, int adminId, double amount, String receiptNo);

    public void rejectedDocumentRequestInsertStatus(int documentRequestId, int adminId, String remarks);

    public boolean rejectedDocumentRequest(int documentRequestId, int adminId);

    public boolean approveDocumentRequest(int documentRequestId, int adminId);

    public boolean deleteDocumentRequest(int id);

    public void deleteDocumentRequestStatus(int id);

}
