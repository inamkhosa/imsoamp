/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.oamp.service.exception;

/**
 *
 * @author Alam Sher
 */
public class TrapServiceException extends Exception {

    private String message = null;
    public TrapServiceException() {
        message = "Unknown TrapServiceException";
    }
    
    public TrapServiceException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "TrapServiceException : " + this.message;
    }
}
