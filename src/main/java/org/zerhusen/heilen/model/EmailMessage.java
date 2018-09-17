/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.model;

/**
 *
 * @author Ferenc
 */
public class EmailMessage {
    
        private String to_address;
	private String subject;
	private String body;

	public String getTo_address() {
		return to_address;
	}

	public void setTo_address(String to_address) {
		this.to_address = to_address;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
            subject = "Correo Testing";
            this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
            body = "Este es un Correo de pruebas pdt: no pesk el archivo adjunto";
            this.body = body;
	}
}
